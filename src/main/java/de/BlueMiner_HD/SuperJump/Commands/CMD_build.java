package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CMD_build implements CommandExecutor {

    private HashMap<Player, GameMode> pgm = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;
        if (!p.hasPermission("SuperJump.build")) {
            p.sendMessage(main.getPrefix() + "§cKeine Rechte!");
            return false;
        }

        if (Methoden.build.isEmpty()) {
            Methoden.build.add(p);
            pgm.put(p, p.getGameMode());
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(main.getPrefix() + "§aDu kannst nun bauen!");
            return false;
        }

        if (Methoden.build.contains(p)) {
            Methoden.build.remove(p);
            p.setGameMode(pgm.get(p));
            p.sendMessage(main.getPrefix() + "§cDu kannst nun nicht mehr bauen!");
            return false;
        }

        Methoden.build.add(p);
        pgm.put(p, p.getGameMode());
        p.setGameMode(GameMode.CREATIVE);
        p.sendMessage(main.getPrefix() + "§aDu kannst nun bauen!");


        return false;
    }
}
