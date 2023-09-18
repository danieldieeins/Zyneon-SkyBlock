package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            s.sendMessage("§4Fehler:§c /vanish [Spieler]");
        } else {
            API.sendErrorMessage(s,"§4Fehler:§c /vanish §7[Spieler]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Vanish")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    sendSyntax(s);
                } else {
                    if(Bukkit.getPlayer(args[0])!=null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        if(t.isInvisible()) {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                all.showPlayer(t);
                            }
                            t.setInvisible(false);
                            API.sendMessage(t,"Du bist nun nicht mehr unsichtbar!");
                            Bukkit.broadcastMessage("§8» §a"+t.getName());
                            API.sendMessage(s,"Du hast den Spieler §e"+t.getName()+"§7 sichtbar gemacht!");
                        } else {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                if(!all.hasPermission("zyneon.team")) {
                                    all.hidePlayer(t);
                                }
                            }
                            t.setInvisible(true);
                            API.sendMessage(t,"Du bist nun unsichtbar!");
                            Bukkit.broadcastMessage("§8« §c"+t.getName());
                            API.sendMessage(s,"Du hast den Spieler §e"+t.getName()+"§7 unsichtbar gemacht!");
                        }
                    } else {
                        API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                    }
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        if(p.isInvisible()) {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                all.showPlayer(p);
                            }
                            p.setInvisible(false);
                            Bukkit.broadcastMessage("§8» §a"+p.getName());
                            API.sendMessage(s,"Du bist nun nicht mehr unsichtbar!");
                        } else {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                if(!all.hasPermission("zyneon.team")) {
                                    all.hidePlayer(p);
                                }
                            }
                            p.setInvisible(true);
                            Bukkit.broadcastMessage("§8« §c"+p.getName());
                            API.sendMessage(s,"Du bist nun unsichtbar!");
                        }
                    } else {
                        if(Bukkit.getPlayer(args[0])!=null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            if(t.isInvisible()) {
                                t.setInvisible(false);
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    all.showPlayer(t);
                                }
                                API.sendMessage(t,"Du bist nun nicht mehr unsichtbar!");
                                Bukkit.broadcastMessage("§8» §a"+t.getName());
                                API.sendMessage(s,"Du hast den Spieler §e"+t.getName()+"§7 sichtbar gemacht!");
                            } else {
                                t.setInvisible(true);
                                for(Player all : Bukkit.getOnlinePlayers()) {
                                    if(!all.hasPermission("zyneon.team")) {
                                        all.hidePlayer(t);
                                    }
                                }
                                API.sendMessage(t,"Du bist nun unsichtbar!");
                                Bukkit.broadcastMessage("§8« §c"+t.getName());
                                API.sendMessage(s,"Du hast den Spieler §e"+t.getName()+"§7 unsichtbar gemacht!");
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