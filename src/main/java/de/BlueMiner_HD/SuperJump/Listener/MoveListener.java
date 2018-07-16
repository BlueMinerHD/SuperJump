package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {


    @EventHandler
    public void onMoveListener(PlayerMoveEvent e) {

        if (Methoden.getState() == State.NOMOVE) {
            Player p = e.getPlayer();
            Location to = e.getTo();
            Location from = e.getFrom();

            if (from.getX() != to.getX() || from.getZ() != to.getZ()) {
                p.teleport(from);
            }

        }
    }


}
