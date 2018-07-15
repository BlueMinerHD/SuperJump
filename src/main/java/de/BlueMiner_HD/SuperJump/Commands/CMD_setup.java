package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_setup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("SuperJump.Setup")){
            p.sendMessage(main.getPrefix() + "§cKeine Rechte!");
            return false;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("Lobby")){
                Methoden.setLobbyLocation(p.getLocation());
                p.sendMessage(main.getPrefix() + "§aErfolgreich die Lobby Location gesetzt!");
            } else {
                sendhelp(p);
            }
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
