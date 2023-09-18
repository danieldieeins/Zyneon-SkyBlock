package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.*;
import com.zyneonstudios.nerotvlive.skyblock.manager.InventoryManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IslandCommand implements CommandExecutor, TabCompleter {

    private void sendSyntax(CommandSender s) {
        Player p = (Player)s;
        API.sendErrorMessage(s,"§4Fehler: §c/insel §7[Inselname/§fname§7/§alevel§c/top/settings§c/§4reset§c] §7[§fName deiner Insel§7/§aSpieler§7]");
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Island")) {
            if(s instanceof Player p) {
                User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
                SkyUser sP = API.getSkyBlockPlayer(u.getUUID());
                String SID = p.getUniqueId().toString();
                String SSID = SID.replace("-","");
                File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
                YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
                if(args.length == 0) {
                    if(sP.isGrounded()) {
                        API.sendErrorMessage(s,"§cDu kannst dich derzeit nicht teleportieren!");
                    } else {
                        API.sendMessage(p, "§7Teleportiere... §8(Das §nkönnte§r§8 ein bisschen dauern)");
                        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
                        WorldAPI.getPlayerIsland(p);
                        if(API.PM.getPlugin("Vault")!=null) {
                            if (!SkyBlock.getEco().hasAccount(p.getUniqueId())) {
                                SkyBlock.getEco().createAccount(p.getUniqueId());
                            }
                        }
                    }
                } else {
                    if(args[0].equalsIgnoreCase("top")) {
                        if (API.PM.getPlugin("Vault") != null) {
                            API.getTopList(s);
                        } else {
                            API.sendErrorMessage(s, "§cDiese Funktion ist derzeit deaktiviert.");
                        }
                    /*} else if(args[0].equalsIgnoreCase("switch")) {
                        WorldAPI.kickAllPlayersInWorld(IslandAPI.getIsland(p));
                        Bukkit.unloadWorld(IslandAPI.getIsland(p),true);

                        File nWF = new File("plugins/SkyBlock/Islands/"+SID+"_world-switch");
                        if(nWF.exists()) {
                            File temp = new File("plugins/SkyBlock/Islands/"+SID+"_world-switch_tmp");
                            WorldAPI.copyWorld(nWF,temp);
                            nWF.delete();
                            File world = new File("plugins/SkyBlock/Islands/"+SID+"_world");
                            WorldAPI.copyWorld(world,nWF);
                            world.delete();
                            WorldAPI.copyWorld(temp,world);
                            temp.delete();
                            WorldAPI.getPlayerIsland(p);
                        } else {
                            File world = new File("plugins/SkyBlock/Islands/"+SID+"_world");
                            WorldAPI.copyWorld(world,nWF);
                            world.delete();
                            WorldAPI.getPlayerIsland(p);
                        }*/
                    } else if(args[0].equalsIgnoreCase("reset")) {
                        InventoryManager.openIslandReset(p);
                    } else if(args[0].equalsIgnoreCase("level")) {
                        if(API.PM.getPlugin("Vault")!=null) {
                            if (args.length == 1) {
                                int level;
                                if (pF.getBoolean("Insel.Team.Enabled")) {
                                    level = WorldAPI.getIslandLevel(pF.getString("Insel.Team.Player"));
                                } else {
                                    level = WorldAPI.getIslandLevel(p.getUniqueId().toString());
                                }
                                API.sendMessage(p, "§7Dein Insellevel ist§8: §e" + level);
                            } else {
                                int level;
                                String tid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();
                                String stid = tid.replace("-", "");
                                File targetFile = new File("plugins/SkyBlock/Players/" + stid + ".yml");
                                YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                                if (targetFile.exists()) {
                                    if (tF.getBoolean("Insel.Team.Enable")) {
                                        level = WorldAPI.getIslandLevel(tF.getString("Insel.Team.Player"));
                                    } else {
                                        level = WorldAPI.getIslandLevel(tid);
                                    }
                                } else {
                                    level = 0;
                                }
                                String name;
                                if (Bukkit.getPlayer(args[1]) != null) {
                                    name = Bukkit.getPlayer(args[1]).getName();
                                } else {
                                    name = args[1];
                                }
                                API.sendMessage(p, "§7Das Insellevel von §a" + name + "§7 beträgt§8: §e" + level);
                            }
                        } else {
                            API.sendErrorMessage(s,"§cDiese Funktion ist derzeit deaktiviert.");
                        }
                    } else if(args[0].equalsIgnoreCase("settings")) {
                        InventoryManager.openIslandSettings(p);
                    } else if(args[0].equalsIgnoreCase("name")) {
                        if (args.length == 2) {
                            if(args[1].toLowerCase().contains(".")) {
                                API.sendErrorMessage(p,"§cDieser Inselname ist ungültig!");
                                return false;
                            }
                            File Islands = new File("plugins/SkyBlock/Islands.yml");
                            YamlConfiguration is = YamlConfiguration.loadConfiguration(Islands);
                            ArrayList<String> islandnames = new ArrayList<>();
                            ConfigAPI.checkEntry("Islands.Names", islandnames, Islands, is);
                            islandnames = (ArrayList<String>) is.getList("Islands.Names");
                            if(islandnames.contains(args[1])) {
                                API.sendErrorMessage(p,"§cDieser Inselname ist schon vergeben!");
                            } else {
                                if(pF.contains("Insel.Name")) {
                                    islandnames.remove(pF.getString("Insel.Name"));
                                    API.is.set("Islands.Names",islandnames);
                                    API.is.set("Names.UUID."+pF.getString("Insel.Name"),null);
                                    ConfigAPI.saveConfig(API.Islands,API.is);
                                }
                                islandnames.add(args[1]);
                                API.is.set("Islands.Names",islandnames);
                                API.is.set("UUID.Names."+SID,args[1]);
                                API.is.set("Names.UUID."+args[1],SID);
                                ConfigAPI.saveConfig(API.Islands,API.is);
                                pF.set("Insel.Name", args[1]);
                                ConfigAPI.saveConfig(playerFile, pF);
                                API.sendMessage(p, "§7Deine Insel heißt nun§8: §e" + args[1]);
                            }
                        } else {
                            sendSyntax(p);
                        }
                    } else {
                        File Islands = new File("plugins/SkyBlock/Islands.yml");
                        YamlConfiguration is = YamlConfiguration.loadConfiguration(Islands);
                        ArrayList<String> islandnames = new ArrayList<>();
                        ConfigAPI.checkEntry("Islands.Names", islandnames, Islands, is);
                        islandnames = (ArrayList<String>) is.getList("Islands.Names");
                        if(islandnames.contains(args[0])) {
                            if(API.is.contains("Names.UUID."+args[0])) {
                                if(API.isVisitable(API.is.getString("Names.UUID."+args[0]))) {
                                    if(sP.isGrounded()) {
                                        API.sendErrorMessage(s,"§cDu kannst dich derzeit nicht teleportieren!");
                                    } else {
                                        WorldAPI.createPlayerIsland(API.is.getString("Names.UUID." + args[0]));
                                        Location worldSpawn = new Location(Bukkit.getWorld("plugins/SkyBlock/Islands/" + API.is.getString("Names.UUID." + args[0]) + "_world"), 0.5, 75, 0.5, 90, 0);
                                        p.teleport(worldSpawn);
                                        API.sendMessage(p,"§7Du bist nun auf der Insel §e"+args[0]+"§8.");
                                    }
                                } else {
                                    API.sendErrorMessage(p,"§cDiese Insel kannst du dir nicht anschauen!");
                                }
                            } else {
                                String m = "";
                                for (int i = 0; i < args.length; i++) {
                                    m = m + args[i] + " ";
                                }
                                p.performCommand("team " + m);
                            }
                        } else {
                            String m = "";
                            for (int i = 0; i < args.length; i++) {
                                m = m + args[i] + " ";
                            }
                            p.performCommand("team " + m);
                        }
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
            completer.add("name");
            completer.add("level");
            completer.add("top");
            completer.add("settings");
            completer.add("accept");
            completer.add("decline");
            completer.add("leave");
            completer.add("invite");
            completer.add("kick");
            completer.add("reset");
        } else if(args.length == 2) {
            completer.add("error");
        }
        return completer;
    }
}