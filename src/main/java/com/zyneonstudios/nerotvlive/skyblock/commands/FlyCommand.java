package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Fly")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/fly [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p = Bukkit.getPlayer(args[0]);
                        if(p.getAllowFlight()) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            API.sendMessage(p, "Du kannst nun nicht mehr fliegen!");
                            s.sendMessage(API.Prefix+"§e"+p.getName()+"§7 kann nun nicht mehr fliegen!");
                        } else {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            API.sendMessage(p, "Du kannst nun fliegen!");
                            s.sendMessage(API.Prefix+"§e"+p.getName()+"§7 kann nun fliegen!");
                        }
                    } else {
                        s.sendMessage("§cDieser Spieler ist nicht online!");
                    }
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        if(p.getAllowFlight()) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            API.sendMessage(p,"Du kannst nun nicht mehr fliegen!");
                        } else {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            API.sendMessage(p,"Du kannst nun fliegen!");
                        }
                    } else {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            Player p2 = Bukkit.getPlayer(args[0]);
                            if(p2.getAllowFlight()) {
                                p2.setAllowFlight(false);
                                p2.setFlying(false);
                                API.sendMessage(p2, "Du kannst nun nicht mehr fliegen!");
                                API.sendMessage(p, "§e" + p2.getName() + "§7 kann nun nicht mehr fliegen!");
                            } else {
                                p2.setAllowFlight(true);
                                p2.setFlying(true);
                                API.sendMessage(p2, "Du kannst nun fliegen!");
                                API.sendMessage(p, "§e" + p2.getName() + "§7 kann nun fliegen!");
                            }
                        } else {
                            API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(p)));
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