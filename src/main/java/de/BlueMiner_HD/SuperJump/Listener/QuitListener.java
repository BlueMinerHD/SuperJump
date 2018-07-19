package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuitListener(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (Methoden.disconect.contains(p)) {
            Methoden.disconect.remove(p);
            e.setQuitMessage(null);
            return;
        }

        if (Methoden.getState() == State.LOBBYPHASE) {
            e.setQuitMessage("§8[§c-§8] §7" + p.getName());
            Methoden.player.remove(p);
            /*for (Player all : Bukkit.getOnlinePlayers()) {
                ScoreboardManager.updateLobbyScoreboard(all);
            }*/
            if (Methoden.votet.containsKey(p)) {
                Map map = Methoden.votet.get(p);

                int votes = Methoden.voteMaps.get(map);
                Methoden.voteMaps.put(map, votes - 1);

                Methoden.votet.remove(p);
            }

            if (Methoden.forcemap == p) {
                Methoden.forcemap = null;
            }
            if (Methoden.player.size() < 2) {
                Bukkit.getScheduler().cancelTask(Methoden.p1);
                Bukkit.getScheduler().cancelTask(Methoden.LOBBYPHASECANCEL);
                Methoden.LOBBYPHASE = 60;
                for (Player all : Methoden.player) {
                    all.setLevel(0);
                }
                Methoden.startLobbyphase();
            }
        } else if (Methoden.getState() == State.INGAME || Methoden.getState() == State.NOMOVE) {
            if (Methoden.spectator.contains(p)) {
                e.setQuitMessage(null);
                Methoden.spectator.remove(p);
                for (Player all : Methoden.spectator) {
                    all.sendMessage("§8[§c-§8] §7" + p.getName());
                }
            } else {
                e.setQuitMessage("§8[§c-§8] §7" + p.getName());
                Methoden.player.remove(p);
            }
            /*for (Player all : Bukkit.getOnlinePlayers()) {
                ScoreboardManager.updateIngameScoreboard(all);
            }*/
            if (Methoden.player.size() == 1) {
                if (Methoden.getState() == State.NOMOVE) {
                    BlueAPI.connect(Methoden.player.get(0), Methoden.lobbyserver);
                    Bukkit.getScheduler().cancelAllTasks();
                    return;
                }
                Methoden.end(p);
            } else if (Methoden.player.size() == 0) {
                Bukkit.getScheduler().cancelAllTasks();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }
        } else if (Methoden.getState() == State.RESTART) {
            if (Methoden.spectator.contains(p)) {
                e.setQuitMessage(null);
                Methoden.spectator.remove(p);
                for (Player all : Methoden.spectator) {
                    all.sendMessage("§8[§c-§8] §7" + p.getName());
                }
            } else {
                e.setQuitMessage("§8[§c-§8] §7" + p.getName());
                Methoden.player.remove(p);
            }
            if (Methoden.player.size() == 0) {
                Bukkit.getScheduler().cancelAllTasks();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }
        }
    }

}
