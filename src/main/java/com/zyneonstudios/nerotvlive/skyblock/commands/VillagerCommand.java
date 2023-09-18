package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VillagerCommand implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/villager [profession]");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("Villager")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("remove")) {
                            p.performCommand("holo remove");
                        } else if (args[0].equalsIgnoreCase("wood")) {

                        } else if (args[0].equalsIgnoreCase("stone")) {

                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        sendSyntax(p);
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
}