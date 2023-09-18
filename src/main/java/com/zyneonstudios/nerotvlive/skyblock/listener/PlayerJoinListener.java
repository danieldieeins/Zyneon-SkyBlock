package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.*;
import com.zyneonstudios.nerotvlive.skyblock.manager.InventoryManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        SkyUser u = API.getSkyBlockPlayer(p.getUniqueId());
        p.setOp(false);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"deop "+p.getName());
        PlayerAPI.renewScoreboard(p);
        PlayerAPI.commandCooldown.remove(p);
        PlayerAPI.setBuildMode(p,false);
        String SID = p.getUniqueId().toString();
        PlayerAPI.registerPlayer(p);
        e.setJoinMessage("");
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        ConfigAPI.reloadConfig(playerFile,pF);
        if(ServerAPI.isMaintenance()) {
            if(p.hasPermission("zyneon.bypassmaintenance")) {
                for(Player all:Bukkit.getOnlinePlayers()) {
                    if(all.getUniqueId()!=p.getUniqueId()) {
                        all.sendMessage("§8» §a"+p.getName());
                    }
                }
                API.sendErrorMessage(p,"§cDer Server befindet sich zurzeit in §4Wartungsarbeiten§c!");
                if(u.getIslandInt()==-1) {
                    InventoryManager.openIslandMenu(p);
                } else {
                    WorldAPI.getBack(p);
                }
            } else {
                p.kickPlayer("§4SkyBlock§c ist momentan in §4Wartungsarbeiten§c!");
            }
        } else {
            for(Player all:Bukkit.getOnlinePlayers()) {
                if(all.getUniqueId()!=p.getUniqueId()) {
                    all.sendMessage("§8» §a"+p.getName());
                }
            }
            if(u.getIslandInt()==-1) {
                InventoryManager.openIslandMenu(p);
            } else {
                WorldAPI.getBack(p);
            }
        }
        p.setGameMode(GameMode.SPECTATOR);
    }
}