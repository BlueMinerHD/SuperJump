package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD_test implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Methoden.getState() == State.INGAME) {
            Methoden.Timer = 5;
        }
        return false;
    }
}
