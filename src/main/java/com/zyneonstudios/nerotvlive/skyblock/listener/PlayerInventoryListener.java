package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.SettingsAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.InventoryManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.ItemManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.island.Island;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getCurrentItem()!=null) {
            if (e.getCurrentItem().getItemMeta() != null) {
                if (e.getCurrentItem().getItemMeta().equals(ItemManager.Placeholder.getItemMeta())) {
                    e.setCancelled(true);
                    return;
                } else if (e.getCurrentItem().getItemMeta().equals(ItemManager.noIsland.getItemMeta())) {
                    Player p = (Player)e.getWhoClicked();
                    SkyUser u = API.getSkyBlockPlayer(p.getUniqueId());
                    u.setIslandInt(e.getSlot());
                    u.sendMessage("Deine Insel wird nun erstellt§8, §7dieser Vorgang kann etwas dauern§8.");
                    u.sendWarnMessage("ACHTUNG: Du wirst automatisch teleportiert, sobald die Insel erstellt und geladen wurde.");
                    Island island = new Island(u);
                    if(SkyBlock.islands.containsKey(u)) {
                        SkyBlock.islands.get(u).destroyObject();
                        SkyBlock.islands.remove(u);
                    }
                    SkyBlock.islands.put(u,island);
                    island.create();
                    p.teleport(new Location(island.getOverworld(),0.5,75,0.5));
                } else if (e.getCurrentItem().getItemMeta() != null) {
                    ItemStack Item = e.getCurrentItem();
                    ItemMeta ItemMeta = e.getCurrentItem().getItemMeta();
                    String ItemName = ItemMeta.getDisplayName();
                    ItemMeta.setDisplayName(ItemName);
                    Item.setItemMeta(ItemMeta);
                    if (ItemName.equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        if(e.getWhoClicked() instanceof Player p) {
            if(e.getClickedInventory()!=null) {
                if(e.getCurrentItem()!=null) {
                    ItemStack item = e.getCurrentItem();
                    if(item.getItemMeta()!=null) {
                        ItemMeta itemMeta = item.getItemMeta();
                        String itemName = itemMeta.getDisplayName();
                        if(item.equals(ItemManager.enableVisits(p))) {
                            e.setCancelled(true);
                            API.toggleVisits(p);
                            p.closeInventory();
                            InventoryManager.openIslandSettings(p);
                        } else if(itemName.equals(SettingsAPI.rainItem(p).getItemMeta().getDisplayName())) {
                            e.setCancelled(true);
                            SettingsAPI.toggleRain(p);
                            p.performCommand("settings");
                        } else if(itemName.equals(ItemManager.langAUTOMATIC.getItemMeta().getDisplayName())) {
                            p.closeInventory();
                            PlayerAPI.setLanguage(p, MessageManager.Language.AUTOMATIC);
                        } else if(itemName.equals(ItemManager.langENGLISH.getItemMeta().getDisplayName())) {
                            p.closeInventory();
                            PlayerAPI.setLanguage(p, MessageManager.Language.ENGLISH);
                        } else if(itemName.equals(ItemManager.langGERMAN.getItemMeta().getDisplayName())) {
                            p.closeInventory();
                            PlayerAPI.setLanguage(p, MessageManager.Language.GERMAN);
                        } else if(item.equals(ItemManager.Close)) {
                            p.closeInventory();
                            PlayerAPI.playNewSound(p,NewSound.BLOCK_ANVIL_BREAK);
                        } else if(item.equals(ItemManager.GameModeAdventure)) {
                            e.setCancelled(true);
                            p.performCommand("gamemode 2");
                            p.closeInventory();
                        } else if(item.equals(ItemManager.GameModeCreative)) {
                            e.setCancelled(true);
                            p.performCommand("gamemode 1");
                            p.closeInventory();
                        } else if(item.equals(ItemManager.GameModeSurvival)) {
                            e.setCancelled(true);
                            p.performCommand("gamemode 0");
                            p.closeInventory();
                        } else if(item.equals(ItemManager.resetIsland)) {
                            e.setCancelled(true);
                            API.resetIsland(p);
                        } else if(item.equals(ItemManager.islandReset)) {
                            e.setCancelled(true);
                            InventoryManager.openIslandReset(p);
                        } else if(item.equals(ItemManager.GameModeSpectator)) {
                            e.setCancelled(true);
                            p.performCommand("gamemode 3");
                            p.closeInventory();
                        }
                    }
                }
            }
        }
    }
}