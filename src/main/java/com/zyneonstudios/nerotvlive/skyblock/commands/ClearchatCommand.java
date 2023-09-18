package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearchatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Clearchat")) {
            if(!(s instanceof Player)) {
                run(s);
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                    run(s);
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }

    private void run(CommandSender s) {
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(all.hasPermission("zyneon.team")) {
                API.sendMessage(all, MessageManager.getMessage(MessageManager.Message.Chatclear, PlayerAPI.getLanguage(all)));
            } else {
                for(int i=0; i<200; i++) {
                    all.sendMessage(" §0 ");
                }
            }
        }
        API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.Chatclear, MessageManager.Language.GERMAN));
    }
}