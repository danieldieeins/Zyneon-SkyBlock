package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class AuthorCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Author")) {
            s.sendMessage("§8");
            s.sendMessage("§8");
            s.sendMessage("§8=================================================");
            s.sendMessage("§8");
            s.sendMessage("§9 Zyneon§8: §9SkyBlock "+ SkyBlock.getVersion());
            s.sendMessage("§f by §cnerotvlive§f.");
            s.sendMessage("§8");
            s.sendMessage("§8=================================================");
            s.sendMessage("§8");
            s.sendMessage("§8");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args) {
        List<String> completer = new ArrayList<>();
        return completer;
    }
}