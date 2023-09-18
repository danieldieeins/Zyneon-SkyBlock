package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI;
import org.bukkit.Bukkit;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class WorldCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("world")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(s.hasPermission("zyneon.leading.world")) {
                    if(args.length == 0) {
                        API.sendMessage(s,"§7Du befindest dich in der Welt§8: §e"+p.getWorld().getName());
                    } else {
                        if(Bukkit.getWorld("plugins/SkyBlock/Islands/"+args[0])==null) {
                            API.sendMessage(p,"§7Welt wird geladen...");
                            WorldAPI.createWorld(args[0],org.bukkit.World.Environment.NORMAL, WorldType.FLAT,false,true);
                        }
                        p.teleport(Bukkit.getWorld("plugins/SkyBlock/Islands/"+args[0]).getSpawnLocation());
                        API.sendMessage(p,"§7Du bist nun in der Welt§8: §e"+args[0]);
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms, PlayerAPI.getLanguage(p)));
                }
            } else {
                API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NeedPlayer, MessageManager.Language.GERMAN));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        return completer;
    }
}