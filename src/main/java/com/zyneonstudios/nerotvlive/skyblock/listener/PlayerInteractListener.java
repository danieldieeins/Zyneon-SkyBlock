package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.manager.ItemManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.items.BiomeStick;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.SculkShrieker;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.PHYSICAL)) {
                if (!PlayerAPI.hasAllowBuild(p)) {
                    e.setCancelled(true);
                }
            }
        }
        if(p.getInventory().getItemInMainHand().getItemMeta()!=null) {
            if(p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 69) {
                    e.setCancelled(true);
                    ItemStack item = p.getInventory().getItemInMainHand();
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta.getDisplayName().contains("BiomeStick")) {
                        String biomeName = itemMeta.getDisplayName().replace("§bBiomeStick §8(§a", "").replace("§8)", "");
                        BiomeStick biomeStick = new BiomeStick(Biome.valueOf(biomeName));
                        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                            if(PlayerAPI.canPlayerBuild(p)||PlayerAPI.hasAllowBuild(p)) {
                                if (p.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                                    Chunk chunk = e.getClickedBlock().getChunk();
                                    biomeStick.changeBiome(chunk);
                                    item.setAmount(item.getAmount() - 1);
                                    API.sendMessage(p, "§7Du hast erfolgreich das Biom zu §e" + biomeStick.getBiome().toString() + "§7 gewechselt§8!");
                                    API.sendErrorMessage(p, "§4Achtung§8: §cRejoine, um die Änderung zu sehen§8!");
                                } else {
                                    API.sendErrorMessage(p,"§4Fehler§8: §cIn dieser Dimension wird der Biomestick noch nicht unterstützt§8!");
                                }
                            }
                        } else if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                            biomeStick.switchBiome();
                            p.getInventory().setItemInMainHand(null);
                            p.getInventory().setItemInMainHand(biomeStick.getItem());
                        }
                    }
                }
            }
        }
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if(e.getClickedBlock()!=null) {
                if(e.getClickedBlock().getLocation().getBlockY()==74) {
                    if(e.getClickedBlock().getLocation().getBlockX()==0&&e.getClickedBlock().getLocation().getBlockZ()==0) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        if(e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.FARMLAND) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        } else {
            if(p.getItemInHand().getType() == Material.STICK) {
                if (p.getItemInHand().getItemMeta().getDisplayName().equals(ItemManager.entityRemover.getItemMeta().getDisplayName())) {
                    if (p.hasPermission("zyneon.team")) {
                        e.getRightClicked().remove();
                        for (Entity entity : e.getRightClicked().getWorld().getEntities()) {
                            if (entity.getLocation().distance(e.getRightClicked().getLocation()) <= 1) {
                                if (entity instanceof ArmorStand) {
                                    entity.remove();
                                }
                            }
                        }
                    } else {
                        p.getItemInHand().setAmount(0);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(!(PlayerAPI.canPlayerBuild(p))) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player) {
            Player p = (Player)e.getDamager();
            if(!(PlayerAPI.canPlayerBuild(p))) {
                if(!PlayerAPI.hasAllowBuild(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(HangingBreakByEntityEvent e) {
        if(e.getRemover() instanceof Player) {
            Player p = (Player)e.getRemover();
            if(!(PlayerAPI.canPlayerBuild(p))) {
                if(!PlayerAPI.hasAllowBuild(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(HangingPlaceEvent e) {
        Player p = e.getPlayer();
        if(!(PlayerAPI.canPlayerBuild(p))) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(BlockPlaceEvent e) {
        if(e.getBlockPlaced().getLocation().getBlockY() == 74||e.getBlockPlaced().getLocation().getBlockY() == 75||e.getBlockPlaced().getLocation().getBlockY() == 76) {
            if(e.getBlockPlaced().getLocation().getBlockX() == 0&&e.getBlockPlaced().getLocation().getBlockZ() == 0) {
                e.setCancelled(true);
            }
        }
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent e) {
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPickup(PlayerPickupItemEvent e) {
        if(e.getItem().getItemStack().getItemMeta().getDisplayName().equals(ItemManager.backItem.getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucket(PlayerBucketFillEvent e) {
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucket(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBucket(PlayerBucketEntityEvent e) {
        Player p = e.getPlayer();
        if(!PlayerAPI.canPlayerBuild(p)) {
            if(!PlayerAPI.hasAllowBuild(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosion(ExplosionPrimeEvent e) {
        for(Player all : e.getEntity().getWorld().getPlayers()) {
            if(all.getLocation().distance(e.getEntity().getLocation()) <= e.getRadius()) {
                all.setHealth(all.getHealth()-15);
                all.playSound(all.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,100,100);
            }
        }
        e.getEntity().remove();
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCreateSpawn(CreatureSpawnEvent e) {
        if (e.getEntity().getType() == EntityType.WITHER) {
            for(Player all : e.getLocation().getWorld().getPlayers()) {
                API.sendErrorMessage(all,"§cWither können noch nicht beschworen werden. Bitte versuche es in einer späteren Version erneut. Behalte den ●-skyblock-changelog-Channel auf unserem Discord-Server im Auge für weitere Informationen. \n §4§nhttps://discord.com/invite/6CnEEJc");
            }
            e.getEntity().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(ProjectileHitEvent e) {
        if(e.getEntity().getShooter() != null) {
            if (e.getEntity().getShooter() instanceof Player) {
                Player p = (Player)e.getEntity().getShooter();
                if(!PlayerAPI.hasAllowBuild(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortal(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().getName().contains("_world")) {
            p.teleport(new Location(Bukkit.getWorld(p.getWorld().getName().replace("_world","_nether")),0,75,0));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockCreate(BlockPlaceEvent e) {
        if(e.getBlockPlaced().getType().equals(Material.SCULK_SHRIEKER)) {
            SculkShrieker schrieker = (SculkShrieker)e.getBlockPlaced();
        }
    }
}