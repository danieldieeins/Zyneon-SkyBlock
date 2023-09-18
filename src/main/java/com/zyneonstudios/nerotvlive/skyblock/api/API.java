package com.zyneonstudios.nerotvlive.skyblock.api;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.paper.utils.user.User;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import com.zyneonstudios.nerotvlive.skyblock.utils.MySQL;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.IslandEco;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class API {

    public static File Config = ConfigAPI.config;
    public static YamlConfiguration cfg = ConfigAPI.cfg;
    public static PluginManager PM = Bukkit.getPluginManager();
    public static File Islands = new File("plugins/SkyBlock/Islands.yml");
    public static YamlConfiguration is = YamlConfiguration.loadConfiguration(Islands);
    public static ArrayList<String> BlockedCommands = new ArrayList<String>();
    public static ArrayList<String> TeleportBlocks = new ArrayList<String>();
    public static boolean mySQL;
    public static boolean isStopping=false;
    public static String date;

    public static HashMap<UUID, SkyUser> skyBlockPlayers = new HashMap<>();

    public static SkyUser getSkyBlockPlayer(UUID uuid) {
        if(skyBlockPlayers.containsKey(uuid)) {
            return skyBlockPlayers.get(uuid);
        } else {
            skyBlockPlayers.put(uuid,new SkyUser(uuid));
            return getSkyBlockPlayer(uuid);
        }
    }

    public static boolean isVisitable(String is) {
        return true;
    }

    public static String getPrefix() {
        if(ConfigAPI.cfg.getString("Core.Strings.General.Prefix")!=null) {
            return ConfigAPI.cfg.getString("Core.Strings.General.Prefix");
        } else {
            return "§9SkyBlock §8» §7";
        }
    }

    public static Location getSpawn() {
        return Bukkit.getWorlds().get(0).getSpawnLocation();
    }

    public static Collection<String> commands = new ArrayList<>();
    public static void initCommandList() {
        for(Command all: PluginCommandYamlParser.parse(SkyBlock.getInstance())) {
            commands.add(all.getName().toLowerCase());
            for(String aliases:all.getAliases()) {
                commands.add(aliases.toLowerCase());
            }
        }
        commands.add("friends");
        commands.add("party");
        commands.add("give");
        commands.add("sit");
        commands.add("crawl");
        commands.add("lay");
    }

    public static boolean hasAvailableSlot(Player player){
        return !invFull(player);
    }

    public static boolean invFull(Player p) {
        return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
    }

    public static String Prefix = getPrefix();

    public static void initConfig() {
        //SETTINGS
        ConfigAPI.checkEntry("Core.Settings.Maintenance",true,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.Console.Language", MessageManager.Language.ENGLISH.toString(),Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.Blacklists.Commands",BlockedCommands,Config,cfg);
        BlockedCommands = (ArrayList<String>)cfg.getList("Core.Settings.Blacklists.Commands");

        //MYSQL
        ConfigAPI.checkEntry("Core.Settings.MySQL.enable",false,Config,cfg);
        mySQL = cfg.getBoolean("Core.Settings.MySQL.enable");
        ConfigAPI.checkEntry("Core.Settings.MySQL.host","127.0.0.1",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.port","3306",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.database","datenbank",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.username","benutzer",Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.MySQL.password","passwort",Config,cfg);

        //TABLIST
        ConfigAPI.checkEntry("Core.Tablist.Header"," \n §9zyneonstudios.com §8● §7Minecraft - aber mehr \n ",Config,cfg);
        ConfigAPI.checkEntry("Core.Tablist.Footer"," \n §9https://www.zyneonstudios.com \n §7sponsored by §ftube-hosting.de \n ",Config,cfg);

        //STRINGS
        ConfigAPI.checkEntry("Core.Strings.General.Prefix","§9SkyBlock §8» §7",ConfigAPI.config,ConfigAPI.cfg);
        TeleportBlocks.add("ENDER_CHEST");
        TeleportBlocks.add("CHEST");
        TeleportBlocks.add("STONECUTTER");
        ConfigAPI.checkEntry("Core.Strings.TeleportBlocks.Names",TeleportBlocks,Config,cfg);
        TeleportBlocks = (ArrayList<String>)cfg.getList("Core.Strings.TeleportBlocks.Names");

        //ISLANDS
        ArrayList<String> islandnames = new ArrayList<>();
        ConfigAPI.checkEntry("Islands.Names",islandnames,Islands,is);
        islandnames = (ArrayList<String>)is.getList("Islands.Names");
    }

    public static void setTablist() {
        for(Player all : Bukkit.getOnlinePlayers()) {
            all.setPlayerListFooter(cfg.getString("Core.Tablist.Footer").replace("&", "§"));
            PlayerAPI.setScoreboard(all);
        }
    }

    public static boolean isStringBlocked(String DYM) {
        String string = DYM.toLowerCase();
        if(string.contains("nigga")) {
            return true;
        } else if(string.contains("niga")) {
            return true;
        } else if(string.contains("nega")) {
            if(string.contains("negativ")) {
                if(string.contains("ohne")) {
                    if(string.contains("tiv")) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if(string.contains("neger")) {
            return true;
        } else if(string.contains("nigger")) {
            return true;
        } else if(string.contains("niger")) {
            if(string.contains("weniger")) {
                return false;
            } else {
                return true;
            }
        } else if(string.contains("nazi")) {
            return true;
        } else if(string.contains("hitler")) {
            return true;
        } else if(string.contains("hure")) {
            return true;
        } else if(string.contains("fotze")) {
            return true;
        } else if(string.contains("vergewalti")) {
            return true;
        } else if(string.contains("misgeburt")) {
            return true;
        } else if(string.contains("mistgeburt")) {
            return true;
        } else if(string.contains("missgeburt")) {
            return true;
        } else if(string.contains("misstgeburt")) {
            return true;
        } else if(string.contains("misset")) {
            return true;
        } else if(string.contains("miset")) {
            return true;
        } else if(string.contains("missed")) {
            return true;
        } else if(string.contains("mised")) {
            return true;
        } else if(string.contains("faggot")) {
            return true;
        } else if(string.contains("schwuchtel")) {
            return true;
        } else if(string.contains("spast")) {
            return true;
        } else if(string.contains("spasst")) {
            return true;
        } else if(string.contains("cancer")) {
            return true;
        } else if(string.contains("krebs")) {
            return true;
        } else if(string.contains("corona")) {
            return true;
        } else if(string.contains("corinski")) {
            return true;
        } else if(string.contains("atilla")) {
            return true;
        } else if(string.contains("hildmann")) {
            return true;
        } else if(string.contains("hildman")) {
            return true;
        } else if(string.contains("atila")) {
            return true;
        } else {
            return false;
        }
    }

    public static void initCommand(CommandExecutor command,String commandName,Boolean tab) {
        sendMessage(Bukkit.getConsoleSender(), MessageManager.getMessage(MessageManager.Message.init_COMMAND, MessageManager.Language.GERMAN).replace("%command%",commandName));
        SkyBlock.getInstance().getCommand(commandName).setExecutor(command);
        if(tab) {
            SkyBlock.getInstance().getCommand(commandName).setTabCompleter((TabCompleter) command);
        }
    }

    public static boolean isNumericPart(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String Check) {
        if(isNumericPart(Check)) {
            return !(Double.parseDouble(Check) > 999999998);
        } else {
            return false;
        }
    }

    public static void sendInit() {
        sendMessage(Bukkit.getConsoleSender(),"§8░§9█§9█§9█§9█§9█§9█╗§9█§9█╗§8░§8░§9█§9█╗§9█§9█╗§8░§8░§8░§9█§9█╗§9█§9█§9█§9█§9█§9█╗§8░§9█§9█╗§8░§8░§8░§8░§8░§8░§9█§9█§9█§9█§9█╗§8░§8░§9█§9█§9█§9█§9█╗§8░§9█§9█╗§8░§8░§9█§9█╗");
        sendMessage(Bukkit.getConsoleSender(),"§9█§9█§9╔════§9╝§9█§9█║§8░§9█§9█§9╔§9╝§9╚§9█§9█╗§8░§9█§9█§9╔§9╝§9█§9█§9╔══§9█§9█╗§9█§9█║§8░§8░§8░§8░§8░§9█§9█§9╔══§9█§9█╗§9█§9█§9╔══§9█§9█╗§9█§9█║§8░§9█§9█§9╔§9╝");
        sendMessage(Bukkit.getConsoleSender(),"§9╚§9█§9█§9█§9█§9█╗§8░§9█§9█§9█§9█§9█═§9╝§8░§8░§9╚§9█§9█§9█§9█§9╔§9╝§8░§9█§9█§9█§9█§9█§9█╦§9╝§9█§9█║§8░§8░§8░§8░§8░§9█§9█║§8░§8░§9█§9█║§9█§9█║§8░§8░§9╚═§9╝§9█§9█§9█§9█§9█═§9╝§8░");
        sendMessage(Bukkit.getConsoleSender(),"§8░§9╚═══§9█§9█╗§9█§9█§9╔═§9█§9█╗§8░§8░§8░§9╚§9█§9█§9╔§9╝§8░§8░§9█§9█§9╔══§9█§9█╗§9█§9█║§8░§8░§8░§8░§8░§9█§9█║§8░§8░§9█§9█║§9█§9█║§8░§8░§9█§9█╗§9█§9█§9╔═§9█§9█╗§8░");
        sendMessage(Bukkit.getConsoleSender(),"§9█§9█§9█§9█§9█§9█§9╔§9╝§9█§9█║§8░§9╚§9█§9█╗§8░§8░§8░§9█§9█║§8░§8░§8░§9█§9█§9█§9█§9█§9█╦§9╝§9█§9█§9█§9█§9█§9█§9█╗§9╚§9█§9█§9█§9█§9█§9╔§9╝§9╚§9█§9█§9█§9█§9█§9╔§9╝§9█§9█║§8░§9╚§9█§9█╗");
        sendMessage(Bukkit.getConsoleSender(),"§9╚═════§9╝§8░§9╚═§9╝§8░§8░§9╚═§9╝§8░§8░§8░§9╚═§9╝§8░§8░§8░§9╚═════§9╝§8░§9╚══════§9╝§8░§9╚════§9╝§8░§8░§9╚════§9╝§8░§9╚═§9╝§8░§8░§9╚═§9╝");
    }

    public static void changeGamemode(Player p, String GameMode) {
        User u = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
        if (GameMode.equalsIgnoreCase("0")) {
            p.setGameMode(org.bukkit.GameMode.SURVIVAL);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("1")) {
            p.setGameMode(org.bukkit.GameMode.CREATIVE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("2")) {
            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("3")) {
            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("SURVIVAL")) {
            p.setGameMode(org.bukkit.GameMode.SURVIVAL);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("CREATIVE")) {
            p.setGameMode(org.bukkit.GameMode.CREATIVE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("ADVENTURE")) {
            p.setGameMode(org.bukkit.GameMode.ADVENTURE);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        } else if (GameMode.equalsIgnoreCase("SPECTATOR")) {
            p.setGameMode(org.bukkit.GameMode.SPECTATOR);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 100.0f, 100.0f);
            u.sendMessage(MessageManager.getMessage(MessageManager.Message.ChangedGamemode,PlayerAPI.getLanguage(p)).replace("%gamemode%",getGamemode(p)));
        }
    }

    public static String getGamemode(Player p) {
        String GameMode;
        if (p != null) {
            if (p.getGameMode().toString().equals("SURVIVAL")) {
                GameMode = MessageManager.getMessage(MessageManager.Message.Gamemode_SURVIVAL,PlayerAPI.getLanguage(p));
            } else if (p.getGameMode().toString().equals("CREATIVE")) {
                GameMode = MessageManager.getMessage(MessageManager.Message.Gamemode_CREATIVE,PlayerAPI.getLanguage(p));
            } else if (p.getGameMode().toString().equals("ADVENTURE")) {
                GameMode = MessageManager.getMessage(MessageManager.Message.Gamemode_ADVENTURE,PlayerAPI.getLanguage(p));
            } else if (p.getGameMode().toString().equals("SPECTATOR")) {
                GameMode = MessageManager.getMessage(MessageManager.Message.Gamemode_SPECTATOR,PlayerAPI.getLanguage(p));
            } else {
                GameMode = null;
            }
        } else {
            GameMode = null;
        }
        return GameMode;
    }

    public static Material generatorBlock() {
        Material result;
        int i = ThreadLocalRandom.current().nextInt(0,100);
        if(i>98) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r<2) {
                result = Material.ANCIENT_DEBRIS;
            } else {
                result = Material.EMERALD_ORE;
            }
        } else if(i>94) {
            int i5 = ThreadLocalRandom.current().nextInt(0,10);
            if(i5 == 3) {
                result = Material.DIAMOND_ORE;
            } else {
                result = Material.STONE;
            }
        } else if(i>92) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r <= 50) {
                result = Material.REDSTONE_ORE;
            } else {
                result = Material.LAPIS_ORE;
            }
        } else if(i>80) {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r < 80) {
                int rr = ThreadLocalRandom.current().nextInt(0,4);
                if(rr == 3) {
                    result = Material.STONE;
                } else {
                    result = Material.IRON_ORE;
                }
            } else {
                result = Material.GOLD_ORE;
            }
        } else if(i>60) {
            int i5 = ThreadLocalRandom.current().nextInt(0,5);
            if(i5 >= 2){
                result = Material.COAL_ORE;
            } else {
                result = Material.DEEPSLATE;
            }
        } else {
            int r = ThreadLocalRandom.current().nextInt(0,100);
            if(r > 20) {
                result = Material.STONE;
            } else {
                result = Material.DEEPSLATE;
            }
        }
        return result;
    }

    public static int getPing(Player p) {
        String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (!p.getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
            p = Bukkit.getPlayer(p.getUniqueId());
        }
        try {
            return p.getPing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void resetIsland(Player p) {
        if(API.PM.getPlugin("Vault")!=null) {
            SkyBlock.getEco().set(p.getUniqueId(),0);
            SkyBlock.getEco().delete(p.getUniqueId());
        }
        String SID = p.getUniqueId().toString();
        String SSID = SID.replace("-", "");
        File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        ConfigAPI.reloadConfig(playerFile, pF);
        if (pF.getBoolean("Insel.Team.Enable")) {
            API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NeedIslandOwner,PlayerAPI.getLanguage(p)));
        } else {
            File island_world = new File("plugins/SkyBlock/Islands/" + SID + "_world");
            File island_nether = new File("plugins/SkyBlock/Islands/" + SID + "_nether");
            p.performCommand("spawn");
            if(WarpAPI.isWarpEnabled("spawn")) {
                p.teleport(WarpAPI.getWarp("spawn"));
            }
            WorldAPI.unloadIsland(p.getUniqueId().toString());
            if (Bukkit.getWorld("plugins/SkyBlock/Islands/" + SID + "_world") != null) {
                Bukkit.unloadWorld(Bukkit.getWorld("plugins/SkyBlock/Islands/" + SID + "_world"), false);
            }
            if (Bukkit.getWorld("plugins/SkyBlock/Islands/" + SID + "_nether") != null) {
                Bukkit.unloadWorld(Bukkit.getWorld("plugins/SkyBlock/Islands/" + SID + "_nether"), false);
            }
            WorldAPI.deleteWorld(island_world);
            WorldAPI.deleteWorld(island_nether);
            ArrayList<String> memberList = new ArrayList<>();
            ConfigAPI.checkEntry("Insel.Mitglieder", memberList, playerFile, pF);
            memberList = (ArrayList<String>) pF.getList("Insel.Mitglieder");
            if(memberList!=null) {
                for (String tSID : memberList) {
                    String tSSID = tSID.toString().replace("-", "");
                    File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                    YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                    tF.set("Insel.Team.Enable", false);
                    tF.set("Insel.Team.Enable", null);
                    tF.set("Insel.Team.Player", null);
                    tF.set("Insel.Team.Invite", null);
                    tF.set("Spieler.Clear", true);
                    ConfigAPI.saveConfig(targetFile, tF);
                    ConfigAPI.reloadConfig(targetFile, tF);
                    if (Bukkit.getPlayer(UUID.fromString(tSID)) != null) {
                        Player t = Bukkit.getPlayer(UUID.fromString(tSID));
                        t.getInventory().clear();
                        t.setLevel(0);
                        t.setExp(0);
                        tF.set("Spieler.Clear", false);
                        tF.set("Spieler.Clear", null);
                        ConfigAPI.saveConfig(targetFile, tF);
                        ConfigAPI.reloadConfig(targetFile, tF);
                        API.sendErrorMessage(t, MessageManager.getMessage(MessageManager.Message.Island_TEAM_CLOSED,PlayerAPI.getLanguage(t)));
                        User tU = Zyneon.getAPI().getOnlineUser(t.getUniqueId());
                    }
                }
            }
            p.getInventory().clear();
            p.setLevel(0);
            p.setExp(0);
            playerFile.delete();
            PlayerAPI.registerPlayer(p);
            PlayerAPI.saveLastLoc(p);
            API.sendMessage(p, MessageManager.getMessage(MessageManager.Message.Island_RESET,PlayerAPI.getLanguage(p)));
            User pU = Zyneon.getAPI().getOnlineUser(p.getUniqueId());
        }
    }

    public static void toggleVisits(Player p) {
        String SID = p.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.contains("Insel.Besuch")) {
            if(pF.getBoolean("Insel.Besuch")) {
                pF.set("Insel.Besuch",false);
            } else {
                pF.set("Insel.Besuch",true);
            }
        } else {
            pF.set("Insel.Besuch",true);
        }
        ConfigAPI.saveConfig(playerFile,pF);
    }

    public static ArrayList<String> getTopList() {
        if(mySQL) {
            IslandEco.checkTable();
            try {
                ArrayList<String> Toplist = new ArrayList<>();
                Toplist.clear();
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM ecodatabase ORDER BY Eco DESC");
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(rs.getString(1)));
                    Toplist.add(WorldAPI.getIslandName(player,true)+" §8-§r "+WorldAPI.getIslandLevel(player.getUniqueId().toString()));
                }
                return Toplist;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static void sendMessage(CommandSender s,String message) {
        s.sendMessage(Strings.prefix("SkyBlock")+message.replace("&&","%and%").replace("&","§").replace("%and&","&"));
        if(s instanceof Player p) {
            p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public static void sendErrorMessage(CommandSender s,String message) {
        s.sendMessage("§c"+message.replace("&&","%and%").replace("&","§").replace("%and&","&"));
        if(s instanceof Player p) {
            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public static boolean getTopList(CommandSender s) {
        MessageManager.Language language;
        if(s instanceof Player) {
            language = PlayerAPI.getLanguage((Player)s);
        } else {
            language = MessageManager.Language.GERMAN;
        }
        if(mySQL) {
            ArrayList<String> Toplist = getTopList();
            int size = Toplist.size();
            if(size > 4) {
                API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.Island_TOP_HEADER,language));
                API.sendMessage(s, MessageManager.getMessage(MessageManager.Message.Island_TOP_LINE,language));
                API.sendMessage(s, "§a1.§8 - §a" + Toplist.get(0));
                API.sendMessage(s, "§e2.§8 - §e" + Toplist.get(1));
                API.sendMessage(s, "§e3.§8 - §e" + Toplist.get(2));
                API.sendMessage(s, "§74.§8 - §7" + Toplist.get(3));
                API.sendMessage(s, "§75.§8 - §7" + Toplist.get(4));
                return true;
            } else {
                API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.Island_TOP_LOADING,language));
                return false;
            }
        } else {
            API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.Island_TOP_ERROR,language));
            return false;
        }
    }

    public static int skylockInt;
    public static String animatedSkyBlock() {
        int state = skylockInt;
        String skyblock = "§fSKYBLOCK";
        if(state == 1) {
            skyblock = "§6S§fKYBLOCK";
        } else if(state == 2) {
            skyblock = "§fS§6K§fYBLOCK";
        } else if(state == 3) {
            skyblock = "§fSK§6Y§fBLOCK";
        } else if(state == 4) {
            skyblock = "§fSKY§6B§fLOCK";
        } else if(state == 5) {
            skyblock = "§fSKYB§6L§fOCK";
        } else if(state == 6) {
            skyblock = "§fSKYBL§6O§fCK";
        } else if(state == 7) {
            skyblock = "§fSKYBLO§6C§fK";
        } else if(state == 8) {
            skyblock = "§fSKYBLOC§6K§f";
        }
        return skyblock;
    }
}