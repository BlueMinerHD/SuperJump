package de.BlueMiner_HD.SuperJump.Commands;

import de.BlueMiner_HD.SuperJump.Methoden.Stats;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_stats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 1) {
                String target = args[0];

                if (Stats.existPlayerName(target)) {
                    sendStatsOther(sender, target);
                } else {
                    sender.sendMessage(main.getPrefix() + "§cDieser Spieler ist nicht in unserer Datenbank!");
                }

            } else {
                Player p = (Player) sender;
                sendStats(p);
            }
        } else {
            if (args.length == 1) {
                String target = args[0];

                if (Stats.existPlayerName(target)) {
                    sendStatsOther(sender, target);
                } else {
                    sender.sendMessage(main.getPrefix() + "§cDieser Spieler ist nicht in unserer Datenbank!");
                }

            }
        }
        return false;

    }

    public static void sendStatsOther(CommandSender sender, String target) {

        int pg = Stats.getPlayedGames(target);
        int wg = Stats.getWonGames(target);

        int position = Stats.getpostion(target);

        sender.sendMessage(" §eStats von §3" + target);
        sender.sendMessage(" §7Position im Ranking: §e" + position);
        sender.sendMessage(" §7Gespielte Spiele: §e" + pg);
        sender.sendMessage(" §7Gewonne Spiele: §e" + wg);
        sender.sendMessage(" §eStats von §3" + target);
    }

    public static void sendStats(Player p) {

        int pg = Stats.getPlayedGames(p);
        int wg = Stats.getWonGames(p);

        int position = Stats.getpostion(p);

        p.sendMessage(" §3Deine §eStats");
        p.sendMessage(" §7Position im Ranking: §e" + position);
        p.sendMessage(" §7Gespielte Spiele: §e" + pg);
        p.sendMessage(" §7Gewonne Spiele: §e" + wg);
        p.sendMessage(" §3Deine §eStats");
    }

}
