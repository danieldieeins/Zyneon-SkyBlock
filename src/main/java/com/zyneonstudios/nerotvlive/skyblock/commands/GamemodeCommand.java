package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player) {
            API.sendErrorMessage((Player)s, MessageManager.getMessage(MessageManager.Message.Syntax_GAMEMODE,PlayerAPI.getLanguage((Player)s)));
        } else {
            API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.Syntax_GAMEMODE, MessageManager.Language.GERMAN));
        }
    }

    public boolean onCommand(final CommandSender s, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (s.hasPermission("zyneon.team")) {
                if (!(s instanceof Player)) {
                    if (args.length < 2) {
                        sendSyntax(s);
                    }
                    else {
                        final Player p = Bukkit.getPlayer(args[1]);
                        if (p == null) {
                            API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                        }
                        else {
                            API.changeGamemode(p, args[0]);
                            API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode, MessageManager.Language.GERMAN).replace("%gamemode%",API.getGamemode(p).replace("%player%",p.getName())));
                        }
                    }
                    return true;
                }
                else if (args.length == 0) {
                    Player p = (Player)s;
                    InventoryManager.openInv_GameMode(p);
                }
                else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("0")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("1")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("2")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("3")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("Survival")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("creative")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("adventure")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else if (args[0].equalsIgnoreCase("spectator")) {
                        final Player p = (Player)s;
                        API.changeGamemode(p, args[0]);
                    }
                    else {
                        final Player p = (Player)s;
                        InventoryManager.openInv_GameMode(p);
                    }
                }
                else {
                    Player sP = (Player)s;
                    final Player p = Bukkit.getPlayer(args[1]);
                    if (p == null) {
                        API.sendErrorMessage(sP, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(sP)));
                    }
                    else if (args[0].equalsIgnoreCase("0")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("1")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("2")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("3")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("SURVIVAL")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("CREATIVE")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("ADVENTURE")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else if (args[0].equalsIgnoreCase("SPECTATOR")) {
                        API.changeGamemode(p, args[0]);
                        API.sendMessage(sP, MessageManager.getMessage(MessageManager.Message.ChangedAnotherGamemode,PlayerAPI.getLanguage(sP)).replace("%player%",p.getName()).replace("%gamemode%",API.getGamemode(p)));
                    }
                    else {
                        API.sendErrorMessage(sP, MessageManager.getMessage(MessageManager.Message.Syntax_GAMEMODE,PlayerAPI.getLanguage(sP)));
                    }
                }
            } else {
                Player sP = (Player)s;
                API.sendErrorMessage(sP, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(sP)));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("survival");
            completer.add("creative");
            completer.add("adventure");
            completer.add("spectator");
            completer.add("0");
            completer.add("1");
            completer.add("2");
            completer.add("3");
        } else if(args.length == 2) {
            completer.add("error");
        }
        return completer;
    }
}