package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Feed")) {
            if(!(s instanceof Player p)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/feed [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p = Bukkit.getPlayer(args[0]);
                        User u = Zyneon.getOnlineUsers().get(p.getUniqueId());
                        p.setFoodLevel(20);
                        u.sendMessage("Du wurdest §agefüttert§7!");
                        s.sendMessage(API.Prefix+"Du hast den Spieler §e"+p.getName()+"§a gefüttert§7!");
                    } else {
                        API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                    }
                }
            } else {
                User u = Zyneon.getOnlineUsers().get(p.getUniqueId());
                if(s.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        p.setFoodLevel(20);
                        u.sendMessage("Du hast dich §agefüttert§7!");
                    } else {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            User tU = Zyneon.getOnlineUsers().get(t.getUniqueId());
                            t.setFoodLevel(20);
                            tU.sendMessage("Du wurdest §agefüttert§7!");
                            u.sendMessage("Du hast den Spieler §e" + t.getName() + "§a gefüttert§7!");
                        } else {
                            API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                        }
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }
}