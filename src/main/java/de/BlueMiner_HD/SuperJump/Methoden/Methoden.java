package de.BlueMiner_HD.SuperJump.Methoden;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Methoden {

    public static List<Player> build = new ArrayList<>();
    public static List<Player> player = new ArrayList<>();
    public static List<Player> disconect = new ArrayList<>();
    public static List<Player> spectator = new ArrayList<>();
    public static HashMap<Map, Integer> voteMaps = new HashMap<>();
    public static HashMap<Integer, Map> voteMapPosition = new HashMap<>();
    public static HashMap<Player, Map> votet = new HashMap<>();
    public static HashMap<Player, Location> lastCheckpoint = new HashMap<>();
    public static HashMap<Player, Integer> lastCheckpointInt = new HashMap<>();
    public static String lobbyserver = "Lobby01";
    public static Player forcemap = null;
    public static Map map;
    public static int p1;
    public static int LOBBYPHASECANCEL;
    public static int LOBBYPHASE = 60;
    private static int RESTARTCANCEL;
    private static int RESTART = 10;
    private static int SHUTDOWNCANCEL;
    private static State state = State.LOBBYPHASE;
    private static int animation = 0;
    private static int schutzphase = 10;
    public static int TimerCANCEL;
    public static int Timer = 60;

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        Methoden.state = state;
    }

    public static void resetPLayer(Player p) {
        p.setLevel(0);
        p.setExp(0);
        p.setHealthScale(20);
        p.setMaxHealth(20);
        p.setHealth(p.getMaxHealth());
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }

        p.setAllowFlight(false);
        p.setFlying(false);
        p.setFoodLevel(20);
        p.setHealthScaled(true);
        // p.setSaturation(1);
        p.setGameMode(GameMode.SURVIVAL);
    }

    public static Location getLobbyLocation() {
        return BlueAPI.getLocation(Files.getConfig().getName(), "Lobby");
    }

    public static void setLobbyLocation(Location loc) {
        BlueAPI.saveLocation(loc, Files.getConfig().getName(), "Lobby");
    }

    public static double getDistanceEnd(Player p) {
        return p.getLocation().distance(Methoden.map.getCheckpoint(10));
    }

    public static List<Map> getVotetMapsSort(){
        if (Methoden.map == null) {

            List<Map> sortMap = new ArrayList<>();

            List<Integer> l = new ArrayList<>(voteMaps.values());

            Collections.sort(l);

            for (int i = l.size(); i != 0; i--) {
                for (Map p : voteMaps.keySet()) {
                    if (l.get(i - 1) == voteMaps.get(p)) {
                        sortMap.add(p);
                    }
                }
            }

            return sortMap;

        }
        return null;
    }

    public static void startLobbyphase() {

        if (player.size() < 2) {
            if (!Bukkit.getScheduler().isQueued(p1)) {
                p1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {

                    @Override
                    public void run() {

                        if (animation == 0) {
                            for (Player all : player) {
                                BlueAPI.sendActionbar("§7Warten auf weiteren Spieler§8", all);
                            }
                            animation++;
                        } else if (animation == 1) {
                            for (Player all : player) {
                                BlueAPI.sendActionbar("§7Warten auf weiteren Spieler§8.", all);
                            }
                            animation++;
                        } else if (animation == 2) {
                            for (Player all : player) {
                                BlueAPI.sendActionbar("§7Warten auf weiteren Spieler§8..", all);
                            }
                            animation++;
                        } else if (animation == 3) {
                            for (Player all : player) {
                                BlueAPI.sendActionbar("§7Warten auf weiteren Spieler§8...", all);
                            }
                            animation = 0;
                        }

                    }
                }, 0, 20);
            }
        } else if (player.size() == 2) {
            Bukkit.getScheduler().cancelTask(p1);
            if (!Bukkit.getScheduler().isQueued(LOBBYPHASECANCEL)) {
                LOBBYPHASECANCEL = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {

                    @Override
                    public void run() {
                        if (LOBBYPHASE == 5) {
                            for (Player all : player) {
                                all.setLevel(LOBBYPHASE);
                                BlueAPI.sendActionbar("§7Die Runde beginnt in §c" + LOBBYPHASE, all);
                            }

                            if (Methoden.map == null) {

                                Methoden.map = getVotetMapsSort().get(0);

                                for (Player all : player) {
                                    all.getInventory().setItem(0, null);
                                    BlueAPI.sendTitle(all, "Map voting beendet", Methoden.map.getName());
                                }

                            }

                        } else if (LOBBYPHASE > 3) {
                            for (Player all : player) {
                                all.setLevel(LOBBYPHASE);
                                BlueAPI.sendActionbar("§7Die Runde beginnt in §c" + LOBBYPHASE, all);
                            }
                        } else if (LOBBYPHASE > 0) {
                            for (Player all : player) {
                                all.setLevel(LOBBYPHASE);
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 100, 1);
                                BlueAPI.sendActionbar("§7Die Runde beginnt in §c" + LOBBYPHASE, all);
                                BlueAPI.sendTitle(all, "§c" + LOBBYPHASE, " ");
                            }

                        } else if (LOBBYPHASE == 0) {
                            for (Player all : player) {
                                all.setLevel(0);
                                BlueAPI.sendTitle(all, " ", " ");
                                BlueAPI.sendActionbar("§7Die Runde startet nun§8. §7Ihr werdet nun teleportiert§8.",
                                        all);
                                resetPLayer(all);
                            }
                            Bukkit.getScheduler().cancelTask(LOBBYPHASECANCEL);
                            Map map = Methoden.map;
                            for (Player all : player) {
                                all.teleport(map.getSpawn());
                                all.getInventory().setItem(0, new ItemManager(Material.GOLD_PLATE, (short) 0, 1).
                                        setDisplayName("§7§l« §8§lTelepoerter §7§l»").addLoreLine("§8Telepoertiere dich zum letzten Checkpoint").build());
                                all.getInventory().setItem(8, new ItemManager(Material.MAGMA_CREAM, (short) 0, 1).setDisplayName("§7§l« §8§lSpiel verlassen §7§l»").build());
                                //ScoreboardManager.setIngameScoreboard(all);

                            }
                            startNOMOVEPhase();
                        }
                        LOBBYPHASE--;
                    }
                }, 0, 20);
            }
        }
    }

    private static void startNOMOVEPhase() {

        setState(State.NOMOVE);
        new BukkitRunnable() {

            @Override
            public void run() {
                if (schutzphase == 10) {
                    for (Player all : player) {
                        all.sendMessage(main.getPrefix() + "§7SuperJump startet in §e" + schutzphase + " §7Sekunden");
                    }
                } else if (schutzphase == 5) {
                    for (Player all : player) {
                        all.sendMessage(main.getPrefix() + "§7SuperJump startet in §e" + schutzphase + " §7Sekunden");
                    }
                } else if (schutzphase == 4) {
                    for (Player all : player) {
                        all.sendMessage(main.getPrefix() + "§7SuperJump startet in §e" + schutzphase + " §7Sekunden");
                    }
                } else if (schutzphase > 0 && schutzphase < 4) {
                    for (Player all : player) {
                        all.sendMessage(main.getPrefix() + "§7SuperJump startet in §e" + schutzphase + " §7Sekunden");
                        BlueAPI.sendTitle(all, "§c" + schutzphase, null);
                    }

                } else if (schutzphase == 0) {
                    for (Player all : player) {
                        BlueAPI.sendTitle(all, "", null);
                        Stats.addPlayedGames(all, 1);
                    }
                    setState(State.INGAME);
                    cancel();

                }
                schutzphase--;

            }
        }.runTaskTimer(main.getInstance(), 0, 20);


    }

    public static void end(final Player winner) {
        setState(State.RESTART);

        Stats.addwonGames(winner, 1);

        Bukkit.broadcastMessage(main.getPrefix() + "§aDer Spieler §8" + winner.getName() + "§a hat gewonnen!");

        RESTARTCANCEL = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {

            @Override
            public void run() {
                if (RESTART > 3) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(RESTART);
                        BlueAPI.sendActionbar("§7Der Server startet in §c" + RESTART + "§7 neu", all);
                    }
                } else if (RESTART > 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(RESTART);
                        all.playSound(all.getLocation(), Sound.NOTE_BASS, 100, 1);
                        BlueAPI.sendActionbar("§7Der Server startet in §c" + RESTART + "§7 neu", all);
                        BlueAPI.sendTitle(all, "§c" + RESTART, null);
                    }

                } else if (RESTART == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(0);
                        BlueAPI.sendTitle(all, "", null);
                        BlueAPI.sendActionbar("§7Der Server startet nun neu§8. §7Ihr werdet nun teleportiert§8.", all);
                        BlueAPI.connect(all, lobbyserver);
                    }
                    Bukkit.getScheduler().cancelTasks(main.getInstance());
                    shutdown();

                }

                Firework fire = winner.getWorld().spawn(winner.getLocation(), Firework.class);

                FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED).flicker(true).trail(true)
                        .withFade(Color.SILVER).with(FireworkEffect.Type.BURST).build();

                FireworkMeta meta = fire.getFireworkMeta();
                meta.addEffect(effect);
                meta.setPower(3);
                fire.setFireworkMeta(meta);
                RESTART--;

            }


        }, 0, 20);

    }

    private static void shutdown() {
        if (Bukkit.getOnlinePlayers().size() != 0) {
            if (!Bukkit.getScheduler().isQueued(SHUTDOWNCANCEL)) {
                SHUTDOWNCANCEL = Bukkit.getScheduler().scheduleSyncDelayedTask(main.getInstance(), new Runnable() {

                    @Override
                    public void run() {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            BlueAPI.connect(all, lobbyserver);
                        }
                        shutdown();
                        return;

                    }
                }, 200);
            }
        }
    }

    public static void startTimer(){
        TimerCANCEL = Bukkit.getScheduler().scheduleSyncRepeatingTask(main.getInstance(), new Runnable() {

            @Override
            public void run() {
                if(getState() != State.INGAME){
                    Bukkit.getScheduler().cancelTask(TimerCANCEL);
                }
                if (Timer > 0 && Timer < 3) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(Timer);
                        all.playSound(all.getLocation(), Sound.NOTE_BASS, 100, 1);
                        BlueAPI.sendActionbar("§7Die Zeit läuft in §e" + Timer + "§7 Sekunden ab!", all);
                    }

                } else if (Timer == 0) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.setLevel(0);
                        BlueAPI.sendTitle(all, "", null);
                        BlueAPI.sendActionbar("§7Die Zeit ist abgelaufen!", all);
                    }
                    Bukkit.getScheduler().cancelTask(TimerCANCEL);
                    endTimer();

                }
                Timer--;

            }


        }, 0, 20);
    }

    private static void endTimer() {

    }

}
