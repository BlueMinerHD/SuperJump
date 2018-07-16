package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreakListener(BlockBreakEvent e) {
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

    @EventHandler
    public void onBlockPlaceListener(BlockPlaceEvent e) {
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

    @EventHandler
    public void onBlockExplodeListener(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        e.blockList().clear();
    }


}
