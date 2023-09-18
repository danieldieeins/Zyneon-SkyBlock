package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.nerotvlive.skyblock.api.API;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFormListener implements Listener {

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {
        Material type = event.getBlock().getType();
        if (type == Material.WATER || type == Material.LAVA) {
            Block b = event.getToBlock();
            if (b.getType() == Material.AIR) {
                if (generatesCobble(type, b)) {
                    if(b.getLocation().getX()==0&&b.getLocation().getZ()==0) {
                        if(b.getLocation().getY()!=74||b.getLocation().getY()!=75||b.getLocation().getY()!=76) {
                            event.setCancelled(true);
                            b.setType(API.generatorBlock());
                        } else {
                            event.setCancelled(true);
                            b.setType(Material.AIR);
                        }
                    } else {
                        event.setCancelled(true);
                        b.setType(API.generatorBlock());
                    }
                }
            }
        }
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b){
        Material mirrorID1 = type == Material.WATER ? Material.LAVA : Material.WATER;
        Material mirrorID2 = type == Material.WATER ? Material.LAVA : Material.WATER;
        for (BlockFace face : faces){
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2){
                return true;
            }
        }
        return false;
    }
}