package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class BuildCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        MessageManager.Language lang;
        if(s instanceof Player) {
            lang = PlayerAPI.getLanguage((Player)s);
        } else {
            lang = MessageManager.Language.GERMAN;
        }
        API.sendErrorMessage(Bukkit.getConsoleSender(), MessageManager.getMessage(MessageManager.Message.Syntax_BUILD,lang));
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s instanceof Player)) {
            if(args.length == 0) {
                sendSyntax(s);
            } else {
                if(Bukkit.getPlayer(args[0])!=null) {
                    Player t = Bukkit.getPlayer(args[0]);
                    PlayerAPI.toggleBuildMode(t);
                    API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.Build_OTHER, MessageManager.Language.GERMAN).replace("%bool%",PlayerAPI.canPlayerBuild(t)+"").replace("%player%",t.getName()));
                    API.sendMessage(t, MessageManager.getMessage(MessageManager.Message.Build_SELF,PlayerAPI.getLanguage(t)).replace("%bool%",PlayerAPI.canPlayerBuild(t)+""));
                } else {
                    API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                }
            }
        } else {
            Player p = (Player)s;
            if(p.hasPermission("zyneon.team")) {
                if(args.length == 0) {
                    PlayerAPI.toggleBuildMode(p);
                    API.sendMessage(p, MessageManager.getMessage(MessageManager.Message.Build_SELF,PlayerAPI.getLanguage(p)).replace("%bool%",PlayerAPI.canPlayerBuild(p)+""));
                } else {
                    Player t = Bukkit.getPlayer(args[0]);
                    PlayerAPI.toggleBuildMode(t);
                    API.sendMessage(t, MessageManager.getMessage(MessageManager.Message.Build_OTHER, MessageManager.Language.GERMAN).replace("%bool%",PlayerAPI.canPlayerBuild(t)+"").replace("%player%",t.getName()));
                    API.sendMessage(t, MessageManager.getMessage(MessageManager.Message.Build_SELF,PlayerAPI.getLanguage(t)).replace("%bool%",PlayerAPI.canPlayerBuild(t)+""));
                }
            } else {
                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        completer.add("error");
        return completer;
    }
}