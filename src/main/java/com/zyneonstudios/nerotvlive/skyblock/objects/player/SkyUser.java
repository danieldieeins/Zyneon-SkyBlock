package com.zyneonstudios.nerotvlive.skyblock.objects.player;

import com.zyneonstudios.api.paper.configuration.Config;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.UUID;

public class SkyUser extends User {

    private Config userConfig;
    private int islandInt;
    private File island_one;
    private File island_two;
    private File island_three;

    public SkyUser(UUID uuid) {
        super(uuid);
        this.userConfig = new Config("plugins/SkyBlock/Islands/"+uuid.toString()+"/settings.yml");
        this.userConfig.checkEntry("Settings.islandInt",-1);
        this.userConfig.saveConfig();
        this.userConfig.reloadConfig();
        this.islandInt = userConfig.getCFG().getInt("Settings.islandInt");
        this.island_one = new File("plugins/Islands/"+uuid+"/1");
        this.island_two = new File("plugins/Islands/"+uuid+"/2");
        this.island_three = new File("plugins/Islands/"+uuid+"/3");
    }

    public File getIslandOne() {
        return island_one;
    }

    public File getIslandTwo() {
        return island_two;
    }

    public File getIslandThree() {
        return island_three;
    }

    public int getIslandInt() {
        return islandInt;
    }

    public void setIslandInt(int i) {
        userConfig.set("Settings.islandInt",i);
        userConfig.saveConfig();
        userConfig.reloadConfig();
        this.islandInt = userConfig.getCFG().getInt("Settings.islandInt");
    }

    public boolean isGrounded() {
        if (getPlayer() != null) {
            Player p = getPlayer();
            if (p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("slab") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("stairs") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("farmland") || p.getWorld().getBlockAt(p.getLocation()).getType().toString().toLowerCase().contains("path")) {
                return false;
            } else if (API.TeleportBlocks.contains(p.getWorld().getBlockAt(p.getLocation()).getType().toString())) {
                return false;
            } else if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.AIR)) {
                if (!p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.VOID_AIR)) {
                    return p.getWorld().getBlockAt(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ())).getType().equals(Material.CAVE_AIR);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void destroy() {
        islandInt = 0;
        island_one = null;
        island_two = null;
        island_three = null;
        userConfig = null;
        System.gc();
    }
}