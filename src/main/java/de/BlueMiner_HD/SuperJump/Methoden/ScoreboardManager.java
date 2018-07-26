package de.BlueMiner_HD.SuperJump.Methoden;

import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    @SuppressWarnings("deprecation")
    public static void setLobbyScoreboard(Player p) {

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

        Team Owner = sb.registerNewTeam("00000Owner");
        Team Admin = sb.registerNewTeam("00001Admin");
        Team SrDev = sb.registerNewTeam("00002SrDev");
        Team SrMod = sb.registerNewTeam("00003SrMod");
        Team Bauleiter = sb.registerNewTeam("00004SrBuilder");
        Team Mod = sb.registerNewTeam("00005Mod");
        Team Sup = sb.registerNewTeam("00006Sup");
        Team Bauteam = sb.registerNewTeam("00007Builder");
        Team vip = sb.registerNewTeam("00008vip");
        Team yt = sb.registerNewTeam("00009YouTuber");
        Team premium = sb.registerNewTeam("00010Premium");
        Team Spieler = sb.registerNewTeam("00011Spieler");

        Owner.setPrefix("§4Inhaber §8┃ §4");
        Admin.setPrefix("§4Admin §8┃ §4");
        SrDev.setPrefix("§bSrDev §8┃ §b");
        SrMod.setPrefix("§cSrMod §8┃ §c");
        Mod.setPrefix("§cMod §8┃ §c");
        Sup.setPrefix("§9Sup §8┃ §9");
        Bauleiter.setPrefix("§2SrBuild §8┃ §2");
        Bauteam.setPrefix("§2Builder §8┃ §2");
        vip.setPrefix("§eVIP §8┃ §e");
        yt.setPrefix("§5YT §8┃ §5");
        premium.setPrefix("§6Premium §8┃ §6");
        Spieler.setPrefix("§7");

        String group;

        if (p.hasPermission("owner")) {
            group = "00000Owner";
        } else if (p.hasPermission("admin")) {
            group = "00001Admin";
        } else if (p.hasPermission("srdev")) {
            group = "00002SrDev";
        } else if (p.hasPermission("srmod")) {
            group = "00003SrMod";
        } else if (p.hasPermission("srbuilder")) {
            group = "00004SrBuilder";
        } else if (p.hasPermission("mod")) {
            group = "00005Mod";
        } else if (p.hasPermission("sup")) {
            group = "00006Sup";
        } else if (p.hasPermission("builder")) {
            group = "00007Builder";
        } else if (p.hasPermission("vip")) {
            group = "00008vip";
        } else if (p.hasPermission("youtuber")) {
            group = "00009YouTuber";
        } else if (p.hasPermission("premium")) {
            group = "00010Premium";
        } else {
            group = "00011Spieler";
        }
        sb.getTeam(group).addEntry(p.getName());
        for (Player all : Bukkit.getOnlinePlayers()) {

            if (!all.getName().equals(p.getName())) {
                Scoreboard asb = all.getScoreboard();

                asb.getTeam(group).addPlayer(p);

                String agroup;

                if (all.hasPermission("owner")) {
                    agroup = "00000Owner";
                } else if (all.hasPermission("admin")) {
                    agroup = "00001Admin";
                } else if (all.hasPermission("srdev")) {
                    agroup = "00002SrDev";
                } else if (all.hasPermission("srmod")) {
                    agroup = "00003SrMod";
                } else if (all.hasPermission("srbuilder")) {
                    agroup = "00004SrBuilder";
                } else if (all.hasPermission("mod")) {
                    agroup = "00005Mod";
                } else if (all.hasPermission("sup")) {
                    agroup = "00006Sup";
                } else if (all.hasPermission("builder")) {
                    agroup = "00007Builder";
                } else if (all.hasPermission("vip")) {
                    agroup = "00008vip";
                } else if (all.hasPermission("youtuber")) {
                    agroup = "00009YouTuber";
                } else if (all.hasPermission("premium")) {
                    agroup = "00010Premium";
                } else {
                    agroup = "00011Spieler";
                }
                sb.getTeam(agroup).addPlayer(all);

            }
        }
        Objective obj = sb.registerNewObjective("aaa", "bbb");

        Team player = sb.registerNewTeam("player");
        player.setPrefix(" §8♦ §e");
        player.setSuffix("" + Methoden.player.size());
        player.addEntry(ChatColor.BLACK.toString());

        Team map = sb.registerNewTeam("map");
        map.setPrefix(" §8♦ §e");
        if (Methoden.map == null) {
            map.setSuffix(Methoden.getVotetMapsSort().get(0).getName());
        } else {
            map.setSuffix(Methoden.map.getName());
        }
        map.addEntry(ChatColor.AQUA.toString());

        obj.setDisplayName("§6§lSuperJump");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("").setScore(9);
        obj.getScore("§7Spieler").setScore(8);
        obj.getScore(ChatColor.BLACK.toString()).setScore(7);
        obj.getScore("  ").setScore(3);
        obj.getScore("§7Map").setScore(2);
        obj.getScore(ChatColor.AQUA.toString()).setScore(1);

        p.setScoreboard(sb);
    }

    @SuppressWarnings("deprecation")
    public static void setIngameScoreboard(Player p) {
        Scoreboard sb = p.getScoreboard();
        sb.clearSlot(DisplaySlot.SIDEBAR);
        Objective obj = sb.registerNewObjective("bbb", "aaa");

        Team player = sb.getTeam("player") != null ? sb.getTeam("player") : sb.registerNewTeam("player");
        player.setPrefix(" §8♦ §e");
        player.setSuffix("" + Methoden.player.size());
        player.addEntry(ChatColor.BLACK.toString());

        if (Methoden.player.size() != 2) {
            new NullPointerException("Es müssen 2 Spieler online sein!");
        }

        double dis1 = Methoden.round(Methoden.getDistanceEnd(Methoden.player.get(0)), 2);

        Team distance1 = sb.registerNewTeam("distance1");
        distance1.setPrefix(" §8♦ §e");
        distance1.setSuffix("" + dis1);
        distance1.addEntry(ChatColor.BLUE.toString());

        double dis2 = Methoden.round(Methoden.getDistanceEnd(Methoden.player.get(1)), 2);

        Team distance2 = sb.registerNewTeam("distance2");
        distance2.setPrefix(" §8♦ §e");
        distance2.setSuffix("" + dis2);
        distance2.addEntry(ChatColor.AQUA.toString());

        Team time = sb.registerNewTeam("time");
        time.setPrefix(" §8♦ §e");
        time.setSuffix("" + Methoden.Timer);
        time.addEntry(ChatColor.RED.toString());

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§lSuperJump");


        obj.getScore("").setScore(12);
        obj.getScore("§7Time").setScore(11);
        obj.getScore(ChatColor.RED.toString()).setScore(10);
        obj.getScore(" ").setScore(9);
        obj.getScore("§7Spieler").setScore(8);
        obj.getScore(ChatColor.BLACK.toString()).setScore(7);
        obj.getScore("  ").setScore(6);
        obj.getScore("§7" + Methoden.player.get(0).getName()).setScore(5);
        obj.getScore(ChatColor.BLUE.toString()).setScore(4);
        obj.getScore("   ").setScore(3);
        obj.getScore("§7" + Methoden.player.get(1).getName()).setScore(2);
        obj.getScore(ChatColor.AQUA.toString()).setScore(1);
    }

    public static void updateIngameScoreboard(Player p) {
        if (Methoden.getState() == State.INGAME || Methoden.getState() == State.NOMOVE) {
            Scoreboard sb = p.getScoreboard();

            sb.getTeam("time").setSuffix("" + Methoden.Timer);

            sb.getTeam("player").setSuffix("" + Methoden.player.size());


            sb.getTeam("distance1").setSuffix("" + Methoden.round(Methoden.getDistanceEnd(Methoden.player.get(0)), 2));

            sb.getTeam("distance2").setSuffix("" + Methoden.round(Methoden.getDistanceEnd(Methoden.player.get(1)), 2));
        }
    }

    public static void updateLobbyScoreboard(Player p) {
        if (Methoden.getState() == State.LOBBYPHASE) {
            Scoreboard sb = p.getScoreboard();

            sb.getTeam("player").setSuffix("" + Methoden.player.size());

            if (Methoden.map == null) {
                sb.getTeam("map").setSuffix(Methoden.getVotetMapsSort().get(0).getName());
            } else {
                sb.getTeam("map").setSuffix(Methoden.map.getName());
            }
        }
    }
}