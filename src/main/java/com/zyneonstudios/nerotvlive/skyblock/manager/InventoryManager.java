package com.zyneonstudios.nerotvlive.skyblock.manager;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.SettingsAPI;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class InventoryManager {

    public static void openIslandMenu(Player p) {
        SkyUser u = API.getSkyBlockPlayer(p.getUniqueId());
        Inventory IslandSettings = Bukkit.createInventory(null, InventoryType.HOPPER, "§9Wähle deine Insel");
        IslandSettings.setItem(0, ItemManager.Placeholder);
        if(u.getIslandOne().exists()) {

        } else {
            IslandSettings.setItem(1, ItemManager.noIsland);
        }
        if(u.getIslandTwo().exists()) {

        } else {
            IslandSettings.setItem(2, ItemManager.noIsland);
        }
        if(u.getIslandThree().exists()) {

        } else {
            IslandSettings.setItem(3, ItemManager.noIsland);
        }
        IslandSettings.setItem(4, ItemManager.Placeholder);
        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
        p.openInventory(IslandSettings);
    }

    public static void openIslandSettings(Player p) {
        Inventory IslandSettings = Bukkit.createInventory(null, InventoryType.HOPPER, "§9Inseleinstellungen");
        IslandSettings.setItem(0, ItemManager.Placeholder);
        IslandSettings.setItem(1, ItemManager.enableVisits(p));
        IslandSettings.setItem(2, SettingsAPI.rainItem(p));
        IslandSettings.setItem(3, ItemManager.islandReset);
        IslandSettings.setItem(4, ItemManager.Placeholder);
        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
        p.openInventory(IslandSettings);
    }

    public static void openInv_GameMode(Player p) {
        Inventory INV006 = Bukkit.createInventory(null, InventoryType.HOPPER, "§bWähle §b§ldeinen§r§b Spielmodus§r§8...§r");
        INV006.setItem(0, ItemManager.GameModeSurvival);
        INV006.setItem(1, ItemManager.GameModeCreative);
        INV006.setItem(2, ItemManager.GameModeAdventure);
        INV006.setItem(3, ItemManager.GameModeSpectator);
        INV006.setItem(4, ItemManager.Close);
        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
        p.openInventory(INV006);
    }

    public static void openIslandReset(Player p) {
        Inventory IslandSettings = Bukkit.createInventory(null, InventoryType.HOPPER, "§cWillst du deine Insel löschen?");
        IslandSettings.setItem(0, ItemManager.Placeholder);
        IslandSettings.setItem(1, ItemManager.Placeholder);
        IslandSettings.setItem(2, ItemManager.resetIsland);
        IslandSettings.setItem(3, ItemManager.Placeholder);
        IslandSettings.setItem(4, ItemManager.Placeholder);
        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
        p.openInventory(IslandSettings);
    }

    public static void openLanguageMenu(Player p) {
        Inventory languageInventory = Bukkit.createInventory(null, InventoryType.HOPPER,"");
        languageInventory.setItem(0, ItemManager.langENGLISH);
        languageInventory.setItem(1, ItemManager.Placeholder);
        languageInventory.setItem(2, ItemManager.langAUTOMATIC);
        languageInventory.setItem(3, ItemManager.Placeholder);
        languageInventory.setItem(4, ItemManager.langGERMAN);
        p.openInventory(languageInventory);
        PlayerAPI.playNewSound(p, NewSound.ENTITY_CHICKEN_EGG);
    }
}