package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onEntityDamageListener(EntityDamageEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamagebyEntityListener(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(Methoden.build.isEmpty()){
                e.setCancelled(true);
                return;
            }
            if(Methoden.build.contains(p)){
                e.setCancelled(false);
                return;
            }

        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onTargetListener(EntityTargetEvent e) {
        if (e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton || e.getEntity() instanceof Creeper
                || e.getEntity() instanceof Spider || e.getEntity() instanceof Enderman
                || e.getEntity() instanceof Slime || e.getEntity() instanceof Ghast) {
            e.setCancelled(true);
        }
    }

}
