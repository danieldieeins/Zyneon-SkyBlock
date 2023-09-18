package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import java.io.File;
import static com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI.copyWorld;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onNether(EntityPortalEnterEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (p.getWorld().getName().contains("_world")) {
                if (Bukkit.getWorld(p.getWorld().getName().replace("_world", "_nether")) == null) {
                    if (!new File(p.getWorld().getName().replace("_world", "_nether")).exists()) {
                        copyWorld(new File("plugins/SkyBlock/Islands/tja_nether"), new File(p.getWorld().getName().replace("_world", "_nether")));
                    }
                    WorldAPI.createWorld(p.getWorld().getName().replace("plugins/SkyBlock/Islands/", "").replace("_world", "_nether"), World.Environment.NETHER, WorldType.FLAT, true, true);
                }
                Location worldSpawn = new Location(Bukkit.getWorld(p.getWorld().getName().replace("_world", "_nether")), 0.5, 75, 0.5, 90, 0);
                p.teleport(worldSpawn);
            } else {
                if (Bukkit.getWorld(p.getWorld().getName().replace("_nether", "_world")) == null) {
                    if (!new File(p.getWorld().getName().replace("_nether", "_world")).exists()) {
                        copyWorld(new File("plugins/SkyBlock/Islands/tja"), new File(p.getWorld().getName().replace("_nether", "_world")));
                    }
                    WorldAPI.createWorld(p.getWorld().getName().replace("plugins/SkyBlock/Islands/", "").replace("_nether", "_world"), World.Environment.NORMAL, WorldType.FLAT, false, true);
                }
                Location worldSpawn = new Location(Bukkit.getWorld(p.getWorld().getName().replace("_nether", "_world")), 0.5, 75, 0.5, 90, 0);
                p.teleport(worldSpawn);
            }
        }
    }
}