package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_start implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("SuperJump.Start")) {
            p.sendMessage(main.getPrefix() + "§cKeine Rechte!");
            return false;

        }
        if (Methoden.getState() != State.LOBBYPHASE) {
            p.sendMessage(main.getPrefix() + "Die Lobbyphase ist bereits rum!");
            return false;

        }
        if (Methoden.LOBBYPHASE < 10) {
            p.sendMessage(main.getPrefix() + "Die Zeit ist bereits unter 10 Sekunden!");
            return false;
        }
        if (Methoden.player.size() < 2) {
            p.sendMessage(main.getPrefix() + "Der Timer ist noch nicht gestartet!");
            return false;
        }
        Methoden.LOBBYPHASE = 10;
        p.sendMessage(main.getPrefix() + "§aErfolgreich die Zeit auf 10 Sekunden gesetzt!");

        return false;

    }
}
