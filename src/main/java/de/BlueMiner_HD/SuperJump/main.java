package de.BlueMiner_HD.SuperJump;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;
import de.BlueMiner_HD.SuperJump.API.MySQL;
import de.BlueMiner_HD.SuperJump.Commands.*;
import de.BlueMiner_HD.SuperJump.Methoden.Files;
import de.BlueMiner_HD.SuperJump.Methoden.Map;
import de.BlueMiner_HD.SuperJump.Methoden.Methoden;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    private static main instance;
    private static String prefix = "§6§lSuperJump §8× §7";

    @Override
    public void onEnable() {
        instance = this;

        registerClasses();
        registerCommands();
        if (!Map.getMaps().isEmpty()) {
            randomMap();
            resetWorld();
            registerEvents();
        }
    }


    @Override
    public void onDisable() {
        if(MySQL.isConnected()) {
            MySQL.close();
        }

    }

    private void registerEvents() {
    }


    private void registerCommands() {

        getCommand("setup").setExecutor(new CMD_setup());
        getCommand("build").setExecutor(new CMD_build());
        getCommand("start").setExecutor(new CMD_start());
        getCommand("forcemap").setExecutor(new CMD_forcemap());
        getCommand("stats").setExecutor(new CMD_stats());
    }

    private void registerClasses() {
        MySQL.loadMySQLFiles();
        Files.loadLocations();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    public static void randomMap() {

        if (Map.getMaps().size() == 1) {
            Methoden.map = Map.getMap(Map.getMaps().get(0));
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(0)));
        } else if (Map.getMaps().size() == 2) {
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(0)));
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(1)));
        } else if (Map.getMaps().size() == 3) {
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(0)));
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(1)));
            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(2)));
        } else {
            int rnd = BlueAPI.getRandom(Map.getMaps().size());

            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(rnd)));

            rnd = BlueAPI.getRandom(Map.getMaps().size());

            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(rnd)));

            rnd = BlueAPI.getRandom(Map.getMaps().size());

            Methoden.voteMaps.add(Map.getMap(Map.getMaps().get(rnd)));
        }
    }

    public static void resetWorld() {

        for (String strmap : Map.getMaps()) {
            Map map = Map.getMap(strmap);
            if (map.getSpawn() != null) {
                World world = map.getSpawn().getWorld();
                for (Entity e : world.getEntities()) {
                    if (e.getType() != EntityType.ARMOR_STAND) {
                        e.remove();
                    }
                }
                world.setStorm(false);
                world.setThundering(false);
                world.setTime(2000);
                world.setGameRuleValue("doDaylightCycle", "false");
                world.setGameRuleValue("mobGriefing", "false");
                world.setGameRuleValue("doEntityDrops", "true");
                world.setGameRuleValue("doFireTick", "false");
                world.setGameRuleValue("doMobLoot", "false");
                world.setGameRuleValue("naturalRegeneration", "true");
                world.setDifficulty(Difficulty.PEACEFUL);
            }
        }
        if (Methoden.getLobbyLocation() != null) {
            World world = Methoden.getLobbyLocation().getWorld();
            for (Entity e : world.getEntities()) {
                if (e.getType() != EntityType.ARMOR_STAND) {
                    e.remove();
                }
            }
            world.setStorm(false);
            world.setThundering(false);
            world.setTime(2000);
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setGameRuleValue("mobGriefing", "false");
            world.setGameRuleValue("doEntityDrops", "true");
            world.setGameRuleValue("doFireTick", "false");
            world.setGameRuleValue("doMobLoot", "false");
            world.setGameRuleValue("naturalRegeneration", "true");
            world.setDifficulty(Difficulty.PEACEFUL);
        }

    }

    public static main getInstance() {
        return instance;
    }

    public static String getPrefix() {
        return prefix;
    }
}
