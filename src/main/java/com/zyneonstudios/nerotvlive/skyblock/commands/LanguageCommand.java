package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.InventoryManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class LanguageCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Language")) {
            if(!(s instanceof Player)) {
                API.sendErrorMessage(s,"§cKommt noch...");
            } else {
                Player p = (Player) s;
                if(args.length == 0) {
                    InventoryManager.openLanguageMenu(p);
                } else {
                    if(args[0].equalsIgnoreCase("auto")||args[0].equalsIgnoreCase("automatic")||args[0].equalsIgnoreCase("automatisch")) {
                        PlayerAPI.setLanguage(p, MessageManager.Language.AUTOMATIC);
                    } else if(args[0].equalsIgnoreCase("deutsch")||args[0].equalsIgnoreCase("german")) {
                        PlayerAPI.setLanguage(p, MessageManager.Language.GERMAN);
                    } else if(args[0].equalsIgnoreCase("english")||args[0].equalsIgnoreCase("englisch")) {
                        PlayerAPI.setLanguage(p, MessageManager.Language.ENGLISH);
                    } else {
                        InventoryManager.openLanguageMenu(p);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("auto");
            completer.add("automatic");
            completer.add("automatisch");
            completer.add("deutsch");
            completer.add("englisch");
            completer.add("english");
            completer.add("german");
        }
        return completer;
    }
}
