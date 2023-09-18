package com.zyneonstudios.nerotvlive.skyblock.api;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ConfigAPI {

    public static File config = new File("plugins/SkyBlock/config.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(config);
    public static YamlConfiguration CFG = cfg;

    public static void checkEntry(String path, Object content, File file, YamlConfiguration cfg) {
        reloadConfig(file,cfg);
        if(!(file.exists())) {
            cfg.set(path, content);
            saveConfig(file,cfg);
        } else {
            if(!(cfg.contains(path))) {
                cfg.set(path, content);
                saveConfig(file,cfg);
            }
        }
    }

    public static void saveConfig(final File file, final YamlConfiguration cfg) {
        try {
            cfg.save(file);
        }
        catch (IOException ef) {
            ef.printStackTrace();
        }
        reloadConfig(file, cfg);
    }

    public static void reloadConfig(final File file, YamlConfiguration cfg) {
        cfg = YamlConfiguration.loadConfiguration(file);
    }
}