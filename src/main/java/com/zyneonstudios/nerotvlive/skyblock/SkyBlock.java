package com.zyneonstudios.nerotvlive.skyblock;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.api.utils.Strings;
import com.zyneonstudios.nerotvlive.Preloader;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.PluginChannel;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import com.zyneonstudios.nerotvlive.skyblock.commands.*;
import com.zyneonstudios.nerotvlive.skyblock.listener.*;
import com.zyneonstudios.nerotvlive.skyblock.manager.BroadcastManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.island.Island;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import com.zyneonstudios.nerotvlive.skyblock.utils.Glow;
import com.zyneonstudios.nerotvlive.skyblock.utils.MySQL;
import com.zyneonstudios.nerotvlive.skyblock.utils.Receiver;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.Economy;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.IslandEco;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.VaultEco;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;

import java.util.HashMap;

import static org.bukkit.Bukkit.getServer;

public class SkyBlock {

    public static HashMap<SkyUser, Island> islands = new HashMap<>();
    public static String getVersion() { return Preloader.getVersion(); }
    public static Preloader getInstance() { return Preloader.getInstance(); }
    public static PluginChannel getPluginChannel() { return Preloader.getPluginChannel(); }
    private static VaultEco vaultImpl;
    private static Economy eco;

    public static void onLoad() {
        Strings.setPrefixWord("SkyBlock");
        API.initConfig();
        API.sendInit();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wird geladen...");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fÜberprüfe Config und generiere fehlende Einträge...");
        if(ServerAPI.isMaintenance()) {
            for(Player all : Bukkit.getOnlinePlayers()) {
                if(!all.hasPermission("zyneon.bypassmaintenance")) {
                    all.kickPlayer("§4SkyBlock§c ist momentan in §4Wartungsarbeiten§c!");
                }
            }
        }
        if(API.PM.getPlugin("Vault")!=null) {
            eco = (Economy) new IslandEco();
            vaultImpl = new VaultEco();
        }
        API.setTablist();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fFertig!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wurde geladen!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        API.sendInit();
    }

    public static void onEnable() {
        API.sendInit();
        API.date = Zyneon.getAPI().getTime();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        API.setTablist();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wird aktivert...");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        if(API.PM.getPlugin("Vault")!=null) {
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fVault-Brücke wird geladen und aktiviert...");
            eco = (Economy) new IslandEco();
            vaultImpl = new VaultEco();
            if(setupEconomy()) {
                Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fVault-Brücke wurde geladen und aktiviert!");
            } else {
                Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§cVault-Brücke konnte nicht aktiviert oder geladen werden!");
            }
        }
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fSpigot- und LabyMod-Channel werden registriert und der Empfänger wird geladen...");
        getServer().getMessenger().registerIncomingPluginChannel(getInstance(),"base:bungee",new Receiver());
        getServer().getMessenger().registerIncomingPluginChannel(getInstance(), "labymod3:main",new Receiver());
        getServer().getMessenger().registerOutgoingPluginChannel(getInstance(),"base:spigot");
        getServer().getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");
        Glow.registerGlow();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fSpigot- und LabyMod-Channel wurden registriert und der Empfänger ist geladen!");
        if(API.mySQL) {
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fMySQL-Verbindung wird getestet...");
            IslandEco.checkTable();
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fFertig!");
        }
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        initCommands();
        initListener();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        BroadcastManager.start();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wurde aktiviert!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        API.sendInit();
        API.initCommandList();
        CommandSendListener.initBlocked();
    }

    public static void onDisable(boolean versionSwitch) {
        API.sendInit();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wird deaktiviert...");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"");
        for(Player all : Bukkit.getOnlinePlayers()) {
            PlayerAPI.saveLastLoc(all);
        }
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fEntregistrierung...");
        getServer().getMessenger().unregisterIncomingPluginChannel(getInstance(),"base:bungee",new Receiver());
        getServer().getMessenger().unregisterIncomingPluginChannel(getInstance(),"labymod3:main",new Receiver());
        getServer().getMessenger().unregisterOutgoingPluginChannel(getInstance(),"base:spigot");
        getServer().getMessenger().unregisterOutgoingPluginChannel(getInstance(), "BungeeCord");
        vaultImpl = null;
        eco = null;
        if(API.mySQL) {
            if(MySQL.isConnected()) {
                MySQL.disconnect();
            }
        }
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fFertig!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        if(versionSwitch) {
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§f§lDas Plugin wird auf eine neue Version aktualisiert...");
            Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        }
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fPlugin wurde deaktiviert!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        API.setTablist();
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§9SkyBlock "+getVersion()+"§f by §enerotvlive");
        API.sendInit();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"save-all");
    }

    private static void initCommands() {
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fCommands werden geladen...");
        API.initCommand(new MaintenanceCommand(),"Maintenance",true);
        API.initCommand(new IslandCommand(),"Island",true);
        API.initCommand(new TeamCommand(),"Team",true);
        API.initCommand(new BuildCommand(),"Build",true);
        API.initCommand(new WorldCommand(),"World",true);
        API.initCommand(new AuthorCommand(),"Author",true);
        API.initCommand(new WarpCommand(),"Warp",true);
        API.initCommand(new SpawnCommand(),"Spawn",true);
        API.initCommand(new MoneyCommand(),"Money",true);
        API.initCommand(new DisconnectCommand(),"Disconnect",true);
        API.initCommand(new GamemodeCommand(),"Gamemode",true);
        API.initCommand(new ClearchatCommand(),"Clearchat",false);
        API.initCommand(new DayCommand(),"Day",false);
        API.initCommand(new NightCommand(),"Night",false);
        API.initCommand(new PingCommand(),"Ping",false);
        API.initCommand(new DifficultyCommand(),"Difficulty",false); //Tabcompleter fehlt
        API.initCommand(new TimeCommand(),"Time",false); //Tabcompleter fehlt
        API.initCommand(new WeatherCommand(),"Weather",false); //Tabcompleter fehlt
        API.initCommand(new TeleportCommand(),"Teleport",false); //Tabcompleter fehlt
        API.initCommand(new VanishCommand(),"Vanish",false);
        API.initCommand(new TellCommand(),"Tell",false);
        API.initCommand(new FlyCommand(),"Fly",false);
        API.initCommand(new GodCommand(),"God",false);
        API.initCommand(new KillCommand(),"Kill",false);
        API.initCommand(new HealCommand(),"Heal",false);
        API.initCommand(new FeedCommand(),"Feed",false);
        API.initCommand(new SpeedCommand(),"Speed",false);
        API.initCommand(new SudoCommand(),"Sudo",false);
        API.initCommand(new SunCommand(),"Sun",false);
        API.initCommand(new RainCommand(),"Rain",false);
        API.initCommand(new ThunderCommand(),"Thunder",false);
        API.initCommand(new PayCommand(),"Pay",false);
        API.initCommand(new StopCommand(),"SRL",false);
        API.initCommand(new HologramCommand(),"Hologram",true);
        API.initCommand(new VillagerCommand(),"Villager",false);
        API.initCommand(new LanguageCommand(),"Language",true);
        API.initCommand(new Aliases(),"Settings",false);
        API.initCommand(new ItemCommand(),"Item",false);
        API.initCommand(new BiomestickCommand(),"BiomeStick",false);
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fCommands wurden geladen!");
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+" ");
    }

    private static void initListener() {
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fListener werden geladen...");
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerJoinListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerQuitListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerChatListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerInteractListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerPortalListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new CommandSendListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerInventoryListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new BlockFormListener(),getInstance());
        Zyneon.getAPI().initListenerClass(Bukkit.getPluginManager(),new PlayerMoveListener(),getInstance());
        Bukkit.getConsoleSender().sendMessage(Strings.prefix()+"§fListener wurden geladen!");
    }

    private static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class,vaultImpl,getInstance(),ServicePriority.Normal);
            return true;
        }
    }

    public static Economy getEco() {
        return eco;
    }

    public static void setPrefix(Player player) {
        String Name = player.getName();
        org.bukkit.scoreboard.Scoreboard Scoreboard = player.getScoreboard();
        if(Scoreboard.getTeam("03Spieler")==null) {
            Scoreboard.registerNewTeam("00000Team");
            Scoreboard.registerNewTeam("01Creator");
            Scoreboard.registerNewTeam("02Premium");
            Scoreboard.registerNewTeam("03Spieler");
            Scoreboard.getTeam("00000Team").setPrefix("§cTeam §8● §f");
            Scoreboard.getTeam("01Creator").setPrefix("§dCreator §8● §f");
            Scoreboard.getTeam("02Premium").setPrefix("§6Premium §8● §f");
            Scoreboard.getTeam("03Spieler").setPrefix("§7User §8● §f");
            Scoreboard.getTeam("00000Team").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("01Creator").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("02Premium").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("03Spieler").setCanSeeFriendlyInvisibles(false);
            Scoreboard.getTeam("00000Team").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("01Creator").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("02Premium").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            Scoreboard.getTeam("03Spieler").setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
        }
        for(Player p:Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("zyneon.team")) {
                Scoreboard.getTeam("00000Team").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("00000Team").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.creator")) {
                Scoreboard.getTeam("01Creator").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("01Creator").getPrefix() + Name);
            } else if (p.hasPermission("zyneon.premium")) {
                Scoreboard.getTeam("02Premium").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("02Premium").getPrefix() + Name);
            } else {
                Scoreboard.getTeam("03Spieler").addPlayer(p);
                p.setDisplayName(Scoreboard.getTeam("03Spieler").getPrefix() + Name);
            }
        }
    }
}