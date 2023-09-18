package com.zyneonstudios.nerotvlive;

import com.zyneonstudios.nerotvlive.skyblock.api.PluginChannel;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Preloader extends JavaPlugin {

    private static PluginChannel pluginChannel;
    private static Preloader instance;
    private static String version = "v";
    public static String getVersion() { if(version.equalsIgnoreCase("v")) { return "0"; } else { return version; } }
    public static Preloader getInstance() { return instance; }
    public static PluginChannel getPluginChannel() { return pluginChannel; }

    @Override
    public void onLoad() {
        if(ServerAPI.isLegacy()) {
            return;
        }
        sendMessage("Loading SkyBlock preloader...");
        version = getDescription().getVersion();
        if(version.replace("-","").replace("experimentel","experimental").replace(" ","").toLowerCase().contains("experimental")) {
            pluginChannel = PluginChannel.INDEV;
        } else if(version.replace("-","").replace(" ","").toLowerCase().contains("prerelease")) {
            pluginChannel = PluginChannel.PRERELEASE;
        } else if(version.replace("-","").replace(" ","").toLowerCase().contains("beta")) {
            pluginChannel = PluginChannel.BETA;
        } else if(version.replace("-","").replace(" ","").toLowerCase().contains("indev")) {
            pluginChannel = PluginChannel.INDEV;
        } else {
            pluginChannel = PluginChannel.STABLE;
        }
        sendMessage("Loading SkyBlock (Channel: "+pluginChannel+")...");
        SkyBlock.onLoad();
    }

    @Override
    public void onEnable() {
        if(ServerAPI.isLegacy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        sendMessage("Checking for plugin dependencies...");
        if(API.PM.getPlugin("Vault")==null) {
            sendMessage("Couldn't find Vault, disabling Economy features!");
        }
        sendMessage("Enabling SkyBlock...");
        instance = this;
        SkyBlock.onEnable();
    }

    @Override
    public void onDisable() {
        if(ServerAPI.isLegacy()) {
            return;
        }
        sendMessage("Disabling SkyBlock plugin...");
        if(getDescription().getVersion().equals(getVersion())) {
            SkyBlock.onDisable(false);
        } else {
            SkyBlock.onDisable(true);
        }
        version = null;
        instance = null;
    }

    private void sendMessage(String message) {
        API.sendErrorMessage(Bukkit.getConsoleSender(),"§7[SkyBlock] §e[PRELOADER] §7"+message);
    }
}