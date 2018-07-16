package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickListener(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getWhoClicked();

        if (Methoden.getState() == State.LOBBYPHASE) {
            Inventory inv = e.getClickedInventory();
            ItemStack item = e.getCurrentItem();
            if (inv != null && item != null) {
                if (inv.getName().equals("§7§l« §8§lMap voting §7§l»")) {
                    if (!Methoden.votet.containsKey(p)) {
                        if (e.getSlot() == 2) {
                            Map map = Methoden.voteMapPosition.get(0);
                            int votes = Methoden.voteMaps.get(map);
                            Methoden.voteMaps.put(map, votes + 1);

                            p.closeInventory();
                            p.sendMessage(main.getPrefix() + "§aErfolgreich für die Map §e" + map.getName() + " §aabgestimmt!");
                            Methoden.votet.put(p, map);

                        } else if (e.getSlot() == 3) {
                            Map map = Methoden.voteMapPosition.get(0);
                            int votes = Methoden.voteMaps.get(map);
                            Methoden.voteMaps.put(map, votes + 1);

                            p.closeInventory();
                            p.sendMessage(main.getPrefix() + "§aErfolgreich für die Map §e" + map.getName() + " §aabgestimmt!");
                            Methoden.votet.put(p, map);
                        } else if (e.getSlot() == 4) {
                            Map map = Methoden.voteMapPosition.get(1);
                            int votes = Methoden.voteMaps.get(map);
                            Methoden.voteMaps.put(map, votes + 1);

                            p.closeInventory();
                            p.sendMessage(main.getPrefix() + "§aErfolgreich für die Map §e" + map.getName() + " §aabgestimmt!");
                            Methoden.votet.put(p, map);
                        } else if (e.getSlot() == 5) {
                            Map map = Methoden.voteMapPosition.get(1);
                            int votes = Methoden.voteMaps.get(map);
                            Methoden.voteMaps.put(map, votes + 1);

                            p.closeInventory();
                            p.sendMessage(main.getPrefix() + "§aErfolgreich für die Map §e" + map.getName() + " §aabgestimmt!");
                            Methoden.votet.put(p, map);
                        } else if (e.getSlot() == 6) {
                            Map map = Methoden.voteMapPosition.get(2);
                            int votes = Methoden.voteMaps.get(map);
                            Methoden.voteMaps.put(map, votes + 1);

                            p.closeInventory();
                            p.sendMessage(main.getPrefix() + "§aErfolgreich für die Map §e" + map.getName() + " §aabgestimmt!");
                            Methoden.votet.put(p, map);
                        }
                    } else {
                        p.sendMessage(main.getPrefix() + "§cDu hast bereits abgestimmt!");
                    }
                }
            }


            if (Methoden.build.isEmpty()) {
                e.setCancelled(true);
                return;
            }
            if (!Methoden.build.contains(p)) {
                e.setCancelled(true);
                return;
            }
        } else if (Methoden.spectator.contains(p)) {
            Inventory inv = e.getClickedInventory();
            ItemStack item = e.getCurrentItem();
            if (inv != null && item != null) {
                if (inv.getName().equals("§7§l« §9§lLebende Spieler §7§l»")
                        && item.getType() == Material.SKULL_ITEM) {
                    SkullMeta meta = (SkullMeta) item.getItemMeta();

                    Player target = Bukkit.getPlayer(meta.getOwner());

                    if (target != null && target.isOnline()) {
                        p.closeInventory();
                        p.teleport(target);
                    }

                }
            }

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

}
