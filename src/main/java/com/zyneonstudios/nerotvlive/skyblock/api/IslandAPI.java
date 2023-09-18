package com.zyneonstudios.nerotvlive.skyblock.api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class IslandAPI {

    public static boolean isOwner(Player player) {
        return isOwner(player, player.getWorld());
    }

    public static boolean isOwner(Player player, World world) {
        return world.getName().replace("plugins/SkyBlock/Islands/", "").replace("_world", "").replace("_nether", "").replace("_end", "").equals(player.getUniqueId().toString());
    }

    public static boolean isMember(Player player) {
        return isMember(player,player.getWorld());
    }

    public static boolean isMember(Player player, World world) {
        if(isOwner(player,world)) {
            return true;
        } else {
            String SID = player.getWorld().getName().replace("plugins/SkyBlock/Islands/","").replace("_world","").replace("_nether","").replace("_end","");
            File targetFile = new File("plugins/SkyBlock/Players/"+SID.replace("-","")+".yml");
            YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
            if(targetFile.exists()&&tF.contains("Insel.Mitglieder")) {
                ArrayList<String> members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
                return members.contains(player.getUniqueId().toString());
            } else {
                return false;
            }
        }
    }

    public static boolean canBuild(Player player) {
        return canBuild(player,player.getWorld());
    }

    public static boolean canBuild(Player player,World world) {
        if(isOwner(player,world)) {
            return true;
        } else if(isMember(player,world)) {
            return true;
        } else return PlayerAPI.canPlayerBuild(player);
    }

    public static World getIsland(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(Bukkit.getWorld("plugins/SkyBlock/Islands/"+player.getUniqueId().toString().replace("-","")+"_world")==null) {
            if(!new File("plugins/SkyBlock/Islands/"+SID+"_world").exists()) {
                WorldAPI.copyWorld(new File("plugins/SkyBlock/Islands/tja"),new File("plugins/SkyBlock/Islands/"+SID+"_world"));
            }
            WorldAPI.createWorld(SID + "_world", World.Environment.NORMAL, WorldType.FLAT, false, true);
        }
        if(Bukkit.getWorld("plugins/SkyBlock/Islands/"+player.getUniqueId().toString().replace("-","")+"_nether")==null) {
            if(!new File("plugins/SkyBlock/Islands/"+SID+"_nether").exists()) {
                WorldAPI.copyWorld(new File("plugins/SkyBlock/Islands/tja_nether"),new File("plugins/SkyBlock/Islands/"+SID+"_nether"));
            }
            WorldAPI.createWorld(SID+"_nether",World.Environment.NETHER,WorldType.FLAT,true,true);
        }
        return Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world");
    }
}