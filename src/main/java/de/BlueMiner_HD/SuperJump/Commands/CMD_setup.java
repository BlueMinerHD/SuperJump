package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class CMD_setup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("SuperJump.Setup")) {
            p.sendMessage(main.getPrefix() + "§cKeine Rechte!");
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("Lobby")) {
                Methoden.setLobbyLocation(p.getLocation());
                p.sendMessage(main.getPrefix() + "§aErfolgreich die Lobby Location gesetzt!");
            } else {
                sendhelp(p);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("addMap")) {
                String map = args[1];

                if (Map.existis(map)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert bereits!");
                    return false;
                }

                new Map(map);
                p.sendMessage(main.getPrefix() + "§aErfolgreich die Map §e" + map + " §aerstellt!");
            } else if (args[0].equalsIgnoreCase("spawn")) {

                String mapname = args[1];

                if (!Map.existis(mapname)) {
                    p.sendMessage(main.getPrefix() + "§cDie Map §e" + mapname + " §cexistiert nicht!");
                    return false;
                }

                Map map = Map.getMap(mapname);
                map.setSpawn(p.getLocation());


                p.sendMessage(main.getPrefix() + "§aErfolgreich den Spawn §afür die Map §e"
                        + mapname + " §agesetzt!");


            } else {
                sendhelp(p);
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("addmap")) {
                String map = args[1];
                String name = args[2];

                if (Map.existis(map)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert bereits!");
                    return false;
                }

                new Map(map, name);
                p.sendMessage(main.getPrefix() + "§aErfolgreich die Map §e" + map + " §a mit dem Namen §e" + name
                        + " §aerstellt!");
            } else if (args[0].equalsIgnoreCase("setName")) {
                String strmap = args[1];
                String name = args[2];

                if (!Map.existis(strmap)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }
                Map map = Map.getMap(strmap);

                try {
                    if (map.getName().equals(name)) {
                        p.sendMessage(main.getPrefix() + "§cDiese Map hat breits den Namen §e" + name + "§c!");
                        return false;
                    }
                } catch (NullPointerException e) {

                }

                map.setName(name);

                p.sendMessage(main.getPrefix() + "§aErfolgreich der Map §e" + strmap + "§a den Anzeigename §e" + name + " gegeben!");


            } else if (args[0].equalsIgnoreCase("setItemID")) {
                String strmap = args[1];
                String strid = args[2];

                if (!BlueAPI.isNumber(strid)) {
                    p.sendMessage(main.getPrefix() + "§c" + strid + " ist keine Zahl!");
                }
                int id = BlueAPI.getNumber(strid);

                if (!Map.existis(strmap)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }
                Map map = Map.getMap(strmap);

                map.setItemID(id);

                p.sendMessage(main.getPrefix() + "§aDas Item der Map §e" + strmap + " §ahat jetzt die ID §e" + id);

            } else if (args[0].equalsIgnoreCase("setItemSubID")) {
                String strmap = args[1];
                String strsubid = args[2];

                if (!BlueAPI.isNumber(strsubid)) {
                    p.sendMessage(main.getPrefix() + "§c" + strsubid + " ist keine Zahl!");
                }
                int subid = BlueAPI.getNumber(strsubid);

                if (!Map.existis(strmap)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }
                Map map = Map.getMap(strmap);

                map.setItemSubID(subid);

                p.sendMessage(main.getPrefix() + "§aDas Item der Map §e" + strmap + " §ahat jetzt die SubID §e" + strsubid);

            } else if (args[0].equalsIgnoreCase("setItemDisplayName")) {
                String strmap = args[1];
                String displayname = args[2];

                if (!Map.existis(strmap)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }
                Map map = Map.getMap(strmap);

                map.setItemDisplayName(displayname);

                p.sendMessage(main.getPrefix() + "§aDas Item der Map §e" + strmap + " §ahat jetzt den Namen §e" + displayname);

            } else if (args[0].equalsIgnoreCase("setCheckpoint")) {
                String strmap = args[1];
                String stri = args[2];

                if (!BlueAPI.isNumber(stri)) {
                    p.sendMessage(main.getPrefix() + "§c" + stri + " ist keine Zahl!");
                    return false;
                }
                int i = BlueAPI.getNumber(stri);

                if(i > 10 || i < 1){
                    p.sendMessage(main.getPrefix() + "§dDie Zahl muss zwischen 1 und 10 sein!");
                    return false;
                }

                if (!Map.existis(strmap)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }
                Map map = Map.getMap(strmap);

                Location loc = p.getLocation();

                Block block = loc.getBlock();

                if(block.getType() != Material.GOLD_PLATE){
                    p.sendMessage(main.getPrefix() + "§cNur goldene Druckplatten können Checkpoints sein!");
                    return false;
                }

                map.setCheckpoint(loc, i);

                p.sendMessage(main.getPrefix() + "§aErfolgreich den Checkpoint §e" + i + " §aauf der Map §e" + strmap + "§a gesetzt");

            } else {
                sendhelp(p);
            }

        } else {
            sendhelp(p);
        }


        return false;
    }

    private void sendhelp(Player p) {
        p.sendMessage(main.getPrefix() + "§7------------§2Hilfe§7------------");
        p.sendMessage(main.getPrefix() + "§2/setup Lobby §7| §2Setzte den Lobby Spawn");
        p.sendMessage(main.getPrefix() + "§2/setup addMap <Name> [Anzeigename]§7| §2Füge eine Map hinzu");
        p.sendMessage(main.getPrefix() + "§2/setup setName <Map> <Name> §7| §2Setzte den Anzeigenamen einer Map");
        p.sendMessage(main.getPrefix() + "§2/setup spawn <Map> §7| §2Setze den Spawn der Map");
        p.sendMessage(main.getPrefix() + "§2/setup setItemID <Map> <id> §7| §2Setze die ID des Items der Map");
        p.sendMessage(main.getPrefix() + "§2/setup setItemSubID <Map> <subid> §7| §2Setze die SubID des Items der Map");
        p.sendMessage(main.getPrefix() + "§2/setup setItemDisplayName <Map> <displayname> §7| §2Setze den Namen des Items der Map");
        p.sendMessage(main.getPrefix() + "§2/setup setCheckpoint <Map> <1-10> §7| §2Setze einen Checkpoint der Map");
        p.sendMessage(main.getPrefix() + "§7------------§2Hilfe§7------------");

    }
}
