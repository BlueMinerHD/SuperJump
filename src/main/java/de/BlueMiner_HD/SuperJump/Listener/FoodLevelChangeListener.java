package de.BlueMiner_HD.SuperJump.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChangeListener(FoodLevelChangeEvent e) {
        e.setCancelled(true);

    }
}
