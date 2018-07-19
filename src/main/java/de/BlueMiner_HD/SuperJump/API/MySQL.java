package de.BlueMiner_HD.SuperJump.API;

import com.sun.jmx.snmp.daemon.CommunicationException;
import de.BlueMiner_HD.SuperJump.main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

	public static String username;
	public static String password;
	public static String database;
	public static String host;
	public static Connection con;

	public static void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true&useUnicode=yes", username,
						password);
				Bukkit.getConsoleSender().sendMessage("§aSuccessfully connected to MySQL-Database.");
			} catch (SQLException e) {
				Bukkit.getConsoleSender()
						.sendMessage("§cCould not connect to MySQL-Database, please check your MySQL-Settings.");
			}
		}
	}

	public static void close() {
		if (isConnected()) {
			try {
				con.close();
				con = null;
				Bukkit.getConsoleSender().sendMessage("§aSuccessfully closed MySQL-Connection.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isConnected() {
		return con != null;
	}

	public static void update(String qry) {
		if (isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
			} catch (CommunicationException e) {
				close();
				connect();
				update(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ResultSet getResult(String qry) {
		if (isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (CommunicationException e) {
				close();
				connect();
				return getResult(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static File createNewFile(String filename) {

		main.getInstance().getDataFolder().mkdir();

		File f = new File(main.getInstance().getDataFolder(), filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
				return f;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static File getFile(String filename) {
		return new File(main.getInstance().getDataFolder(), filename);
	}

	public static boolean exists(String filename) {
		return getFile(filename).exists();
	}

	public void deleteFile(String filename) {
		File f = new File("plugins/", filename);
		if (f.exists()) {
			f.delete();
		}
	}

	public static YamlConfiguration getConfiguration(String filename) {
		return YamlConfiguration.loadConfiguration(getFile(filename));
	}

	public static void saveFile(File file, YamlConfiguration yml) {
		try {
			yml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadMySQLFiles() {

		if (!MySQL.exists("mysql.yml")) {
			File file = MySQL.createNewFile("mysql.yml");
			YamlConfiguration cfg = MySQL.getConfiguration("mysql.yml");
			cfg.set("user", "localuser");
			cfg.set("password", "localpassword");
			cfg.set("host", "localhost");
			cfg.set("database", "localdatabase");

			saveFile(file, cfg);

		}
		YamlConfiguration cfg = MySQL.getConfiguration("mysql.yml");
		host = cfg.getString("host");
		username = cfg.getString("user");
		database = cfg.getString("database");
		password = cfg.getString("password");

		connect();

		createTable();

	}

	public static void createTable() {
		// Perm
		MySQL.update(
				"CREATE TABLE IF NOT EXISTS stats_superjump (name VARCHAR(100), uuid VARCHAR(100), pg INT, wg INT)");

	}

}
