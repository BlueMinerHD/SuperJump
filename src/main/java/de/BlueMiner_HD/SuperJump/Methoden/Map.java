package de.BlueMiner_HD.SuperJump.Methoden;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Map {

    private String map;
    private String name;

    public Map(String map) {
        this.map = map;
        this.name = map;
        addMap(map);
        setName(map);

        setItemID(1);
        setItemSubID(0);
        setItemDisplayName(map);
    }

    public Map(String map, String name) {
        this.map = map;
        addMap(map);
        setName(name);

        setItemID(1);
        setItemSubID(0);
        setItemDisplayName(name);
    }

    private Map(String map, int id) {
        this.map = map;
        if (Files.getConfigYml().getString(getMap() + ".Name") != null) {
            name = Files.getConfigYml().getString(getMap() + ".Name");
        }
    }

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

    public Location getSpawn() {
        return BlueAPI.getLocation(Files.getConfig().getName(), map + ".Spawn");
    }

    public void setSpawn(Location loc) {
        BlueAPI.saveLocation(loc, Files.getConfig().getName(), map + ".Spawn");

    }

    public int getItemID() {
        return Files.getConfigYml().getInt(getMap() + ".Item.ID");
    }

    public void setItemID(int id) {
        Files.getConfigYml().set(getMap() + ".Item.ID", id);
        Files.saveYml();
    }

    public int getItemSubID() {
        return Files.getConfigYml().getInt(getMap() + ".Item.SubID");
    }

    public void setItemSubID(int subid) {
        Files.getConfigYml().set(getMap() + ".Item.SubID", subid);
        Files.saveYml();
    }

    public String getItemDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', Files.getConfigYml().getString(getMap() + ".Item.displayName"));
    }

    public void setItemDisplayName(String displayName) {
        Files.getConfigYml().set(getMap() + ".Item.displayName", displayName);
        Files.saveYml();
    }

    public ItemStack getItem() {
        return new ItemManager(getItemID(), (short) getItemSubID(), 1).
                setDisplayName(getItemDisplayName()).addLoreLine("§3Votes: " + Methoden.voteMaps.get(this)).build();
    }

}
