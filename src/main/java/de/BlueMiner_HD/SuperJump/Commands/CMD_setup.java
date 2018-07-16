package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                        p.sendMessage(main.getPrefix() + "§cDiese Map hat breits den Namen §e " + name + "§c!");
                        return false;
                    }
                } catch (NullPointerException e) {

                }

                map.setName(name);

                p.sendMessage(main.getPrefix() + "§aErfolgreich der Map " + strmap + " den Anzeigename " + name + " gegeben!");


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
        p.sendMessage(main.getPrefix() + "§7------------§2Hilfe§7------------");

    }
}
