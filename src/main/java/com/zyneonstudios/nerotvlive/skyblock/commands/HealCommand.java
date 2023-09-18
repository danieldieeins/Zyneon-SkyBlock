package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Heal")) {
            if(!(s instanceof Player)) {
                if(args.length == 0) {
                    s.sendMessage("§4Fehler: §c/heal [Spieler]");
                } else {
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p = Bukkit.getPlayer(args[0]);
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.setFireTicks(0);
                        API.sendMessage(p,"Du wurdest §ageheilt§7!");
                        s.sendMessage(API.Prefix+"Du hast den Spieler §e"+p.getName()+"§a geheilt§7!");
                    } else {
                        API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                    }
                }
            } else {
                Player p = (Player) s;
                if(s.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        p.setHealth(20);
                        p.setFoodLevel(20);
                        p.setFireTicks(0);
                        API.sendMessage(p, "Du hast dich §ageheilt§7!");
                    } else {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            t.setHealth(20);
                            t.setFoodLevel(20);
                            t.setFireTicks(0);
                            API.sendMessage(t, "Du wurdest §ageheilt§7!");
                            API.sendMessage(p, "Du hast den Spieler §e" + t.getName() + "§a geheilt§7!");
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