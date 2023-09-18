package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("God")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/god [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        if(t.isInvulnerable()) {
                            t.setInvulnerable(false);
                            API.sendMessage(t,"Du bist nun nicht mehr unsterblich!");
                            API.sendMessage(s,"Der Spieler §e"+t.getName()+"§7 ist nun nicht mehr unsterblich!");
                        } else {
                            t.setInvulnerable(true);
                            API.sendMessage(t,"Du bist nun unsterblich!");
                            API.sendMessage(s,"Der Spieler §e"+t.getName()+"§7 ist nun unsterblich!");
                        }
                    } else {
                        API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                    }
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        if(p.isInvulnerable()) {
                            p.setInvulnerable(false);
                            API.sendMessage(p,"Du bist nun nicht mehr unsterblich!");
                        } else {
                            p.setInvulnerable(true);
                            API.sendMessage(p,"Du bist nun unsterblich!");
                        }
                    } else {
                        if(Bukkit.getPlayer(args[0])!=null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            if(t.isInvulnerable()) {
                                t.setInvulnerable(false);
                                API.sendMessage(t,"Du bist nun nicht mehr unsterblich!");
                                API.sendMessage(s,"Der Spieler §e"+t.getName()+"§7 ist nun nicht mehr unsterblich!");
                            } else {
                                t.setInvulnerable(true);
                                API.sendMessage(t,"Du bist nun unsterblich!");
                                API.sendMessage(s,"Der Spieler §e"+t.getName()+"§7 ist nun unsterblich!");
                            }
                        } else {
                            API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                        }
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms, PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }
}
