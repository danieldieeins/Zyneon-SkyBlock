package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.WarpAPI;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        SkyUser u = API.getSkyBlockPlayer(p.getUniqueId());
        if(u.getIslandInt()==-1) {
            e.setCancelled(true);
        }
        if(p.getWorld().getName().equals("world")) {
            if(p.getLocation().getY()<0) {
                if(WarpAPI.isWarpEnabled("spawn")) {
                    p.teleport(WarpAPI.getWarp("spawn"));
                } else {
                    p.teleport(p.getWorld().getSpawnLocation());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player p) {
            SkyUser u = API.getSkyBlockPlayer(p.getUniqueId());
            if(u.getIslandInt()==-1) {
                e.setCancelled(true);
            }
            if(p.getWorld().getName().equals("world")) {
                e.setCancelled(true);
            }
        }
    }
}