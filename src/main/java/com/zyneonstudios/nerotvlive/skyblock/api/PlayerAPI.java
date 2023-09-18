package com.zyneonstudios.nerotvlive.skyblock.api;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.utils.Countdown;
import com.zyneonstudios.nerotvlive.skyblock.utils.MySQL;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.IslandEco;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PlayerAPI {

    public static ArrayList<Player> commandCooldown = new ArrayList<>();

    public static void sendPlayerMessage(Player player,String message) {
        player.sendMessage(ServerAPI.formatMessage(message));
    }

    public static void sendPlayerMessage(Player player, String message, NewSound newSound) {
        player.playSound(player.getLocation(),org.bukkit.Sound.valueOf(newSound.toString()),100,100);
        player.sendMessage(ServerAPI.formatMessage(message));
    }

    public static void playNewSound(Player player,NewSound newSound) {
        org.bukkit.Sound sound = SoundAPI.getNewSound(newSound);
        player.playSound(player.getLocation(),sound,100,100);
    }

    public static void playNewSound(Player player,NewSound newSound, float v) {
        org.bukkit.Sound sound = SoundAPI.getNewSound(newSound);
        player.playSound(player.getLocation(),sound,v,100);
    }

    public static void playNewSound(Player player,NewSound newSound, float v, float v1) {
        org.bukkit.Sound sound = SoundAPI.getNewSound(newSound);
        player.playSound(player.getLocation(),sound,v,v1);
    }

    public static void registerPlayer(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        pF.set("Spieler.Name",player.getName());
        pF.set("Spieler.UUID", player.getUniqueId().toString());
        ConfigAPI.checkEntry("Insel.Team.Enable",false,playerFile,pF);
        pF.set("Insel.Team.Invite",null);
        ConfigAPI.saveConfig(playerFile,pF);
        ConfigAPI.reloadConfig(playerFile,pF);
        API.setTablist();
        PlayerAPI.setBuildMode(player,false);
        PlayerAPI.setScoreboard(player);
        if(API.PM.getPlugin("Vault")!=null) {
            if (!SkyBlock.getEco().hasAccount(player.getUniqueId())) {
                SkyBlock.getEco().createAccount(player.getUniqueId());
            }
        }
        if(pF.contains("Spieler.Clear")) {
            if(pF.getBoolean("Spieler.Clear")) {
                player.getInventory().clear();
                player.setLevel(0);
                player.setExp(0);
                pF.set("Spieler.Clear",false);
                pF.set("Spieler.Clear",null);
                ConfigAPI.saveConfig(playerFile,pF);
                User u = Zyneon.getAPI().getOnlineUser(player.getUniqueId());
            }
        }
    }

    public static void saveLastLoc(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        pF.set("Spieler.LastLoc",false);
        ConfigAPI.saveConfig(playerFile,pF);
        ConfigAPI.reloadConfig(playerFile,pF);
        pF.set("Spieler.World", player.getWorld().getName());
        pF.set("Spieler.X",player.getLocation().getX());
        pF.set("Spieler.Y",player.getLocation().getY());
        pF.set("Spieler.Z",player.getLocation().getZ());
        pF.set("Spieler.y",player.getLocation().getYaw());
        pF.set("Spieler.p",player.getLocation().getPitch());
        pF.set("Spieler.LastLoc",true);
        ConfigAPI.saveConfig(playerFile,pF);
        ConfigAPI.reloadConfig(playerFile,pF);
    }

    static ArrayList<UUID> buildPlayers = new ArrayList<>();
    public static boolean canPlayerBuild(Player p) {
        if(buildPlayers.contains(p.getUniqueId())) {
            return true;
        } else {
            return false;
        }
    }

    public static void setBuildMode(Player p,boolean state) {
        if(state) {
            if(!buildPlayers.contains(p.getUniqueId())) {
                buildPlayers.add(p.getUniqueId());
                p.setGameMode(GameMode.CREATIVE);
            }
        } else {
            if(buildPlayers.contains(p.getUniqueId())) {
                buildPlayers.remove(p.getUniqueId());
            }
        }
    }

    public static void toggleBuildMode(Player p) {
        if(canPlayerBuild(p)) {
            setBuildMode(p,false);
        } else {
            setBuildMode(p,true);
        }
    }

    public static double getBalance(String SID) {
        if(API.mySQL) {
            return getBalanceSQL(SID);
        } else {
            String SSID = SID.replace("-", "");
            File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
            YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
            if (pF.getBoolean("Insel.Team.Enable")) {
                String tSID = pF.getString("Insel.Team.Player");
                String tSSID = tSID.replace("-", "");
                File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                return tF.getDouble("Insel.Geld");
            } else {
                return pF.getDouble("Insel.Geld");
            }
        }
    }

    public static double getBalanceSQL(String SID) {
        if(!MySQL.isConnected()) {
            MySQL.connect();
        }
        IslandEco.checkTable();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.getBoolean("Insel.Team.Enable")) {
            String tSID = pF.getString("Insel.Team.Player");
            if(SkyBlock.getEco().hasAccount(UUID.fromString(tSID))) {
                return SkyBlock.getEco().getBalance(UUID.fromString(tSID)).getBalance();
            } else {
                return 0;
            }
        } else {
            if(SkyBlock.getEco().hasAccount(UUID.fromString(SID))) {
                return SkyBlock.getEco().getBalance(UUID.fromString(SID)).getBalance();
            } else {
                return 0;
            }
        }
    }

    public static boolean hasBalanceHUD(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.contains("Settings.LabyMod.BalanceHUD")) {
            return pF.getBoolean("Settings.LabyMod.BalanceHUD");
        } else {
            return true;
        }
    }

    public static boolean hasAllowBuild(Player player) {
        String SID = player.getWorld().getName().replace("plugins/SkyBlock/Islands/","").replace("_world","").replace("_nether","").replace("_end","");
        File targetFile = new File("plugins/SkyBlock/Players/"+SID.replace("-","")+".yml");
        YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
        if(player.getWorld().getName().replace("plugins/SkyBlock/Islands/","").replace("_world","").replace("_nether","").replace("_end","").equals(player.getUniqueId().toString())) {
            return true;
        } else if(targetFile.exists()&&tF.contains("Insel.Mitglieder")) {
            ArrayList<String> members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
            if(members.contains(player.getUniqueId().toString())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean invFull(Player p) {
        return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
    }

    public static void startCommandCooldown(Player p) {
        if (!p.hasPermission("zyneon.leading")) {
            commandCooldown.add(p);
            new Countdown(3, SkyBlock.getInstance()) {
                @Override
                public void count(int current) {
                    if (current == 1) {
                        commandCooldown.remove(p);
                    }
                }
            }.start();
        }
    }

    public static MessageManager.Language getLanguage(Player p) {
        String SSID = p.getUniqueId().toString().replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.contains("Spieler.Language")) {
            if(MessageManager.Language.valueOf(pF.getString("Spieler.Language")).equals(MessageManager.Language.GERMAN)) {
                return MessageManager.Language.GERMAN;
            } else {
                return MessageManager.Language.ENGLISH;
            }
        } else {
            //SICHERUNG, DAMIT KEINE USER VERSEHENTLICH DAS HALBE PLUGIN ANDERS HABEN
            if(!p.getName().equals("nerotvlive")) {
                if(!p.getName().equals("DannyTheOne")) {
                    return MessageManager.Language.GERMAN;
                }
            }//ENDE SICHERUNG
            if (p.getLocale().equals("de_de") || p.getLocale().equals("de_ch") || p.getLocale().equals("de_at") || p.getLocale().equals("bar") || p.getLocale().equals("ksh") || p.getLocale().equals("sxu")) {
                return MessageManager.Language.GERMAN;
            } else {
                return MessageManager.Language.ENGLISH;
            }
        }
    }

    public static void setLanguage(Player p, MessageManager.Language lang) {
        String SSID = p.getUniqueId().toString().replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(lang.equals(MessageManager.Language.AUTOMATIC)) {
            pF.set("Spieler.Language",null);
            ConfigAPI.saveConfig(playerFile,pF);
            API.sendMessage(p, MessageManager.getMessage(MessageManager.Message.Language_SELF,getLanguage(p)).replace("Deutsch","automatisch").replace("English","automatic"));
            return;
        }
        pF.set("Spieler.Language",lang.toString());
        ConfigAPI.saveConfig(playerFile,pF);
        API.sendMessage(p, MessageManager.getMessage(MessageManager.Message.Language_SELF,getLanguage(p)));
        renewScoreboard(p);
    }

    public static MessageManager.Message rankName(Player player) {
        if(player.hasPermission("zyneon.leading")) {
            return MessageManager.Message.rankLeading;
        } else if(player.hasPermission("zyneon.team")) {
            return MessageManager.Message.rankTeam;
        } else if(player.hasPermission("zyneon.creator")) {
            return MessageManager.Message.rankCreator;
        } else if(player.hasPermission("zyneon.premium")) {
            return MessageManager.Message.rankPremium;
        } else {
            return MessageManager.Message.rankUser;
        }
    }

    public static void setScoreboard(Player player) {
        Scoreboard board = player.getScoreboard();
        Objective boardContent = board.getObjective("SKYBLOCK");
        board.resetScores("SKYBLOCK");
        Score zyneon = boardContent.getScore("§fServer§7-§fIP§8:");
        boardContent.setDisplayName(API.animatedSkyBlock());
        Score zyneonContent = boardContent.getScore("§ezyneonstudios.com");
        Score placeholder0 = boardContent.getScore("§0");
        Score placeholder1 = boardContent.getScore("§1");
        Score placeholder2 = boardContent.getScore("§2");
        Score placeholder3 = boardContent.getScore("§3");
        Score placeholder4 = boardContent.getScore("§4");
        Score time = boardContent.getScore("§fZeit§8:");
        Score timeContent = boardContent.getScore("§e" + API.date);
        Score money = boardContent.getScore("§f"+ MessageManager.getMessage(MessageManager.Message.coins,PlayerAPI.getLanguage(player))+"§8:");
        int moneyInt = (int) SkyBlock.getEco().getBalance(player.getUniqueId()).getBalance();
        Score moneyContent = boardContent.getScore("§e"+moneyInt);
        Score rank = boardContent.getScore("§f"+ MessageManager.getMessage(MessageManager.Message.rank,PlayerAPI.getLanguage(player))+"§8:");
        String rankString = MessageManager.getMessage(PlayerAPI.rankName(player),PlayerAPI.getLanguage(player));
        Score rankContent = boardContent.getScore("§e"+rankString);
        Score level = boardContent.getScore("§fLevel§8:");
        Score levelContent = boardContent.getScore("§e"+ WorldAPI.getIslandLevel(player.getUniqueId().toString()));
        placeholder0.setScore(15);
        money.setScore(14);
        moneyContent.setScore(13);
        placeholder1.setScore(12);
        level.setScore(11);
        levelContent.setScore(10);
        placeholder2.setScore(9);
        time.setScore(8);
        timeContent.setScore(7);
        placeholder4.setScore(6);
        rank.setScore(5);
        rankContent.setScore(4);
        placeholder3.setScore(3);
        zyneon.setScore(2);
        zyneonContent.setScore(1);
        SkyBlock.setPrefix(player);
    }

    public static void renewScoreboard(Player player) {
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        player.setScoreboard(sm.getNewScoreboard());
        Scoreboard board = player.getScoreboard();
        if(board.getObjective("SKYBLOCK")==null) {
            board.registerNewObjective("SKYBLOCK", "SKYBLOCK");
        }
        Objective boardContent = board.getObjective("SKYBLOCK");
        boardContent.setDisplaySlot(DisplaySlot.SIDEBAR);
        setScoreboard(player);
    }

    public static MemberType getMemberType(Player player) {
        File playerFile = new File("plugins/SkyBlock/Players/"+player.getUniqueId().toString().replace("-","")+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.getBoolean("Insel.Team.Enable")) {
            return MemberType.MEMBER;
        } else {
            return MemberType.OWNER;
        }
    }

    public static boolean isVIP(Player player) {
        return player.hasPermission("zyneon.premium") || player.hasPermission("zyneon.premium");
    }

    public static boolean isCreator(Player player) {
        return player.hasPermission("zyneon.creator") || player.hasPermission("zyneon.creator");
    }

    public static boolean isTeam(Player player) {
        return player.hasPermission("zyneon.team") || player.hasPermission("zyneon.team");
    }

    public static boolean isLeading(Player player) {
        return player.hasPermission("zyneon.leading") || player.hasPermission("zyneon.leading");
    }
}