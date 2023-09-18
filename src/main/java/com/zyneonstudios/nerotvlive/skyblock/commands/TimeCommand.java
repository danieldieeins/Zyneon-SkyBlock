package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            s.sendMessage("§4Fehler: §c/time [set/add/remove] [Wert] [Welt]");
        } else {
            API.sendErrorMessage(s,"§4Fehler: §c/time [set/add/remove] [Wert] §7[Welt]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Time")) {
            if(!(s instanceof Player)) {
                if(args.length < 3) {
                    s.sendMessage("§4Fehler: §c/time [set/add/remove] [Wert] [Welt]");
                } else {
                    if (Bukkit.getWorld(args[2]) != null) {
                        World world = Bukkit.getWorld(args[2]);
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args[1].equalsIgnoreCase("day")) {
                                WorldAPI.setTime(0, world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eTag§7 gesetzt!");
                            } else if (args[1].equalsIgnoreCase("night")) {
                                WorldAPI.setTime(13500, world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eNacht§7 gesetzt!");
                            } else {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.setTime(Integer.parseInt(args[1]), world);
                                    API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §e" + args[1] + "§7 gesetzt!");
                                } else {
                                    sendSyntax(s);
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("add")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.addTime(Integer.parseInt(args[1]), world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 erhöht!");
                            } else {
                                sendSyntax(s);
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.removeTime(Integer.parseInt(args[1]), world);
                                API.sendMessage(s, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 verringert!");
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        s.sendMessage("§cDie Welt §4"+args[2]+"§c existiert nicht!");
                    }
                }
            } else {
                Player p = (Player)s;
                if(p.hasPermission("zyneon.team")) {
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args[1].equalsIgnoreCase("day")) {
                                WorldAPI.setTime(0, p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit auf §eTag§7 gesetzt!");
                            } else if (args[1].equalsIgnoreCase("night")) {
                                WorldAPI.setTime(13500, p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit auf §eNacht§7 gesetzt!");
                            } else {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.setTime(Integer.parseInt(args[1]), p.getWorld());
                                    API.sendMessage(p, "Du hast die Zeit auf §e" + args[1] + "§7 gesetzt!");
                                } else {
                                    sendSyntax(s);
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("add")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.addTime(Integer.parseInt(args[1]), p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit um §e" + args[1] + "§7 erhöht!");
                            } else {
                                sendSyntax(s);
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (API.isNumeric(args[1])) {
                                WorldAPI.removeTime(Integer.parseInt(args[1]), p.getWorld());
                                API.sendMessage(p, "Du hast die Zeit um §e" + args[1] + "§7 verringert!");
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            sendSyntax(s);
                        }
                    } else if (args.length > 2) {
                        if (Bukkit.getWorld(args[2]) != null) {
                            World world = Bukkit.getWorld(args[2]);
                            if (args[0].equalsIgnoreCase("set")) {
                                if (args[1].equalsIgnoreCase("day")) {
                                    WorldAPI.setTime(0, world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eTag§7 gesetzt!");
                                } else if (args[1].equalsIgnoreCase("night")) {
                                    WorldAPI.setTime(13500, world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §eNacht§7 gesetzt!");
                                } else {
                                    if (API.isNumeric(args[1])) {
                                        WorldAPI.setTime(Integer.parseInt(args[1]), world);
                                        API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 auf §e" + args[1] + "§7 gesetzt!");
                                    } else {
                                        sendSyntax(s);
                                    }
                                }
                            } else if (args[0].equalsIgnoreCase("add")) {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.addTime(Integer.parseInt(args[1]), world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 erhöht!");
                                } else {
                                    sendSyntax(s);
                                }
                            } else if (args[0].equalsIgnoreCase("remove")) {
                                if (API.isNumeric(args[1])) {
                                    WorldAPI.removeTime(Integer.parseInt(args[1]), world);
                                    API.sendMessage(p, "Du hast die Zeit der Welt §e"+world.getName()+"§7 um §e" + args[1] + "§7 verringert!");
                                } else {
                                    sendSyntax(s);
                                }
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            p.sendMessage("§cDie Welt §4"+args[2]+"§c existiert nicht!");
                            PlayerAPI.playNewSound(p, NewSound.BLOCK_ANVIL_BREAK);
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms, PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }
}