package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Sudo")) {
            if(!(s instanceof Player)) {
                if(args.length >= 2) {
                    Player p2 = Bukkit.getPlayer(args[0]);
                    String c="";
                    for(int i=1;i<args.length;i++) {
                        c=c+args[i]+" ";
                    }
                    p2.performCommand(c);
                    s.sendMessage(API.Prefix+"Du hast §e"+p2.getName()+"§7 dazu gezwungen §e/"+c+"§7auszuführen.");
                } else {
                    s.sendMessage("§4Fehler: §c/sudo [Spieler] [Aktion]");
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.leading.sudo")) {
                    if(args.length >= 2) {
                        if(Bukkit.getPlayer(args[0]) != null) {
                            Player p2 = Bukkit.getPlayer(args[0]);
                            String c="";
                            for(int i=1;i<args.length;i++) {
                                c=c+args[i]+" ";
                            }
                            p2.performCommand(c);
                            API.sendMessage(p,"Du hast §e"+p2.getName()+"§7 dazu gezwungen §e/"+c+"§7auszuführen.");
                        } else {
                            if(args[0].equalsIgnoreCase("console")) {
                                String c="";
                                for(int i=1;i<args.length;i++) {
                                    c=c+args[i]+" ";
                                }
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),c);
                                API.sendMessage(p,"Du hast §edie Konsole§7 dazu gezwungen §e/"+c+"§7auszuführen.");
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                            }
                        }
                    } else {
                        API.sendErrorMessage(s,"§4Fehler: §c/sudo [Spieler] [Aktion]");
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms, PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }
}