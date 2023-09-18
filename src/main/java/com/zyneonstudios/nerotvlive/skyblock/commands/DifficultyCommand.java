package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DifficultyCommand implements CommandExecutor {

    //HIER

    private void sendSyntax(CommandSender s) {
        if(!(s instanceof Player)) {
            s.sendMessage("§4Fehler: §c/difficulty [Schwierigkeit] [Welt]");
        } else {
            Player p = (Player)s;
            PlayerAPI.playNewSound(p, NewSound.BLOCK_ANVIL_BREAK);
            s.sendMessage("§4Fehler: §c/difficulty [Schwierigkeit] §7[Welt]");
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Difficulty")) {
            if(!(s instanceof Player p)) {
                if(args.length == 2) {
                    if(Bukkit.getWorld(args[1]) != null) {
                        World world = Bukkit.getWorld(args[1]);
                        if (args[0].equalsIgnoreCase("peaceful")) {
                            world.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eFriedlich §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("easy")) {
                            world.setDifficulty(org.bukkit.Difficulty.EASY);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eLeicht §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("normal")) {
                            world.setDifficulty(org.bukkit.Difficulty.NORMAL);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eNormal §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("hard")) {
                            world.setDifficulty(org.bukkit.Difficulty.HARD);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eSchwer §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("0")) {
                            world.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eFriedlich §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("1")) {
                            world.setDifficulty(org.bukkit.Difficulty.EASY);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eLeicht §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("2")) {
                            world.setDifficulty(org.bukkit.Difficulty.NORMAL);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eNormal §7gesetzt.");
                        } else if (args[0].equalsIgnoreCase("3")) {
                            world.setDifficulty(org.bukkit.Difficulty.HARD);
                            s.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eSchwer §7gesetzt.");
                        } else {
                            sendSyntax(s);
                        }
                    } else {
                        s.sendMessage("§cDie Welt §4"+args[1]+"§c existiert nicht!");
                    }
                } else {
                    sendSyntax(s);
                }
            } else {
                User u = Zyneon.getOnlineUsers().get(p.getUniqueId());
                if(p.hasPermission("zyneon.team")) {
                    if(args.length == 0) {
                        u.sendMessage("Die Schwierigkeit steht auf: §e"+p.getWorld().getDifficulty().toString());
                    } else if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("peaceful")) {
                            p.getWorld().setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                            u.sendMessage("Du hast die Schwierigkeit auf §eFriedlich §7gesetzt.");
                        } else if(args[0].equalsIgnoreCase("easy")) {
                            p.getWorld().setDifficulty(org.bukkit.Difficulty.EASY);
                            u.sendMessage("Du hast die Schwierigkeit auf §eLeicht §7gesetzt.");
                        } else if(args[0].equalsIgnoreCase("normal")) {
                            p.getWorld().setDifficulty(org.bukkit.Difficulty.NORMAL);
                            u.sendMessage("Du hast die Schwierigkeit auf §eNormal §7gesetzt.");
                        } else if(args[0].equalsIgnoreCase("hard")) {
                            p.getWorld().setDifficulty(org.bukkit.Difficulty.HARD);
                            u.sendMessage("Du hast die Schwierigkeit auf §eSchwer §7gesetzt.");
                        } else if(args[0].equalsIgnoreCase("0")) {
                            p.performCommand("difficulty peaceful");
                        } else if(args[0].equalsIgnoreCase("1")) {
                            p.performCommand("difficulty easy");
                        } else if(args[0].equalsIgnoreCase("2")) {
                            p.performCommand("difficulty normal");
                        } else if(args[0].equalsIgnoreCase("3")) {
                            p.performCommand("difficulty hard");
                        } else {
                            sendSyntax(s);
                        }
                    } else if(args.length == 2) {
                        if(Bukkit.getWorld(args[1]) != null) {
                            World world = Bukkit.getWorld(args[1]);
                            if (args[0].equalsIgnoreCase("peaceful")) {
                                world.setDifficulty(org.bukkit.Difficulty.PEACEFUL);
                                u.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eFriedlich §7gesetzt.");
                            } else if (args[0].equalsIgnoreCase("easy")) {
                                world.setDifficulty(org.bukkit.Difficulty.EASY);
                                u.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eLeicht §7gesetzt.");
                            } else if (args[0].equalsIgnoreCase("normal")) {
                                world.setDifficulty(org.bukkit.Difficulty.NORMAL);
                                u.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eNormal §7gesetzt.");
                            } else if (args[0].equalsIgnoreCase("hard")) {
                                world.setDifficulty(org.bukkit.Difficulty.HARD);
                                u.sendMessage("Du hast die Schwierigkeit der Welt §e"+world.getName()+"§7 auf §eSchwer §7gesetzt.");
                            } else if (args[0].equalsIgnoreCase("0")) {
                                p.performCommand("difficulty peaceful " + args[1]);
                            } else if (args[0].equalsIgnoreCase("1")) {
                                p.performCommand("difficulty easy " + args[1]);
                            } else if (args[0].equalsIgnoreCase("2")) {
                                p.performCommand("difficulty normal " + args[1]);
                            } else if (args[0].equalsIgnoreCase("3")) {
                                p.performCommand("difficulty hard " + args[1]);
                            } else {
                                sendSyntax(s);
                            }
                        } else {
                            p.sendMessage("§cDie Welt §4"+args[1]+"§c existiert nicht!");
                        }
                    } else {
                        sendSyntax(s);
                    }
                } else {
                    API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                }
            }
        }
        return false;
    }
}