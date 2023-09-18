package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.WarpAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarpCommand implements CommandExecutor, TabCompleter {

    void sendES(Player p) {
        PlayerAPI.sendPlayerMessage(p, "§cDieser Warp existiert bereits!", NewSound.BLOCK_ANVIL_BREAK);
    }

    void sendESN(Player p) {
        PlayerAPI.sendPlayerMessage(p, "§cDieser Warp existiert nicht!", NewSound.BLOCK_ANVIL_BREAK);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Warp")) {

                if (!(s instanceof Player)) {
                    s.sendMessage("§cDazu §4musst§c du ein Spieler sein§4!");
                } else {
                    Player p = (Player) s;
                    if (args.length == 0) {
                        p.performCommand("warp list 1");
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("list")) {
                            if (p.hasPermission("zyneon.team")) {
                                p.performCommand("warp list 1");
                            } else {
                                p.performCommand("warp");
                            }
                        } else {
                            p.performCommand("warp");
                        }
                    } else {
                        String Warpname = args[1];
                        if (p.hasPermission("zyneon.team")) {
                            if (args[0].equalsIgnoreCase("set")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    sendES(p);
                                } else {
                                    WarpAPI.setWarp(Warpname, p, false);
                                    API.sendMessage(p, "Du hast §aerfolgreich den Warp-Punkt \"§e" + Warpname + "§7\" hinzugefügt. Nutze §f/warp enable " + Warpname + "§7 oder §f/warp toggle " + Warpname + "§7, um den Warp-Punkt zu aktivieren.");
                                }
                            } else if (args[0].equalsIgnoreCase("teleport")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    p.teleport(WarpAPI.getWarp(Warpname));
                                    PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("tp")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    p.teleport(WarpAPI.getWarp(Warpname));
                                    PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("enable")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.enableWarp(Warpname);
                                    API.sendMessage(p, "Du hast §aerfolgreich§7 den Warp \"§e" + Warpname + "§7\" aktiviert!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("disable")) {
                                if (WarpAPI.isWarpEnabled(Warpname)) {
                                    WarpAPI.disableWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp \"§e" + Warpname + "§7\" §cdeaktiviert§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("toggle")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    if (WarpAPI.isWarpEnabled(Warpname)) {
                                        p.performCommand("warp disable " + Warpname);
                                    } else {
                                        p.performCommand("warp enable " + Warpname);
                                    }
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("delete")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("remove")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("del")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("rem")) {
                                if (WarpAPI.ifWarpExists(Warpname)) {
                                    WarpAPI.removeWarp(Warpname);
                                    API.sendMessage(p, "Du hast erfolgreich den Warp-Punkt §e" + Warpname + "§c entfernt§7!");
                                } else {
                                    sendESN(p);
                                }
                            } else if (args[0].equalsIgnoreCase("list")) {
                                API.sendMessage(p, "§7Es existieren folgende Warp-Punkte:");
                                s.sendMessage(Arrays.toString(WarpAPI.getWarpList()));
                            } else {
                                p.performCommand("warp");
                            }
                        } else {
                            p.performCommand("warp");
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
            completer.add("teleport");
            completer.add("list");
            completer.add("set");
            completer.add("rem");
            completer.add("del");
            completer.add("remove");
            completer.add("delete");
            completer.add("enable");
            completer.add("disable");
            completer.add("toggle");
        }
        return completer;
    }
}