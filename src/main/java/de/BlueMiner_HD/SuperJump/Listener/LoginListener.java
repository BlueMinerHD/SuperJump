package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();


        if (Methoden.getState() == State.LOBBYPHASE) {
            if (Bukkit.getOnlinePlayers().size() >= 2) {
                if (p.hasPermission("SuperJump.JoinFullServer")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (!all.hasPermission("SuperJump.StayAtFullServer")) {
                            Methoden.disconect.add(all);
                            Methoden.player.remove(all);
                            Bukkit.broadcastMessage("§8[§c-§8] §7" + all.getName());
                            all.kickPlayer(main.getPrefix() + "§cDu wurdest von einem Premium Spieler gekickt! Kaufe dir Premium damit du nicht mehr gekickt werden kannst!");
                            BlueAPI.connect(all, Methoden.lobbyserver);

                            e.allow();

                            return;
                        }
                    }
                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, "§cEs wurde kein Spieler zum kicken gefunden!");

                } else {
                    e.disallow(PlayerLoginEvent.Result.KICK_FULL, "§cDer Server ist voll. Kaufe dir Premium um joinen zu können!");
                }
            }
        } else {
            e.allow();
        }

    }


}
