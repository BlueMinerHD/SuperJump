package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItemListener implements Listener {

    @EventHandler
    public void onDropItemListener(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        if (Methoden.build.isEmpty()) {
            e.setCancelled(true);
            return;
        }
        if (!Methoden.build.contains(p)) {
            e.setCancelled(true);
            return;
        }
    }
}
