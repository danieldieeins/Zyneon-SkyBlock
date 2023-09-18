package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        SkyUser sP = API.getSkyBlockPlayer(p.getUniqueId());
        PlayerAPI.commandCooldown.remove(p);
        String SID = p.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        PlayerAPI.saveLastLoc(p);
        e.setQuitMessage("");
        if(ServerAPI.isMaintenance()) {
            if(p.hasPermission("zyneon.bypassmaintenance")) {
                Bukkit.broadcastMessage("§8« §c"+p.getName());
            }
        } else {
            Bukkit.broadcastMessage("§8« §c"+p.getName());
        }
        if(!WorldAPI.hasActiveTeammember(p.getUniqueId().toString())) {
            WorldAPI.unloadIsland(p.getUniqueId().toString());
        }
        if(pF.getBoolean("Team.Enable")) {
            WorldAPI.unloadIsland(pF.getString("Team.Player"));
        }
        API.skyBlockPlayers.remove(p.getUniqueId());
        sP.destroy();
    }
}