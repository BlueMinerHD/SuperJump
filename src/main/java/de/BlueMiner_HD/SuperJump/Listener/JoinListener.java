package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.ItemManager;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.Methoden.Stats;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoinListener(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        Methoden.resetPLayer(p);

        Stats.registerPlayer(p);

        if (Methoden.getState() == State.LOBBYPHASE) {
            e.setJoinMessage("§8[§a+§8] §7" + p.getName());
            Methoden.player.add(p);
            if (Methoden.getLobbyLocation() != null) {
                p.teleport(Methoden.getLobbyLocation());
            }
            if (p.hasPermission("SuperJump.Start")) {
                p.getInventory().setItem(4, new ItemManager(Material.DIAMOND, (short) 0, 1).setDisplayName("§7§l« §8§lStart §7§l»").build());
            }
            p.getInventory().setItem(8, new ItemManager(Material.MAGMA_CREAM, (short) 0, 1).setDisplayName("§7§l« §8§lSpiel verlassen §7§l»").build());

            /*ScoreboardManager.setLobbyScoreboard(p);
            for (Player all : Bukkit.getOnlinePlayers()) {
                ScoreboardManager.updateLobbyScoreboard(all);
            }*/

            Methoden.startLobbyphase();

        } else {
            e.setJoinMessage(null);
            Methoden.spectator.add(p);

            for (Player player : Methoden.player) {
                for (Player spectator : Methoden.spectator) {
                    player.hidePlayer(spectator);
                }
            }
            p.spigot().setCollidesWithEntities(false);
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(Methoden.map.getSpawn());
            p.setAllowFlight(true);
            p.setFlying(true);
            p.getInventory().setItem(0,
                    new ItemManager(Material.COMPASS, (short) 0, 1).setDisplayName("§7§l« §9§lLebende Spieler §7§l»").build());
            p.getInventory().setItem(8,
                    new ItemManager(Material.MAGMA_CREAM, (short) 0, 1).setDisplayName("§7§l« §8§lSpiel verlassen §7§l»").build());

        }

    }
}