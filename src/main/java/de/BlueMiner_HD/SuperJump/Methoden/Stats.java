package de.BlueMiner_HD.SuperJump.Methoden;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import de.BlueMiner_HD.SuperJump.API.MySQL;

public class Stats {

	public static void registerPlayer(Player p) {
		if (!existPlayer(p.getUniqueId().toString())) {
			MySQL.update("INSERT INTO stats_superjump(name, uuid, pg, wg) VALUES ('" + p.getName()
					+ "', '" + p.getUniqueId().toString() + "', '0', '0')");

		} else {
			set(p.getUniqueId().toString(), "uuid", "name", p.getName());
		}
	}

	public static void setPlayedGames(Player p, int playedgames) {
		set(p.getUniqueId().toString(), "uuid", "pg", "" + playedgames);
	}

	public static void setwongames(Player p, int wongames) {
		set(p.getUniqueId().toString(), "uuid", "wg", "" + wongames);
	}

	public static void addPlayedGames(Player p, int playedgames) {
		int playedgame = getPlayedGames(p) + playedgames;
		set(p.getUniqueId().toString(), "uuid", "pg", "" + playedgame);
	}

	public static void addwonGames(Player p, int wongames) {
		int wongame = getWonGames(p) + wongames;
		set(p.getUniqueId().toString(), "uuid", "wg", "" + wongame);
	}

	public static int getPlayedGames(Player p) {
		return (int) get(p.getUniqueId().toString(), "uuid", "pg");
	}

	public static int getWonGames(Player p) {
		return (int) get(p.getUniqueId().toString(), "uuid", "wg");
	}

	public static int getpostion(Player p) {

		ResultSet rs = MySQL.getResult(
				"SELECT rank FROM (SELECT @rank:=@rank+1 AS rank, name, uuid FROM stats_superjump, (SELECT @rank := 0) r ORDER BY wg DESC ) t WHERE uuid = '"
						+ p.getUniqueId() + "'");
		try {
			if (rs.next()) {
				Object v = rs.getObject("rank");
				Double d = (double) v;
				return d.intValue();
			}
		} catch (SQLException e) {
		}

		return 0;
	}

	public static void setPlayedGames(String player, int playedgames) {
		set(player, "name", "pg", "" + playedgames);
	}

	public static void setwongames(String player, int wongames) {
		set(player, "name", "wg", "" + wongames);
	}

	public static void addPlayedGames(String player, int playedgames) {
		int playedgame = getPlayedGames(player) + playedgames;
		set(player, "name", "pg", "" + playedgame);
	}

	public static void addwonGames(String player, int wongames) {
		int wongame = getWonGames(player) + wongames;
		set(player, "name", "wg", "" + wongame);
	}

	public static int getPlayedGames(String player) {
		return (int) get(player, "name", "pg");
	}

	public static int getWonGames(String player) {
		return (int) get(player, "name", "wg");
	}

	public static int getpostion(String player) {

		ResultSet rs = MySQL.getResult(
				"SELECT rank FROM (SELECT @rank:=@rank+1 AS rank, name, uuid FROM stats_superjump, (SELECT @rank := 0) r ORDER BY wg DESC ) t WHERE name = '"
						+ player + "'");
		try {
			if (rs.next()) {
				Object v = rs.getObject("rank");
				Double d = (double) v;
				return d.intValue();
			}
		} catch (SQLException e) {
		}

		return 0;
	}

	public static void set(String whereresult, String where, String whereset, String set) {
		MySQL.update(
				"UPDATE stats_superjump SET " + whereset + "='" + set + "' WHERE " + where + "='" + whereresult + "'");
	}

	public static Object get(String whereresult, String where, String select) {

		ResultSet rs = MySQL
				.getResult("SELECT " + select + " FROM stats_superjump WHERE " + where + "='" + whereresult + "'");
		try {
			if (rs.next()) {
				Object v = rs.getObject(select);
				return v;
			}
		} catch (SQLException e) {
		}
		return null;
	}

	public static boolean existPlayer(String uuid) {
		try {
			ResultSet rs = MySQL.getResult("SELECT * FROM stats_superjump WHERE uuid='" + uuid + "'");

			if (rs.next()) {
				return rs.getString("uuid") != null;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean existPlayerName(String name) {
		try {
			ResultSet rs = MySQL.getResult("SELECT * FROM stats_superjump WHERE name='" + name + "'");

			if (rs.next()) {
				return rs.getString("name") != null;
			}
			rs.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
