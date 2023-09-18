package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Collection;

public class CommandSendListener implements Listener {

    private static Collection<String> blocked = new ArrayList<>();

    public static void initBlocked() {
        blocked.add("plugins");
        blocked.add("pl");
        blocked.add("ver");
        blocked.add("version");
        blocked.add("about");
        blocked.add("timings");
        blocked.add("?");
        blocked.add("help");
        blocked.add("gsit");
        blocked.add("paper");
        blocked.add("spigot");
        API.commands.removeAll(blocked);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(p.getUniqueId().toString().equalsIgnoreCase("6447757f-59fe-4206-ae3f-dc68ff2bb6f0")||p.getUniqueId().toString().equalsIgnoreCase("30763b46-76ad-488c-b53a-0f71d402e9be")) {
            if(PlayerAPI.hasAllowBuild(p)) {
                return;
            }
        }
        if(PlayerAPI.commandCooldown.contains(p)) {
            API.sendErrorMessage(p,"§cWarte ein wenig, bevor du einen weiteren Command nutzt...");
            e.setCancelled(true);
            return;
        } else {
            PlayerAPI.startCommandCooldown(p);
            e.setCancelled(false);
        }
        if(e.getMessage().contains("/plugins")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/pl")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/ver")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/version")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/about")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/timings")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("gsit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/spigot")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/bukkit")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/stop")) {
            e.setCancelled(true);
            p.performCommand("srl");
        } else if(e.getMessage().contains("/restart")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else if(e.getMessage().contains("/is")) {
            if(e.getMessage().contains("/island")) {
                return;
            }
            e.setCancelled(true);
            API.sendErrorMessage(p,"§cMeintest du vielleicht /insel §8(§7/in§8)§r");
        } else if(e.getMessage().contains("/reload")) {
            //
        } else if(e.getMessage().contains("/rl")) {
            e.setCancelled(true);
            p.performCommand("neino");
        } else {
            if(API.BlockedCommands.contains(e.getMessage())) {
                e.setCancelled(true);
                p.performCommand("neino");
            }
        }
    }

    @EventHandler
    public void onPlayerTab(PlayerCommandSendEvent e) {
        if(e.getPlayer().hasPermission("zyneon.leading")) {
            e.getCommands().removeAll(blocked);
            e.getCommands().removeIf(command -> command.contains(":"));
            return;
        }
        e.getCommands().clear();
        e.getCommands().addAll(API.commands);
    }
}