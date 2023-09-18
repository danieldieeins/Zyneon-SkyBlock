package com.zyneonstudios.nerotvlive.skyblock.api;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.io.File;

public class HologramAPI {

    public static boolean createHologram(String name,World world, double x, double y, double z,String text) {
        File holoFile = new File("plugins/SkyBlock/Holograms.yml");
        YamlConfiguration hF = YamlConfiguration.loadConfiguration(holoFile);
        x = (int)x; x = x+0.5; y = y-1.5; z = (int)z; z = z+0.5;
        hF.set("Hologram."+name+".w",world.getName());
        hF.set("Hologram."+name+".x",x);
        hF.set("Hologram."+name+".y",y);
        hF.set("Hologram."+name+".z",z);
        hF.set("Hologram."+name+".lines",1);
        hF.set("Hologram."+name+".line1",text.replace("&","§"));
        ArmorStand armorStand = world.spawn(new Location(world,x,y,z),ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setCustomName(text.replace("&","§"));
        armorStand.setCustomNameVisible(true);
        ConfigAPI.saveConfig(holoFile,hF);
        return true;
    }

    public static boolean createHologram(String name, Player player,String text) {
        return createHologram(name,player.getLocation().getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ(),text);
    }

    public static boolean addHologramLine(String name, String text) {
        File holoFile = new File("plugins/SkyBlock/Holograms.yml");
        YamlConfiguration hF = YamlConfiguration.loadConfiguration(holoFile);
        if(hF.contains("Hologram."+name)) {
            String worldName = hF.getString("Hologram."+name+".w");
            double x = hF.getDouble("Hologram."+name+".x");
            double y = hF.getDouble("Hologram."+name+".y")-0.25;
            double z = hF.getDouble("Hologram."+name+".z");
            int l = hF.getInt("Hologram."+name+".lines");
            if(Bukkit.getWorld(worldName)==null) {
                Bukkit.createWorld(new WorldCreator(worldName));
            }
            World world = Bukkit.getWorld(worldName);
            ArmorStand armorStand = world.spawn(new Location(world,x,y,z),ArmorStand.class);
            armorStand.setVisible(false);
            armorStand.setInvulnerable(true);
            armorStand.setCustomName(text.replace("&","§"));
            armorStand.setCustomNameVisible(true);
            hF.set("Hologram."+name+".lines",l+1);
            hF.set("Hologram."+name+".y",y);
            hF.set("Hologram."+name+".line"+l+1,text.replace("&","§"));
            ConfigAPI.saveConfig(holoFile,hF);
            return true;
        } else {
            return false;
        }
    }
}