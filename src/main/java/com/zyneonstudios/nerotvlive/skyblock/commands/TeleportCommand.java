package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    void sendSyntax(Player p) {
        PlayerAPI.sendPlayerMessage(p, "§4Fehler: §c/tp [X] [Y] [Z]§7, §c/tp [Spieler] §7[Spieler]§7 oder §c/tp [Spieler] [X] [Y] [Z]", NewSound.BLOCK_ANVIL_BREAK);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Teleport")) {
            if (!(s instanceof Player)) {
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Player p = Bukkit.getPlayer(args[0]);
                            Player t = Bukkit.getPlayer(args[1]);
                            p.teleport(t.getLocation());
                            PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                            s.sendMessage(API.Prefix + "Du hast den Spieler §e" + p.getName() + "§7 zu §e" + t.getName() + "§7 teleportiert!");
                        } else {
                            s.sendMessage("§cDer Spieler §4" + args[1] + "§c ist nicht online!");
                        }
                    } else {
                        s.sendMessage("§cDer Spieler §4" + args[0] + "§c ist nicht online!");
                    }
                } else if (args.length == 4) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (API.isNumericPart(args[1])) {
                            if (API.isNumericPart(args[2])) {
                                if (API.isNumericPart(args[3])) {
                                    Player p = Bukkit.getPlayer(args[0]);
                                    double x = Double.parseDouble(args[1]);
                                    double y = Double.parseDouble(args[2]);
                                    double z = Double.parseDouble(args[3]);
                                    p.teleport(new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch()));
                                    PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                                    s.sendMessage(API.Prefix + "Du hast den Spieler §e" + p.getName() + "§7 zu §aX" + x + " Y" + y + " Z" + z + "§7 teleportiert!");
                                } else {
                                    s.sendMessage("§4Fehler: §c/tp [Spieler] §7[Spieler]§7 oder §c/tp [Spieler] [X] [Y] [Z]");
                                }
                            } else {
                                s.sendMessage("§4Fehler: §c/tp [Spieler] §7[Spieler]§7 oder §c/tp [Spieler] [X] [Y] [Z]");
                            }
                        } else {
                            s.sendMessage("§4Fehler: §c/tp [Spieler] §7[Spieler]§7 oder §c/tp [Spieler] [X] [Y] [Z]");
                        }
                    } else {
                        API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPlayer, MessageManager.Language.GERMAN));
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/tp [Spieler] §7[Spieler]§7 oder §c/tp [Spieler] [X] [Y] [Z]");
                }
            } else {
                Player p = (Player) s;
                if (args.length == 0) {
                    sendSyntax(p);
                } else if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player t = Bukkit.getPlayer(args[0]);
                        p.teleport(t.getLocation());
                        API.sendMessage(p, "Du hast dich zu §e" + t.getName() + "§7 teleportiert!");
                    }
                } else if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Player p1 = Bukkit.getPlayer(args[0]);
                            Player p2 = Bukkit.getPlayer(args[1]);
                            p1.teleport(p2.getLocation());
                            PlayerAPI.playNewSound(p1, NewSound.ENTITY_CHICKEN_EGG);
                            API.sendMessage(p, "Du hast §e" + p1.getName() + "§7 zu §e" + p2.getName() + "§7 teleportiert!");
                        } else {
                            PlayerAPI.sendPlayerMessage(p, "§cDer Spieler §4" + args[0] + "§c ist nicht online!", NewSound.BLOCK_ANVIL_BREAK);
                        }
                    } else {
                        PlayerAPI.sendPlayerMessage(p, "§cDer Spieler §4" + args[0] + "§c ist nicht online!", NewSound.BLOCK_ANVIL_BREAK);
                    }
                } else if (args.length == 3) {
                    if (API.isNumericPart(args[0])) {
                        if (API.isNumericPart(args[1])) {
                            if (API.isNumericPart(args[2])) {
                                double x = Double.parseDouble(args[0]);
                                double y = Double.parseDouble(args[1]);
                                double z = Double.parseDouble(args[2]);
                                Location loc = new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                                p.teleport(loc);
                                API.sendMessage(p, "Du hast dich zu §eX" + x + " Y" + y + " Z" + z + "§7 teleportiert!");
                            } else {
                                sendSyntax(p);
                            }
                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        sendSyntax(p);
                    }
                } else {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (API.isNumericPart(args[1])) {
                            if (API.isNumericPart(args[2])) {
                                if (API.isNumericPart(args[3])) {
                                    Player t = Bukkit.getPlayer(args[0]);
                                    t.teleport(new Location(t.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[2]), t.getLocation().getYaw(), t.getLocation().getPitch()));
                                    PlayerAPI.playNewSound(t, NewSound.ENTITY_CHICKEN_EGG);
                                    API.sendMessage(p, "Du hast den Spieler §e" + t.getName() + "§7 zu §a X" + args[1] + " Y" + args[2] + " Z" + args[3] + "§7 teleportiert!");
                                } else {
                                    sendSyntax(p);
                                }
                            } else {
                                sendSyntax(p);
                            }
                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                    }
                }
            }
        }
        return false;
    }
}