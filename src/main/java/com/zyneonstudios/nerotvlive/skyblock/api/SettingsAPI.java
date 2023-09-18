package com.zyneonstudios.nerotvlive.skyblock.api;

import com.zyneonstudios.nerotvlive.skyblock.manager.ItemManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingsAPI {

    public static String getRainMode(Player player) {
        World island = IslandAPI.getIsland(player);
        if(Boolean.TRUE.equals(island.getGameRuleValue(GameRule.DO_WEATHER_CYCLE))) {
            return MessageManager.getMessage(MessageManager.Message.Automatic,PlayerAPI.getLanguage(player));
        } else {
            if(island.isThundering()) {
                return MessageManager.getMessage(MessageManager.Message.Thunder,PlayerAPI.getLanguage(player));
            } else if(island.hasStorm()) {
                return MessageManager.getMessage(MessageManager.Message.Enabled,PlayerAPI.getLanguage(player));
            } else {
                return MessageManager.getMessage(MessageManager.Message.Disabled,PlayerAPI.getLanguage(player));
            }
        }
    }

    public static ItemStack rainItem(Player player) {
        World island = IslandAPI.getIsland(player);
        if(Boolean.TRUE.equals(island.getGameRuleValue(GameRule.DO_WEATHER_CYCLE))) {
            return ItemManager.createGuiItem(Material.LIME_WOOL,"§a"+ MessageManager.getMessage(MessageManager.Message.Downfall,PlayerAPI.getLanguage(player)),"§7"+ MessageManager.getMessage(MessageManager.Message.Automatic,PlayerAPI.getLanguage(player)));
        } else {
            if(island.isThundering()) {
                return ItemManager.createGuiItem(Material.LIME_WOOL,"§4"+ MessageManager.getMessage(MessageManager.Message.Downfall,PlayerAPI.getLanguage(player)),"§7"+ MessageManager.getMessage(MessageManager.Message.Thunder,PlayerAPI.getLanguage(player)));
            } else if(island.hasStorm()) {
                return ItemManager.createGuiItem(Material.LIME_WOOL,"§c"+ MessageManager.getMessage(MessageManager.Message.Downfall,PlayerAPI.getLanguage(player)),"§7"+ MessageManager.getMessage(MessageManager.Message.Enabled,PlayerAPI.getLanguage(player)));
            } else {
                return ItemManager.createGuiItem(Material.LIME_WOOL,"§2"+ MessageManager.getMessage(MessageManager.Message.Downfall,PlayerAPI.getLanguage(player)),"§7"+ MessageManager.getMessage(MessageManager.Message.Disabled,PlayerAPI.getLanguage(player)));
            }
        }
    }

    public static void toggleRain(Player player) {
        if(PlayerAPI.getMemberType(player).equals(MemberType.OWNER)) {
            if(PlayerAPI.isVIP(player)) {
                World island = IslandAPI.getIsland(player);
                if(getRainMode(player).equals(MessageManager.getMessage(MessageManager.Message.Automatic,PlayerAPI.getLanguage(player)))) {
                    island.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
                    island.setStorm(true);
                    island.setThundering(true);
                    API.sendMessage(player, MessageManager.getMessage(MessageManager.Message.rainToggle_success,PlayerAPI.getLanguage(player)).replace("%type%",getRainMode(player)));
                } else if(getRainMode(player).equals(MessageManager.getMessage(MessageManager.Message.Thunder,PlayerAPI.getLanguage(player)))) {
                    island.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
                    island.setStorm(true);
                    island.setThundering(false);
                    API.sendMessage(player, MessageManager.getMessage(MessageManager.Message.rainToggle_success,PlayerAPI.getLanguage(player)).replace("%type%",getRainMode(player)));
                } else if(getRainMode(player).equals(MessageManager.getMessage(MessageManager.Message.Enabled,PlayerAPI.getLanguage(player)))) {
                    island.setGameRule(GameRule.DO_WEATHER_CYCLE,false);
                    island.setThundering(false);
                    island.setStorm(false);
                    API.sendMessage(player, MessageManager.getMessage(MessageManager.Message.rainToggle_success,PlayerAPI.getLanguage(player)).replace("%type%",getRainMode(player)));
                } else {
                    island.setGameRule(GameRule.DO_WEATHER_CYCLE,true);
                    API.sendMessage(player, MessageManager.getMessage(MessageManager.Message.rainToggle_success,PlayerAPI.getLanguage(player)).replace("%type%",getRainMode(player)));
                }
            } else {
                API.sendErrorMessage(player, MessageManager.getMessage(MessageManager.Message.noPremium,PlayerAPI.getLanguage(player)));
            }
        } else {
            API.sendErrorMessage(player, MessageManager.getMessage(MessageManager.Message.NeedIslandOwner,PlayerAPI.getLanguage(player)));
        }
    }
}