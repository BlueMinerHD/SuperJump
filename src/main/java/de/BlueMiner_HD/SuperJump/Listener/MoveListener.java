package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

        } else if (Methoden.getState() == State.INGAME) {
            Player p = e.getPlayer();
            if (p.getLocation().getBlock().getType() == Material.GOLD_PLATE) {
                Block block = p.getLocation().getBlock();


                if (Methoden.lastCheckpointInt.get(p) == null) {
                    Location loc = Methoden.map.getCheckpoint(1);

                    if (block.getLocation().distance(loc) < 1) {
                        Methoden.lastCheckpoint.put(p, loc);
                        Methoden.lastCheckpointInt.put(p, 1);
                        p.sendMessage(main.getPrefix() + "§aDu hast den Checkpoint §e1 §aerreicht!");
                        return;

                    } else {
                        p.sendMessage(main.getPrefix() + "§cBitte gehe zum ersten Checkpoint zurück!");
                        return;
                    }
                }

                for (int i = 2; i <= 10; i++) {
                    int zahl = Methoden.lastCheckpointInt.get(p);
                    if (zahl != i) {
                        Location loc = Methoden.map.getCheckpoint(i);
                        if (block.getLocation().distance(loc) < 1) {
                            if (zahl >= i) {
                                p.sendMessage(main.getPrefix() + "§cBitte laufe nach vorne und nicht nach hinten!");
                                return;
                            }
                            Methoden.lastCheckpoint.put(p, loc);
                            Methoden.lastCheckpointInt.put(p, i);
                            p.sendMessage(main.getPrefix() + "§aDu hast den Checkpoint §e" + i + " §aerreicht!");
                            if (i == 10) {
                                Methoden.end();
                            }
                            return;

                        }
                    }
                }
            }
        }
    }


}
