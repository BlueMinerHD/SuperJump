package de.BlueMiner_HD.SuperJump.Listener;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.Methoden.ItemManager;
import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InteractListener implements Listener {

    private static HashMap<Player, Integer> time = new HashMap<>();

    @EventHandler
    private void onInteractListener(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = e.getItem();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                if (Methoden.spectator.contains(p)) {
                    if (item.getItemMeta().getDisplayName().equals("§7§l« §9§lLebende Spieler §7§l»")
                            && item.getType() == Material.COMPASS) {
                        Inventory inv = Bukkit.createInventory(null, 9, "§7§l« §9§lLebende Spieler §7§l»");

                        for (int i = 0; i < Methoden.player.size(); i++) {
                            Player all = Methoden.player.get(i);
                            inv.setItem(i, new ItemManager(Material.SKULL_ITEM, (short) 3)
                                    .setSkullOwner(all.getName()).setDisplayName(all.getName()).build());
                        }

                        p.openInventory(inv);
                        return;
                    }
                }

                if (item.getItemMeta().getDisplayName().equals("§7§l« §8§lSpiel verlassen §7§l»")
                        && item.getType() == Material.MAGMA_CREAM) {
                    BlueAPI.connect(p, Methoden.lobbyserver);
                    return;
                }
            }
        }
        if (Methoden.getState() == State.INGAME) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Methoden.player.contains(p)) {
                    ItemStack item = e.getItem();
                    if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        if (item.getType() == Material.GOLD_PLATE
                                && item.getItemMeta().getDisplayName().equals("§7§l« §8§lTelepoerter §7§l»")) {
                            teleporter(p);
                        }
                    }
                }
            }
        } else if (Methoden.getState() == State.LOBBYPHASE) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Methoden.player.contains(p)) {
                    ItemStack item = e.getItem();
                    if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        if (item.getItemMeta().getDisplayName().equals("§7§l« §8§lStart §7§l»")
                                && item.getType() == Material.DIAMOND && p.hasPermission("SuperJump.Start")) {
                            p.performCommand("start");
                        } else if (item.getItemMeta().getDisplayName().equals("§7§l« §8§lMap voting §7§l»")
                                && item.getType() == Material.CHEST) {
                            Inventory inv = Bukkit.createInventory(null, 9, "§7§l« §8§lMap voting §7§l»");

                            if (Methoden.voteMaps.size() == 2) {
                                List<Map> l = new ArrayList<>(Methoden.voteMaps.keySet());
                                Map map = l.get(0);
                                ItemStack itemmap = map.getItem();
                                inv.setItem(3, itemmap);
                                Methoden.voteMapPosition.put(0, map);

                                Map map2 = l.get(1);
                                ItemStack itemmap2 = map2.getItem();
                                inv.setItem(5, itemmap2);
                                Methoden.voteMapPosition.put(1, map2);

                            } else {
                                List<Map> l = new ArrayList<>(Methoden.voteMaps.keySet());
                                Map map = l.get(0);
                                ItemStack itemmap = map.getItem();
                                inv.setItem(2, itemmap);
                                Methoden.voteMapPosition.put(0, map);

                                Map map2 = l.get(1);
                                ItemStack itemmap2 = map2.getItem();
                                inv.setItem(4, itemmap2);
                                Methoden.voteMapPosition.put(1, map2);

                                Map map3 = l.get(2);
                                ItemStack itemmap3 = map3.getItem();
                                inv.setItem(6, itemmap3);
                                Methoden.voteMapPosition.put(2, map3);
                            }
                            p.openInventory(inv);

                        }
                    }
                }

            }
        }
    }

    public static void teleporter(final Player p) {

        if (time.isEmpty() || !time.containsKey(p)) {
            if (Methoden.lastCheckpoint.get(p) == null) {
                p.teleport(Methoden.map.getSpawn());
            } else {
                p.teleport(Methoden.lastCheckpoint.get(p));
            }
            time.put(p, 3);
            new BukkitRunnable() {
                @Override
                public void run() {
                    time.remove(p);

                }
            }.runTaskLaterAsynchronously(main.getInstance(), time.get(p) * 20);
        } else {
            p.sendMessage(main.getPrefix() + "§cDu kannst dich nur alle §e3 §cSekunden zum letzten Checkpoint teleportieren!");
        }
    }

}
