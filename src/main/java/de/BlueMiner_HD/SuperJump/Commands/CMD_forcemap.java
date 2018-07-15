package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import de.BlueMiner_HD.SuperJump.Methoden.State;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMD_forcemap implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)

        {
            Player p = (Player) sender;

            if (!p.hasPermission("SuperJump.forcemap")) {
                p.sendMessage(main.getPrefix() + "§cKeine Rechte!");
                return false;
            }
            if (Methoden.getState() != State.LOBBYPHASE) {
                p.sendMessage(main.getPrefix() + "Die Lobbyphase ist bereits rum!");
                return false;
            }

            if (Methoden.forcemap != null) {
                p.sendMessage(main.getPrefix() + "Es wurde bereits eine Map ausgewählt!");
                return false;
            }

            if (args.length == 0) {
                String txt = main.getPrefix() + "Es gibt folgende Maps: ";
                List<String> maps = Map.getMaps();
                if (maps.size() > 1) {
                    txt = txt + maps.get(0);
                    for (int i = 1; i < maps.size(); i++) {
                        txt = txt + ", " + maps.get(i);
                    }
                } else if (maps.size() != 0) {
                    txt = txt + maps.get(0);
                }
                p.sendMessage(txt);

                return false;
            } else if (args.length == 1) {
                String map = args[0];

                if (!Map.existis(map)) {
                    p.sendMessage(main.getPrefix() + "§cDiese Map existiert nicht!");
                    return false;
                }

                Methoden.map = Map.getMap(map);
                Methoden.forcemap = p;
                p.sendMessage(main.getPrefix() + "§aErfolgreich die Map " + map + " §aausgewählt!");

                /*for (Player all : Methoden.player) {
                    ScoreboardManager.updateLobbyScoreboard(all);
                }*/

            }

        }

        return false;
    }
}
