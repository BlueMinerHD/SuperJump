package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupItemListener implements Listener {

    @EventHandler
    public void onPickupItemListener(PlayerPickupItemEvent e) {

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
