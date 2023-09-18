package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Maintenance")) {
            if(s.hasPermission("zyneon.leading.maintenance")) {
                if(args.length == 0) {
                    ServerAPI.toggleMaintenance();
                    API.sendMessage(s,"§7Der §aMaintenance-Modus§7 steht nun auf§8: §e"+ServerAPI.isMaintenance());
                } else {
                    if(args[0].equalsIgnoreCase("true")||args[0].equalsIgnoreCase("on")) {
                        ServerAPI.setMaintenance(true);
                        API.sendMessage(s,"§7Der §aMaintenance-Modus§7 steht nun auf§8: §e"+ServerAPI.isMaintenance());
                    } else if(args[0].equalsIgnoreCase("false")||args[0].equalsIgnoreCase("off")) {
                        ServerAPI.setMaintenance(false);
                        API.sendMessage(s,"§7Der §aMaintenance-Modus§7 steht nun auf§8: §e"+ServerAPI.isMaintenance());
                    }
                }
                if(ServerAPI.isMaintenance()) {
                    for(Player all:Bukkit.getOnlinePlayers()) {
                        if(!all.hasPermission("zyneon.bypassmaintenance")) {
                            all.kickPlayer("§4SkyBlock§c ist momentan in §4Wartungsarbeiten§c!");
                        }
                    }
                }
            } else {
                if(s instanceof Player) {
                    API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPerms, PlayerAPI.getLanguage((Player)s)));
                } else {
                    API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPerms, MessageManager.Language.GERMAN));
                }
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