package de.BlueMiner_HD.SuperJump.Methoden;

import java.util.List;

import org.bukkit.Location;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;

public class Map {

	public static Map getMap(String name) {
		if (!existis(name)) {
			new NullPointerException("Map " + name + " existiert nicht").printStackTrace();
			return null;
		}
		return new Map(name, 0);
	}

	public static List<String> getMaps() {
		return Files.getConfigYml().getStringList("Maps");
	}

	public static void setMaps(List<String> list) {
		Files.getConfigYml().set("Maps", list);
		Files.saveYml();
	}

	public static void addMap(String name) {
		List<String> list = getMaps();
		list.add(name);
		setMaps(list);
	}

	public static boolean existis(String map) {
		if (getMaps().isEmpty() || !getMaps().contains(map)) {
			return false;
		}
		return true;
	}

	private String map;
	private String name;

	public Map(String map) {
		this.map = map;
		addMap(map);
	}

	public Map(String map, String name) {
		this.map = map;
		addMap(map);
		setName(name);
	}

	private Map(String map, int id) {
		this.map = map;
		if (Files.getConfigYml().getString(getMap() + ".Name") != null) {
			name = Files.getConfigYml().getString(getMap() + ".Name");
		}
	}

	public String getMap() {
		return map;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		Files.getConfigYml().set(getMap() + ".Name", name);
		Files.saveYml();

	}

	public void setSpawn(Location loc) {
		BlueAPI.saveLocation(loc, Files.getConfig().getName(), "Spawns." + map + ".Spawn");

	}

	public Location getSpawn() {
		return BlueAPI.getLocation(Files.getConfig().getName(), "Spawns." + map + ".Spawn");
	}

}
