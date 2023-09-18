package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.IslandAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.SettingsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Rain")) {
            if(!(s instanceof Player)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"weather rain");
            } else {
                Player p = (Player)s;
                if(IslandAPI.isOwner(p)) {
                    SettingsAPI.toggleRain(p);
                    return false;
                }
                p.performCommand("weather rain");
            }
        }
        return false;
    }
}