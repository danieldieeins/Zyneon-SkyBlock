package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.HologramAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.ItemManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class HologramCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/hologram [create/§fremove§c] [text/§fx§c] [§fy§c] [§fz§c]");
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Hologram")) {
            if(s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("zyneon.team")) {
                    if (args.length == 0) {
                        sendSyntax(p);
                    } else if (args.length == 1||args.length == 2) {
                        if(args[0].equalsIgnoreCase("remove")) {
                            if(PlayerAPI.canPlayerBuild(p)) {
                                p.getInventory().addItem(ItemManager.entityRemover);
                            } else {
                                API.sendErrorMessage(p,"§cDu bist nicht im Baumodus!");
                            }
                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        if(args[0].equalsIgnoreCase("create")) {
                            String n = args[1];
                            String m = "";
                            for (int i = 2; i < args.length; i++) {
                                m=m+args[i]+" ";
                            }
                            m=m.substring(0,m.length()-1);
                            if(HologramAPI.createHologram(n,p,m)) {
                                API.sendMessage(p, "§7Du hast erfolgreich ein Hologram mit dem Namen §a" + n + " §7und dem Text §e" + m + "§7 erstellt§8.");
                            } else {
                                API.sendErrorMessage(s,"§cDas Hologram gibt es bereits!");
                            }
                        } else if(args[0].equalsIgnoreCase("addline")) {
                            String n = args[1];
                            String m = "";
                            for (int i = 2; i < args.length; i++) {
                                m=m+args[i]+" ";
                            }
                            m=m.substring(0,m.length()-1);
                            if (HologramAPI.addHologramLine(n,m)) {
                                API.sendMessage(p, "§7Du hast erfolgreich dem Hologram §a" + n + " §7die Zeile §e" + m + "§7 hinzugefügt§8.");
                            } else {
                                API.sendErrorMessage(s,"§cDas Hologram gibt es nicht!");
                            }
                        } else {
                            sendSyntax(p);
                        }
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
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
        if(args.length == 1) {
            completer.add("create");
            completer.add("remove");
        }
        return completer;
    }
}