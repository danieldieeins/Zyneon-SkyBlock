package com.zyneonstudios.nerotvlive.skyblock.objects.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.zyneonstudios.api.paper.generators.VoidGenerator;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.objects.player.SkyUser;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.IslandEco;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Island {

    private ArrayList<OfflinePlayer> team;
    private SkyUser owner;
    private IslandEco eco;
    private World overworld;
    private World the_nether;
    //private World the_end;

    public Island(SkyUser owner) {
        this.owner = owner;
        this.overworld = Bukkit.createWorld(new WorldCreator("plugins/SkyBlock/Islands/"+owner.getUUID().toString()+"/"+owner.getIslandInt()+"/overworld").generator(new VoidGenerator()).generateStructures(false));
        this.the_nether = Bukkit.createWorld(new WorldCreator("plugins/SkyBlock/Islands/"+owner.getUUID().toString()+"/"+owner.getIslandInt()+"/the_nether").generator(new VoidGenerator()).environment(World.Environment.NETHER).generateStructures(false));
    }

    public void create() {
        createOverworld();
        createNether();
        //createEnd();
    }

    public void createOverworld() {
        File schematic = new File("plugins/SkyBlock/Schematics/overworld.schem");
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        Clipboard clipboard;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
            clipboard = reader.read();
        } catch (FileNotFoundException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Datei nicht gefunden§8!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Es ist ein Fehler aufgetreten§8!");
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(overworld))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(0,75,0))
                    .build();
            Operations.complete(operation);
        }
        overworld.setSpawnLocation(new Location(overworld,0.5,75,0.5));
    }

    public void createNether() {
        File schematic = new File("plugins/SkyBlock/Schematics/nether.schem");
        ClipboardFormat format = ClipboardFormats.findByFile(schematic);
        Clipboard clipboard;
        try (ClipboardReader reader = format.getReader(new FileInputStream(schematic))) {
            clipboard = reader.read();
        } catch (FileNotFoundException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Datei nicht gefunden§8!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            clipboard = null;
            Bukkit.getConsoleSender().sendMessage("§4FEHLER§8:§c Es ist ein Fehler aufgetreten§8!");
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(new BukkitWorld(the_nether))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(0,75,0))
                    .build();
            Operations.complete(operation);
        }
        the_nether.setSpawnLocation(new Location(the_nether,0.5,75,0.5));
    }

    public World getOverworld() {
        return overworld;
    }

    public World getNether() {
        return the_nether;
    }

    //public World getTheEnd() {
    //    return the_end;
    //}

    public void destroyObject() {
        this.team = null;
        this.eco = null;
        this.owner = null;
        overworld.save();
        for(Player all:overworld.getPlayers()) {
            all.teleport(API.getSpawn());
        }
        Bukkit.unloadWorld(overworld,true);
        this.overworld = null;
        the_nether.save();
        Bukkit.unloadWorld(the_nether,true);
        this.the_nether = null;
        //the_end.save();
        //Bukkit.unloadWorld(the_end,true);
        //this.the_end = null;
    }
}