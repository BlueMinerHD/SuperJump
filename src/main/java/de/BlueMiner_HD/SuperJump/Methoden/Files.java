package de.BlueMiner_HD.SuperJump.Methoden;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.BlueMiner_HD.SuperJump.API.BlueAPI;

public class Files {

	static File config;
	static YamlConfiguration configyml;

	public static void loadLocations() {
		config = BlueAPI.getFile("config.yml");
		configyml = BlueAPI.getConfiguration(getConfig());

		if (!config.exists()) {
			saveYml();
		}

	}

	public static File getConfig() {
		return config;
	}

	public static YamlConfiguration getConfigYml() {
		return configyml;
	}

	public static void saveYml() {
		BlueAPI.saveFile(getConfig(), getConfigYml());
	}

}
