package de.BlueMiner_HD.SuperJump.Methoden;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Methoden {

    private static State state = State.LOBBYPHASE;

    public static List<Player> build = new ArrayList<>();
    public static List<Player> player = new ArrayList<>();
    public static List<Player> spectator = new ArrayList<>();
    public static List<Map> voteMaps = new ArrayList<>();

    public static String lobbyserver = "Lobby01";

    public static Player forcemap = null;

    public static Map map;

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

    public static void setLobbyLocation(Location loc) {
        BlueAPI.saveLocation(loc, Files.getConfig().getName(), "Lobby");
    }

    public static Location getLobbyLocation() {
        return BlueAPI.getLocation(Files.getConfig().getName(), "Lobby");
    }

    public static int p1;
    private static int animation = 0;
    public static int LOBBYPHASECANCEL;
    public static int LOBBYPHASE = 60;


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
                        if (LOBBYPHASE > 3) {
                            for (Player all : player) {
                                all.setLevel(LOBBYPHASE);
                                BlueAPI.sendActionbar("§7Die Runde beginnt in §c" + LOBBYPHASE, all);
                            }
                        } else if (LOBBYPHASE > 0) {
                            for (Player all : player) {
                                all.setLevel(LOBBYPHASE);
                                all.playSound(all.getLocation(), Sound.NOTE_BASS, 100, 1);
                                BlueAPI.sendActionbar("§7Die Runde beginnt in §c" + LOBBYPHASE, all);
                                BlueAPI.sendTitle(all, "§c" + LOBBYPHASE, null);
                            }

                        } else if (LOBBYPHASE == 0) {
                            for (Player all : player) {
                                all.setLevel(0);
                                BlueAPI.sendTitle(all, "", null);
                                BlueAPI.sendActionbar("§7Die Runde startet nun§8. §7Ihr werdet nun teleportiert§8.",
                                        all);
                                resetPLayer(all);
                            }
                            Bukkit.getScheduler().cancelTask(LOBBYPHASECANCEL);
                            int i = 1;
                            Map map = Methoden.map;
                            for (Player all : player) {
                                all.teleport(map.getSpawn());
                                //ScoreboardManager.setIngameScoreboard(all);
                                i++;

                            }
                            startNOMOVEPhase();
                        }
                        LOBBYPHASE--;
                    }
                }, 0, 20);
            }
        }
    }

    private static int schutzphase = 10;

    public static void startNOMOVEPhase() {

    }

}
