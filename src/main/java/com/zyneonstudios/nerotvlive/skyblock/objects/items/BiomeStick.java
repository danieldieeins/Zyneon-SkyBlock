package com.zyneonstudios.nerotvlive.skyblock.objects.items;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.block.Biome.*;

public class BiomeStick {

    private Biome biome;
    private String name;

    public BiomeStick(Biome biome) {
        this.biome = biome;
        name = "§bBiomeStick §8(§a"+biome.toString()+"§8)";
    }

    public void switchBiome() {
        if(biome == PLAINS) {
            biome = SUNFLOWER_PLAINS;
        } else if(biome == SUNFLOWER_PLAINS) {
            biome = SNOWY_PLAINS;
        } else if(biome == SNOWY_PLAINS) {
            biome = ICE_SPIKES;
        } else if(biome == ICE_SPIKES) {
            biome = DESERT;
        } else if(biome == DESERT) {
            biome = SWAMP;
        } else if(biome == SWAMP) {
            biome = MANGROVE_SWAMP;
        } else if(biome == MANGROVE_SWAMP) {
            biome = FOREST;
        } else if(biome == FOREST) {
            biome = FLOWER_FOREST;
        } else if(biome == FLOWER_FOREST) {
            biome = BIRCH_FOREST;
        } else if(biome == BIRCH_FOREST) {
            biome = DARK_FOREST;
        } else if(biome == DARK_FOREST) {
            biome = OLD_GROWTH_BIRCH_FOREST;
        } else if(biome == OLD_GROWTH_BIRCH_FOREST) {
            biome = OLD_GROWTH_PINE_TAIGA;
        } else if(biome == OLD_GROWTH_PINE_TAIGA) {
            biome = OLD_GROWTH_SPRUCE_TAIGA;
        } else if(biome == OLD_GROWTH_SPRUCE_TAIGA) {
            biome = TAIGA;
        } else if(biome == TAIGA) {
            biome = SNOWY_TAIGA;
        } else if(biome == SNOWY_TAIGA) {
            biome = SAVANNA;
        } else if(biome == SAVANNA) {
            biome = SAVANNA_PLATEAU;
        } else if(biome == SAVANNA_PLATEAU) {
            biome = WINDSWEPT_HILLS;
        } else if(biome == WINDSWEPT_HILLS) {
            biome = WINDSWEPT_GRAVELLY_HILLS;
        } else if(biome == WINDSWEPT_GRAVELLY_HILLS) {
            biome = WINDSWEPT_FOREST;
        } else if(biome == WINDSWEPT_FOREST) {
            biome = WINDSWEPT_SAVANNA;
        } else if(biome == WINDSWEPT_SAVANNA) {
            biome = JUNGLE;
        } else if(biome == JUNGLE) {
            biome = SPARSE_JUNGLE;
        } else if(biome == SPARSE_JUNGLE) {
            biome = BAMBOO_JUNGLE;
        } else if(biome == BAMBOO_JUNGLE) {
            biome = BADLANDS;
        } else if(biome == BADLANDS) {
            biome = ERODED_BADLANDS;
        } else if(biome == ERODED_BADLANDS) {
            biome = WOODED_BADLANDS;
        } else if(biome == WOODED_BADLANDS) {
            biome = MEADOW;
        } else if(biome == MEADOW) {
            biome = GROVE;
        } else if(biome == GROVE) {
            biome = SNOWY_SLOPES;
        } else if(biome == SNOWY_SLOPES) {
            biome = FROZEN_PEAKS;
        } else if(biome == FROZEN_PEAKS) {
            biome = JAGGED_PEAKS;
        } else if(biome == JAGGED_PEAKS) {
            biome = STONY_PEAKS;
        } else if(biome == STONY_PEAKS) {
            biome = RIVER;
        } else if(biome == RIVER) {
            biome = FROZEN_RIVER;
        } else if(biome == FROZEN_RIVER) {
            biome = BEACH;
        } else if(biome == BEACH) {
            biome = SNOWY_BEACH;
        } else if(biome == SNOWY_BEACH) {
            biome = STONY_SHORE;
        } else if(biome == STONY_SHORE) {
            biome = WARM_OCEAN;
        } else if(biome == WARM_OCEAN) {
            biome = LUKEWARM_OCEAN;
        } else if(biome == LUKEWARM_OCEAN) {
            biome = DEEP_LUKEWARM_OCEAN;
        } else if(biome == DEEP_LUKEWARM_OCEAN) {
            biome = OCEAN;
        } else if(biome == OCEAN) {
            biome = DEEP_OCEAN;
        } else if(biome == DEEP_OCEAN) {
            biome = COLD_OCEAN;
        } else if(biome == COLD_OCEAN) {
            biome = DEEP_COLD_OCEAN;
        } else if(biome == DEEP_COLD_OCEAN) {
            biome = FROZEN_OCEAN;
        } else if(biome == FROZEN_OCEAN) {
            biome = DEEP_FROZEN_OCEAN;
        } else if(biome == DEEP_FROZEN_OCEAN) {
            biome = MUSHROOM_FIELDS;
        } else if(biome == MUSHROOM_FIELDS) {
            biome = DRIPSTONE_CAVES;
        } else if(biome == DRIPSTONE_CAVES) {
            biome = LUSH_CAVES;
        } else if(biome == LUSH_CAVES) {
            biome = DEEP_DARK;
        } else if(biome == DEEP_DARK) {
            biome = PLAINS;
        } else {
            biome = PLAINS;
        }
        name = "§bBiomeStick §8(§a"+biome.toString()+"§8)";
    }

    public String getName() {
        return name;
    }

    public Biome getBiome() {
        return biome;
    }

    public void changeBiome(Chunk chunk) {
        int cX = chunk.getX() * 16;
        int cZ = chunk.getZ() * 16;
        World w = chunk.getWorld();
        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                w.setBiome(cX + x, cZ+ z, biome);
            }
        }
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.END_ROD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(69);
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
}