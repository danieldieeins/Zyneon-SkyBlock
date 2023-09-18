package com.zyneonstudios.nerotvlive.skyblock.api;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.utils.Countdown;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerAPI {

    public static String formatMessage(String message) {
        return message.replace("&","§");
    }

    public static boolean scheduledShutdown() {
        if(!API.isStopping) {
            API.isStopping = true;
            new Countdown(27, SkyBlock.getInstance()) {
                @Override
                public void count(int current) {
                    if (current < 11) {
                        int cur = current - 1;
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title "+all.getName()+" title \""+ MessageManager.getMessage(MessageManager.Message.restart_TITLE,PlayerAPI.getLanguage(all)).replace("%seconds%",cur+"")+"\"");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title "+all.getName()+" subtitle \""+ MessageManager.getMessage(MessageManager.Message.restart_SUBTITLE,PlayerAPI.getLanguage(all)).replace("%seconds%",cur+"")+"\"");
                            PlayerAPI.playNewSound(all, NewSound.BLOCK_NOTE_BLOCK_PLING);
                        }
                        if (current <= 6) {
                            for(Player all:Bukkit.getOnlinePlayers()) {
                                all.sendMessage(MessageManager.getMessage(MessageManager.Message.restart_MESSAGE,PlayerAPI.getLanguage(all)).replace("%seconds%",cur+""));
                            }
                        }
                        if (current == 1) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.kickPlayer("§cSkyBlock startet neu§8...");
                            }
                        } else if (current == 0) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.kickPlayer("§cSkyBlock startet neu§8...");
                            }
                            Bukkit.shutdown();
                        }
                    } else {
                        if (current == 26) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                PlayerAPI.playNewSound(all, NewSound.BLOCK_NOTE_BLOCK_PLING);
                                all.sendMessage(MessageManager.getMessage(MessageManager.Message.restart_MESSAGE,PlayerAPI.getLanguage(all)).replace("%seconds%","25"));
                            }
                        } else if (current == 21) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                PlayerAPI.playNewSound(all, NewSound.BLOCK_NOTE_BLOCK_PLING);
                                all.sendMessage(MessageManager.getMessage(MessageManager.Message.restart_MESSAGE,PlayerAPI.getLanguage(all)).replace("%seconds%","20"));
                            }
                        } else if (current == 16) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                PlayerAPI.playNewSound(all, NewSound.BLOCK_NOTE_BLOCK_PLING);
                                all.sendMessage(MessageManager.getMessage(MessageManager.Message.restart_MESSAGE,PlayerAPI.getLanguage(all)).replace("%seconds%","15"));
                            }
                        } else if (current == 11) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title "+all.getName()+" title \""+ MessageManager.getMessage(MessageManager.Message.restart_TITLE,PlayerAPI.getLanguage(all)).replace("%seconds%","10")+"\"");
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"title "+all.getName()+" subtitle \""+ MessageManager.getMessage(MessageManager.Message.restart_SUBTITLE,PlayerAPI.getLanguage(all)).replace("%seconds%","10")+"\"");
                                PlayerAPI.playNewSound(all, NewSound.BLOCK_NOTE_BLOCK_PLING);
                                all.sendMessage(MessageManager.getMessage(MessageManager.Message.restart_MESSAGE,PlayerAPI.getLanguage(all)).replace("%seconds%","10"));
                            }
                        }
                    }
                }
            }.start();
            return true;
        } else {
            return false;
        }
    }

    public static int getServerVersion() {
        if(Bukkit.getVersion().contains("1.0")) {
            return 100;
        } else if(Bukkit.getVersion().contains("1.1.")) {
            return 101;
        } else if(Bukkit.getVersion().contains("1.2.")) {
            return 102;
        } else if(Bukkit.getVersion().contains("1.3.")) {
            return 103;
        } else if(Bukkit.getVersion().contains("1.4.")) {
            return 104;
        } else if(Bukkit.getVersion().contains("1.5.")) {
            return 105;
        } else if(Bukkit.getVersion().contains("1.6.")) {
            return 106;
        } else if(Bukkit.getVersion().contains("1.7.")) {
            return 107;
        } else if(Bukkit.getVersion().contains("1.8")) {
            return 108;
        } else if(Bukkit.getVersion().contains("1.9")) {
            return 109;
        } else if(Bukkit.getVersion().contains("1.10")) {
            return 110;
        } else if(Bukkit.getVersion().contains("1.11")) {
            return 111;
        } else if(Bukkit.getVersion().contains("1.12")) {
            return 112;
        } else if(Bukkit.getVersion().contains("1.13")) {
            return 113;
        } else if(Bukkit.getVersion().contains("1.14")) {
            return 114;
        } else if(Bukkit.getVersion().contains("1.15")) {
            return 115;
        } else if(Bukkit.getVersion().contains("1.16")) {
            return 116;
        } else if(Bukkit.getVersion().contains("1.17")) {
            return 117;
        } else if(Bukkit.getVersion().contains("1.18")) {
            return 118;
        } else if(Bukkit.getVersion().contains("1.19")) {
            return 119;
        } else {
            return 120;
        }
    }

    public static Boolean isLegacy() {
        return getServerVersion() < 113;
    }

    public static void setMaintenance(boolean state) {
        ConfigAPI.cfg.set("Core.Settings.Maintenance",state);
        ConfigAPI.saveConfig(ConfigAPI.config,ConfigAPI.cfg);
        ConfigAPI.reloadConfig(ConfigAPI.config,ConfigAPI.cfg);
    }

    public static void toggleMaintenance() {
        if(isMaintenance()) {
            setMaintenance(false);
        } else {
            setMaintenance(true);
        }
    }

    public static boolean isMaintenance() {
        return ConfigAPI.cfg.getBoolean("Core.Settings.Maintenance");
    }
}