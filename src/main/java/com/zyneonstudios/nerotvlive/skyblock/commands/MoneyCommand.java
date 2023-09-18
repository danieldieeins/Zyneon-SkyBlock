package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class MoneyCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        if(s instanceof Player) {
            if(s.hasPermission("zyneon.team")) {
                API.sendErrorMessage(s, "§4Fehler: §c/money §7[pay/set/add/remove] [Spieler] [Wert]");
            } else {
                API.sendErrorMessage(s, "§4Fehler: §c/money §7[pay] [Spieler] [Wert]");
            }
        } else {
            API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NeedPlayer, MessageManager.Language.GERMAN));
        }
    }

    @Override
    public boolean onCommand(CommandSender s,Command cmd,String label,String[] args) {
        if(cmd.getName().equalsIgnoreCase("money")) {
            if (API.PM.getPlugin("Vault") != null) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (!SkyBlock.getEco().hasAccount(p.getUniqueId())) {
                        SkyBlock.getEco().createAccount(p.getUniqueId());
                    }
                    if (args.length == 0) {
                        API.sendMessage(p, "§7Du hast momentan §e" + PlayerAPI.getBalance(p.getUniqueId().toString()) + "§7 Münzen§8!");
                    } else if (args.length == 1) {
                        sendSyntax(s);
                    } else if (args.length == 2) {
                        sendSyntax(s);
                    } else {
                        if (args[0].equalsIgnoreCase("pay")) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player t = Bukkit.getPlayer(args[1]);
                                if (API.isNumeric(args[2])) {
                                    double payAmount = Double.parseDouble(args[2]);
                                    if (payAmount <= SkyBlock.getEco().getBalance(p.getUniqueId()).getBalance()) {
                                        if (p.getName().equals(t.getName())) {
                                            API.sendErrorMessage(p, "§cDu kannst dir selber kein Geld geben!");
                                        } else {
                                            double money = SkyBlock.getEco().getBalance(p.getUniqueId()).getBalance();
                                            double result = money - payAmount;
                                            SkyBlock.getEco().set(p.getUniqueId(), result);
                                            SkyBlock.getEco().deposit(t.getUniqueId(), payAmount);
                                            API.sendMessage(p, "§7Du hast dem Spieler §e" + t.getName() + "§a " + (int)payAmount + "§7 Münzen gegeben§8!");
                                            API.sendMessage(p, "§7Du hast nun§8: §e" + (int) SkyBlock.getEco().getBalance(p.getUniqueId()).getBalance() + " Münzen");
                                            API.sendMessage(t, "§7Du hast §a" + (int)payAmount + "§7 von §e" + p.getName() + "§7 erhalten§8!");
                                            API.sendMessage(t, "§7Du hast nun§8: §e" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen");
                                        }
                                    } else {
                                        if (p.getName().equals("Maxi15122")) {
                                            API.sendErrorMessage(p, "§cDu Geringverdiener!");
                                        } else {
                                            API.sendErrorMessage(p, "§cDazu hast du nicht genügend Geld!");
                                        }
                                    }
                                } else {
                                    API.sendErrorMessage(p, "§cDu musst eine gültige Zahl angeben.");
                                }
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(p)));
                            }
                        } else if (args[0].equalsIgnoreCase("add")) {
                            if (s.hasPermission("zyneon.team")) {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    Player t = Bukkit.getPlayer(args[1]);
                                    if (API.isNumeric(args[2])) {
                                        double amount = Double.parseDouble(args[2]);
                                        SkyBlock.getEco().set(t.getUniqueId(), SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + amount);
                                        API.sendMessage(s, "§7Der Spieler §e" + t.getName() + "§7 hat nun §a" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                        API.sendMessage(t, "§7Du hast nun §e" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                    } else {
                                        API.sendErrorMessage(s, "§cDu musst eine gültige Zahl angeben.");
                                    }
                                } else {
                                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(p)));
                                }
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                            }
                        } else if (args[0].equalsIgnoreCase("set")) {
                            if (s.hasPermission("zyneon.team")) {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    Player t = Bukkit.getPlayer(args[1]);
                                    if (API.isNumeric(args[2])) {
                                        double amount = Double.parseDouble(args[2]);
                                        SkyBlock.getEco().set(t.getUniqueId(), amount);
                                        API.sendMessage(s, "§7Der Spieler §e" + t.getName() + "§7 hat nun §a" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                        API.sendMessage(t, "§7Du hast nun §e" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                    } else {
                                        API.sendErrorMessage(s, "§cDu musst eine gültige Zahl angeben.");
                                    }
                                } else {
                                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(p)));
                                }
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                            }
                        } else if (args[0].equalsIgnoreCase("remove")) {
                            if (s.hasPermission("zyneon.team")) {
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    Player t = Bukkit.getPlayer(args[1]);
                                    if (API.isNumeric(args[2])) {
                                        double amount = Double.parseDouble(args[2]);
                                        SkyBlock.getEco().set(t.getUniqueId(), SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() - amount);
                                        API.sendMessage(s, "§7Der Spieler §e" + t.getName() + "§7 hat nun §a" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                        API.sendMessage(t, "§7Du hast nun §e" + (int) SkyBlock.getEco().getBalance(t.getUniqueId()).getBalance() + " Münzen§8!");
                                    } else {
                                        API.sendErrorMessage(s, "§cDu musst eine gültige Zahl angeben.");
                                    }
                                } else {
                                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer,PlayerAPI.getLanguage(p)));
                                }
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                            }
                        } else {
                            sendSyntax(s);
                        }
                    }
                } else {
                    sendSyntax(s);
                }
            } else {
                API.sendErrorMessage(s,"§cDiese Funktion ist derzeit deaktiviert.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("pay");
            if(sender.hasPermission("zyneon.team")) {
                completer.add("add");
                completer.add("remove");
                completer.add("set");
            }
        } else if(args.length == 2) {
            for(Player all:Bukkit.getOnlinePlayers()) {
                completer.add(all.getName());
            }
        }
        return completer;
    }
}
