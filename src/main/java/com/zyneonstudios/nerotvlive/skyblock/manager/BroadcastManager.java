package com.zyneonstudios.nerotvlive.skyblock.manager;

import com.zyneonstudios.api.paper.Zyneon;
import com.zyneonstudios.nerotvlive.skyblock.api.ConfigAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.Preloader;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static org.bukkit.Bukkit.getServer;

public class BroadcastManager {

    private static Preloader MAIN;
    public BroadcastManager(final Preloader main) {
        this.MAIN = main;
    }

    static File Config = new File("plugins/SkyBlock/config.yml");
    static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(Config);
    static ArrayList<String> Messages = new ArrayList<>();

    private static void saveDefaultConfig() {
        ConfigAPI.checkEntry("Core.Settings.Broadcasts.Enable",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.EconomyDisplay.Enable",false,Config,cfg);
        ConfigAPI.checkEntry("Core.Settings.Broadcasts.SecondInterval",10,Config,cfg);
        ConfigAPI.checkEntry("Core.Strings.Broadcasts",Messages,Config,cfg);
        ConfigAPI.saveConfig(Config,cfg);
        ConfigAPI.reloadConfig(Config,cfg);
        Messages = (ArrayList<String>)cfg.getList("Core.Strings.Broadcasts");
    }

    public static void start() {
        saveDefaultConfig();
        sendScoreboard(getServer().getScheduler());
        if(cfg.getBoolean("Core.Settings.Broadcasts.Enable")) {
            startBroadcastTimer(getServer().getScheduler());
        }
    }

    private static void startBroadcastTimer(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(SkyBlock.getInstance(), () -> {
            Integer size = Messages.size();
            Integer random = ThreadLocalRandom.current().nextInt(0,size);
            Bukkit.broadcastMessage(API.Prefix+Messages.get(random).replace("&","§"));
            API.setTablist();
            startBroadcastTimer(scheduler);
        }, cfg.getLong("Core.Settings.Broadcasts.SecondInterval")*20);
    }

    private static void sendScoreboard(BukkitScheduler scheduler) {
        int scheduleId = scheduler.scheduleSyncDelayedTask(SkyBlock.getInstance(), () -> {
            for(Player all : Bukkit.getOnlinePlayers()) {
                PlayerAPI.setScoreboard(all);
                SkyUser u = API.getSkyBlockPlayer(all.getUniqueId());
                if(u.getIslandInt()==-1) {
                    if(!all.getOpenInventory().getTitle().contains("Wähle deine Insel")) {
                        InventoryManager.openIslandMenu(all);
                    }
                }
            }
            if(API.skylockInt == 8) {
                API.skylockInt = 0;
            } else if(API.skylockInt == 0) {
                API.skylockInt = 1;
            } else if(API.skylockInt == 1) {
                API.skylockInt = 2;
            } else if(API.skylockInt == 2) {
                API.skylockInt = 3;
            } else if(API.skylockInt == 3) {
                API.skylockInt = 4;
            } else if(API.skylockInt == 4) {
                API.skylockInt = 5;
            } else if(API.skylockInt == 5) {
                API.skylockInt = 6;
            } else if(API.skylockInt == 6) {
                API.skylockInt = 7;
            } else if(API.skylockInt == 7) {
                API.skylockInt = 8;
            }
            if(!API.date.equals(Zyneon.getAPI().getTime())) {
                API.date = Zyneon.getAPI().getTime();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    PlayerAPI.renewScoreboard(all);
                }
            }
            sendScoreboard(scheduler);
        },15);
    }
}