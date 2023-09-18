package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.api.ConfigAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.WorldAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        Player player = (Player)s;
        API.sendErrorMessage(player,"§4Fehler: §c/team [accept/decline/leave/§finvite§c/§fkick§c] §f[Spieler]");
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Team")) {
            if(s instanceof Player p) {
                User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
                String SID = p.getUniqueId().toString();
                String SSID = SID.replace("-","");
                File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
                YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
                if(args.length == 0) {
                    sendSyntax(p);
                } else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("accept")) {
                        ArrayList<String> pmembers = new ArrayList<>();
                        ConfigAPI.checkEntry("Insel.Mitglieder", pmembers, playerFile, pF);
                        pmembers = (ArrayList<String>) pF.getList("Insel.Mitglieder");
                        if(pmembers.size()>0) {
                            API.sendErrorMessage(p,"§cDu kannst keiner Insel beitreten, während du ein Team hast!");
                        } else if(pF.getString("Insel.Team.Invite")==null) {
                            API.sendErrorMessage(p,"§cDu wurdest zu keiner Insel eingeladen!");
                        } else {
                            String tSID = pF.getString("Insel.Team.Invite");
                            if(Bukkit.getPlayer(UUID.fromString(tSID))!=null) {
                                Player t = Bukkit.getPlayer(UUID.fromString(tSID));
                                API.sendMessage(t,"§e"+p.getName()+"§7 hat deine Einladung §aangenommen§8.");
                            }
                            String tSSID = tSID.replace("-","");
                            File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                            YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                            ArrayList<String> members = new ArrayList<>();
                            ConfigAPI.checkEntry("Insel.Mitglieder",members,targetFile,tF);
                            members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
                            members.add(SID);
                            tF.set("Insel.Mitglieder",members);
                            pF.set("Insel.Team.Enable",true);
                            pF.set("Insel.Team.Player",pF.getString("Insel.Team.Invite"));
                            pF.set("Insel.Team.Invite",null);
                            p.getInventory().clear();
                            p.setLevel(0);
                            p.setExp(0);
                            ConfigAPI.saveConfig(targetFile,tF);
                            ConfigAPI.saveConfig(playerFile,pF);
                            API.sendMessage(p,"§7Du hast die Einladung §aangenommen§8!");
                            WorldAPI.getPlayerIsland(p);
                            WorldAPI.unloadIsland(p.getUniqueId().toString());
                        }
                    } else if(args[0].equalsIgnoreCase("decline")) {
                        if(pF.getString("Insel.Team.Invite")==null) {
                            API.sendErrorMessage(p,"§cDu wurdest zu keiner Insel eingeladen!");
                        } else {
                            if(Bukkit.getPlayer(UUID.fromString(pF.getString("Insel.Team.Invite")))!=null) {
                                API.sendMessage(Bukkit.getPlayer(UUID.fromString(pF.getString("Insel.Team.Invite"))),"§e"+p.getName()+"§7 hat deine Einladung §cabgelehnt§8.");
                            }
                            pF.set("Insel.Team.Invite",null);
                            ConfigAPI.saveConfig(playerFile,pF);
                            API.sendMessage(p,"§7Du hast die Einladung §cabgelehnt§8.");
                        }
                    } else if(args[0].equalsIgnoreCase("leave")) {
                        if(pF.getBoolean("Insel.Team.Enable")) {
                            String tSID = pF.getString("Insel.Team.Player");
                            String tSSID = tSID.replace("-","");
                            File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                            YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                            ArrayList<String> members = new ArrayList<>();
                            ConfigAPI.checkEntry("Insel.Mitglieder",members,targetFile,tF);
                            members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
                            members.remove(SID);
                            tF.set("Insel.Mitglieder",members);
                            pF.set("Insel.Team.Enable", false);
                            pF.set("Insel.Team.Enable", null);
                            pF.set("Insel.Team.Player", null);
                            pF.set("Insel.Team.Invite", null);
                            p.getInventory().clear();
                            p.setLevel(0);
                            p.setExp(0);
                            ConfigAPI.saveConfig(targetFile,tF);
                            ConfigAPI.saveConfig(playerFile, pF);
                            API.sendMessage(p, "§7Du hast die Insel §cverlassen§8!");
                            WorldAPI.getPlayerIsland(p);
                        } else {
                            API.sendErrorMessage(p,"§cDu bist in keinem Inselteam!");
                        }
                    } else {
                        sendSyntax(p);
                    }
                } else {
                    if(args[0].equalsIgnoreCase("invite")) {
                        if (!pF.getBoolean("Insel.Team.Enable")) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player t = Bukkit.getPlayer(args[1]);
                                if(t.getName().equals(p.getName())) {
                                    API.sendErrorMessage(p,"§cDu kannst dich nicht selbst einladen!");
                                } else {
                                    String tSID = t.getUniqueId().toString();
                                    String tSSID = tSID.replace("-", "");
                                    ArrayList<String> members = new ArrayList<>();
                                    File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                                    YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                                    ConfigAPI.checkEntry("Insel.Mitglieder",members,targetFile,tF);
                                    members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
                                    if (tF.getBoolean("Insel.Team.Enable")) {
                                        API.sendErrorMessage(p, "§cDer Spieler ist bereits Mitglied einer anderen Insel!");
                                    } else if(members.size()>0) {
                                        API.sendErrorMessage(p, "§cDer Spieler ist bereits Mitglied einer anderen Insel!");
                                    } else {
                                        tF.set("Insel.Team.Invite", p.getUniqueId().toString());
                                        ConfigAPI.saveConfig(targetFile, tF);
                                        API.sendMessage(p, "§7Du hast den Spieler §e" + t.getName() + "§7 zu deiner Insel eingeladen§8.");
                                        API.sendMessage(t, "§7Du wurdest zu der Insel von §e" + p.getName() + "§7 eingeladen§8.");
                                        TextComponent accepter = new TextComponent(API.Prefix+"§8» §a/team accept §7zum Annehmen§8.");
                                        accepter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept"));
                                        TextComponent decliner = new TextComponent(API.Prefix+"§8» §c/team decline §7zum Ablehnen§8.");
                                        decliner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team decline"));
                                        t.spigot().sendMessage(accepter);
                                        t.spigot().sendMessage(decliner);
                                    }
                                }
                            } else {
                                API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                            }
                        } else {
                            API.sendErrorMessage(p, "§cDu bist nicht der Inselbesitzer!");
                        }
                    } else if(args[0].equalsIgnoreCase("kick")) {
                        if (!pF.getBoolean("Insel.Team.Enable")) {
                            if (Bukkit.getPlayer(args[1]) != null) {
                                Player t = Bukkit.getPlayer(args[1]);
                                if(t.getName().equals(p.getName())) {
                                    API.sendErrorMessage(p,"§cDu kannst dich nicht selbst kicken!");
                                } else {
                                    ArrayList<String> members = new ArrayList<>();
                                    ConfigAPI.checkEntry("Insel.Mitglieder", members, playerFile, pF);
                                    members = (ArrayList<String>) pF.getList("Insel.Mitglieder");
                                    if (members.contains(t.getUniqueId().toString())) {
                                        members.remove(t.getUniqueId().toString());
                                        ConfigAPI.checkEntry("Insel.Mitglieder", members, playerFile, pF);
                                        ConfigAPI.saveConfig(playerFile, pF);
                                        String tSSID = t.getUniqueId().toString().replace("-", "");
                                        File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                                        YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                                        tF.set("Insel.Team.Enable", false);
                                        tF.set("Insel.Team.Enable", null);
                                        tF.set("Insel.Team.Player", null);
                                        tF.set("Insel.Team.Invite", null);
                                        t.getInventory().clear();
                                        t.setLevel(0);
                                        t.setExp(0);
                                        User tU = Zyneon.getAPI().getOnlineUser(t.getUniqueId());
                                        ConfigAPI.saveConfig(targetFile, tF);
                                        WorldAPI.getPlayerIsland(t);
                                        API.sendMessage(t, "§7Du wurdest aus der Insel §cgeworfen§8!");
                                        API.sendMessage(p, "§7Du hast den Spieler §e" + t.getName() + "§7 aus deiner Insel §cgeworfen§8!");
                                    } else {
                                        API.sendErrorMessage(p, "§cDieser Spieler ist kein Mitglied deiner Insel!");
                                    }
                                }
                            } else {
                                OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
                                if(t.getName().equals(p.getName())) {
                                    API.sendErrorMessage(p,"§cDu kannst dich nicht selbst kicken!");
                                } else {
                                    ArrayList<String> members = new ArrayList<>();
                                    ConfigAPI.checkEntry("Insel.Mitglieder", members, playerFile, pF);
                                    members = (ArrayList<String>) pF.getList("Insel.Mitglieder");
                                    if (members.contains(t.getUniqueId().toString())) {
                                        members.remove(t.getUniqueId().toString());
                                        ConfigAPI.checkEntry("Insel.Mitglieder", members, playerFile, pF);
                                        ConfigAPI.saveConfig(playerFile, pF);
                                        String tSSID = t.getUniqueId().toString().replace("-", "");
                                        File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                                        YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                                        tF.set("Insel.Team.Enable", false);
                                        tF.set("Insel.Team.Enable", null);
                                        tF.set("Insel.Team.Player", null);
                                        tF.set("Insel.Team.Invite", null);
                                        tF.set("Spieler.Clear",true);
                                        ConfigAPI.saveConfig(targetFile, tF);
                                        API.sendMessage(p, "§7Du hast den Spieler §e" + t.getName() + "§7 aus deiner Insel §cgeworfen§8!");
                                    } else {
                                        API.sendErrorMessage(p, "§cDieser Spieler ist kein Mitglied deiner Insel!");
                                    }
                                }
                            }
                        } else {
                            API.sendErrorMessage(p,"§cDu bist nicht der Inselbesitzer!");
                        }
                    } else {
                        sendSyntax(p);
                    }
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
            completer.add("accept");
            completer.add("decline");
            completer.add("leave");
            completer.add("invite");
            completer.add("kick");
        } else if(args.length == 2) {
            completer.add("error");
        }
        return completer;
    }
}