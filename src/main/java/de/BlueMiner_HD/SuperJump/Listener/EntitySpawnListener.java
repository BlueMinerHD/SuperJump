package de.BlueMiner_HD.SuperJump.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawnListener(EntitySpawnEvent e) {
        e.setCancelled(true);
        return;


    }
}
