package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
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
