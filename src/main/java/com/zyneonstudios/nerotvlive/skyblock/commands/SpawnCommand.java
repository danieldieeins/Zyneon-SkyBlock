package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.WarpAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args) {
        if(cmd.getName().equalsIgnoreCase("Spawn")) {
            if(s instanceof Player p) {
                SkyUser sP = API.getSkyBlockPlayer(p.getUniqueId());
                if(WarpAPI.isWarpEnabled("spawn")) {
                    if(sP.isGrounded()) {
                        API.sendErrorMessage(s,"§cDu kannst dich derzeit nicht teleportieren!");
                    } else {
                        p.teleport(WarpAPI.getWarp("spawn"));
                        API.sendMessage(p, "§7Du bist nun am §eSpawn§8!");
                    }
                } else {
                    API.sendErrorMessage(s,"§cDer Spawn ist zurzeit nicht erreichbar.");
                }
            } else {
                API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NeedPlayer, MessageManager.Language.GERMAN));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        return completer;
    }
}