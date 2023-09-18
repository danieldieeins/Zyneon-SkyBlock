package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("SRL")) {
            if(s.hasPermission("zyneon.leading")) {
                MessageManager.Language language;
                if(s instanceof Player) {
                    language = PlayerAPI.getLanguage((Player)s);
                } else {
                    language = MessageManager.Language.GERMAN;
                }
                if(ServerAPI.scheduledShutdown()) {
                    API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.restart_START,language));
                } else {
                    API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.restart_ERROR,language));
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
}
