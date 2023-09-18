package com.zyneonstudios.nerotvlive.skyblock.api;

import com.zyneonstudios.nerotvlive.skyblock.utils.CleanroomGenerator;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class WorldAPI {

    @Deprecated
    public static void createPlayerIsland(String SID) {
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world")==null) {
            if(!new File("plugins/SkyBlock/Islands/"+SID+"_world").exists()) {
                copyWorld(new File("plugins/SkyBlock/Islands/tja"),new File("plugins/SkyBlock/Islands/"+SID+"_world"));
            }
            createWorld(SID + "_world", World.Environment.NORMAL, WorldType.FLAT, false, true);
        }
        if(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_nether")==null) {
            if(!new File("plugins/SkyBlock/Islands/"+SID+"_nether").exists()) {
                copyWorld(new File("plugins/SkyBlock/Islands/tja_nether"),new File("plugins/SkyBlock/Islands/"+SID+"_nether"));
            }
            createWorld(SID+"_nether",World.Environment.NETHER,WorldType.FLAT,true,true);
        }
        Location worldSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world"),0.5,75,0.5,90,0);
        Location netherSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_nether"),0.5,75,0.5,90,0);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world").setSpawnLocation(worldSpawn);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_nether").setSpawnLocation(netherSpawn);
        worldSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world"),0,74,0);
        netherSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_nether"),0,74,0);
        worldSpawn.getBlock().setType(Material.BEDROCK);
        netherSpawn.getBlock().setType(Material.BEDROCK);
        setDifficulty(Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_world"),Difficulty.NORMAL);
        setDifficulty(Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_nether"),Difficulty.NORMAL);
        Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_world").setGameRule(GameRule.MOB_GRIEFING,true);
        Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_nether").setGameRule(GameRule.MOB_GRIEFING,true);
        Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_world").setGameRule(GameRule.RANDOM_TICK_SPEED,3);
        Bukkit.getWorld("plugins/SkyBlock/Islands/"+SID+"_nether").setGameRule(GameRule.RANDOM_TICK_SPEED,3);
    }

    @Deprecated
    public static void getBack(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.get("Spieler.LastLoc")!=null) {
            if (pF.getBoolean("Spieler.LastLoc")) {
                if(!pF.getString("Spieler.World").equals("world")) {
                    if (pF.getBoolean("Insel.Team.Enable")) {
                        String tSID = pF.getString("Insel.Team.Player");
                        if (pF.getString("Spieler.World").contains(tSID)) {
                            createPlayerIsland(tSID);
                            World W = Bukkit.getWorld(pF.getString("Spieler.World"));
                            double X = pF.getDouble("Spieler.X");
                            double Y = pF.getDouble("Spieler.Y");
                            double Z = pF.getDouble("Spieler.Z");
                            float p = (float) pF.getDouble("Spieler.p");
                            float y = (float) pF.getDouble("Spieler.y");
                            Location LastLoc = new Location(W, X, Y, Z, y, p);
                            player.teleport(LastLoc);
                        } else {
                            getPlayerIsland(player);
                        }
                    } else {
                        if (pF.getString("Spieler.World").contains(SID)) {
                            createPlayerIsland(SID);
                            World W = Bukkit.getWorld(pF.getString("Spieler.World"));
                            double X = pF.getDouble("Spieler.X");
                            double Y = pF.getDouble("Spieler.Y");
                            double Z = pF.getDouble("Spieler.Z");
                            float p = (float) pF.getDouble("Spieler.p");
                            float y = (float) pF.getDouble("Spieler.y");
                            Location LastLoc = new Location(W, X, Y, Z, y, p);
                            player.teleport(LastLoc);
                        } else {
                            getPlayerIsland(player);
                        }
                    }
                }
            } else {
                getPlayerIsland(player);
            }
        }
    }

    @Deprecated
    public static void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists()) target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException ignore) {
        }
    }

    @Deprecated
    public static void getPlayerIsland(Player player) {
        String SID = player.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.getBoolean("Insel.Team.Enable")) {
            String tSID = pF.getString("Insel.Team.Player").replace("-","");
            File targetFile = new File("plugins/SkyBlock/Players/"+tSID+".yml");
            YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
            if(tF.getBoolean("Insel.Team.Enable")) {
                API.sendErrorMessage(Bukkit.getConsoleSender(),"§cDer/Die Inselbesitzer/in deines Teams ist Mitglied einer anderen Insel! Warte bis er/sie diese verlässt, oder mache §4/team leave§c um sein/ihre Insel zu verlassen!");
            } else {
                createPlayerIsland(pF.getString("Insel.Team.Player"));
                Location worldSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+pF.getString("Insel.Team.Player") + "_world"), 0.5, 75, 0.5, 90, 0);
                player.teleport(worldSpawn);
            }
        } else {
            createPlayerIsland(SID);
            Location worldSpawn = new Location(Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world"),0.5,75,0.5,90,0);
            player.teleport(worldSpawn);
        }
    }

    @Deprecated
    public static void unloadIsland(String SID) {
        World world = Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world");
        if(world!=null) {
            kickAllPlayersInWorld(world);
            API.sendMessage(Bukkit.getConsoleSender(),"§8>> §7Entlade §e"+"plugins/SkyBlock/Islands/"+SID+"_world"+"§8...");
            Bukkit.unloadWorld(world,true);
        }
        World world1 = Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+SID+"_world");
        if(world!=null) {
            kickAllPlayersInWorld(world1);
            API.sendMessage(Bukkit.getConsoleSender(),"§8>> §7Entlade §e"+"plugins/SkyBlock/Islands/"+SID+"_nether"+"§8...");
            Bukkit.unloadWorld(world1,true);
        }
    }

    @Deprecated
    public static boolean hasActiveTeammember(String SID) {
        Integer in = 0;
        String SSID = SID.replace("-","");
        File targetFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
        YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
        ArrayList<String> members = new ArrayList<>();
        ConfigAPI.checkEntry("Insel.Mitglieder",members,targetFile,tF);
        members = (ArrayList<String>)tF.getList("Insel.Mitglieder");
        for(String member:members) {
            if(Bukkit.getPlayer(UUID.fromString(member))!=null) {
                in = in + 1;
            }
        }
        if(in>0) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public static World getPlayerWorld(Player player) {
        return player.getWorld();
    }

    @Deprecated
    public static String getPlayerWorldName(Player player) {
        return getPlayerWorld(player).getName();
    }

    @Deprecated
    public static World getWorld(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) == null) { return null; } else {
            return Bukkit.getServer().getWorld(worldname);
        }
    }

    @Deprecated
    public static Difficulty getDifficulty(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            return Bukkit.getServer().getWorld(worldname).getDifficulty();
        } else {
            return null;
        }
    }

    @Deprecated
    public static long getTime(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            return Bukkit.getServer().getWorld(worldname).getTime();
        } else {
            return 0;
        }
    }

    @Deprecated
    public static Weather getWeather(World world) {
        if(world.isThundering()) {
            return Weather.THUNDER;
        } else if(world.hasStorm()) {
            return Weather.RAIN;
        } else {
            return Weather.SUN;
        }
    }

    @Deprecated
    public static Weather getWeather(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            if(world.isThundering()) {
                return Weather.THUNDER;
            } else if(world.hasStorm()) {
                return Weather.RAIN;
            } else {
                return Weather.SUN;
            }
        } else {
            return null;
        }
    }

    @Deprecated
    public static void setTime(long time, World world) {
        world.setTime(time);
    }

    @Deprecated
    public static void setTime(long time, String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            world.setTime(time);
        }
    }

    @Deprecated
    public static void addTime(long time, World world) {
        long t = world.getTime()+time;
        world.setTime(t);
    }

    @Deprecated
    public static void addTime(long time, String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            long t = world.getTime()+time;
            world.setTime(t);
        }
    }

    @Deprecated
    public static void removeTime(long time, World world) {
        long t = world.getTime()-time;
        world.setTime(t);
    }

    @Deprecated
    public static void removeTime(long time, String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            long t = world.getTime()-time;
            world.setTime(t);
        }
    }

    @Deprecated
    public static void setSun(World world) {
        world.setThundering(false);
        world.setStorm(false);
    }

    @Deprecated
    public static void setSun(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            world.setThundering(false);
            world.setStorm(true);
        }
    }

    @Deprecated
    public static void setRain(World world) {
        world.setThundering(false);
        world.setStorm(true);
    }

    @Deprecated
    public static void setRain(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            world.setThundering(false);
            world.setStorm(true);
        }
    }

    @Deprecated
    public static void setStorm(World world) {
        world.setStorm(true);
        world.setThundering(true);
    }

    @Deprecated
    public static void setStorm(String worldname) {
        if(Bukkit.getServer().getWorld(worldname) != null) {
            World world = Bukkit.getServer().getWorld(worldname);
            world.setStorm(true);
            world.setThundering(true);
        }
    }

    @Deprecated
    public static void setDifficulty(World world, Difficulty difficulty) {
        world.setDifficulty(difficulty);
    }

    @Deprecated
    public static void setDifficulty(World world, String difficultyname) {
        Difficulty diff = resolveDifficulty(difficultyname);
        world.setDifficulty(diff);
    }

    @Deprecated
    public static void setDifficulty(String worldname, Difficulty difficulty) {
        World world = Bukkit.getServer().getWorld(worldname);
        if(world != null)  {
            world.setDifficulty(difficulty);
        }
    }

    @Deprecated
    public static void setDifficulty(String worldname, String difficultyname) {
        Difficulty diff = resolveDifficulty(difficultyname);
        World world = Bukkit.getServer().getWorld(worldname);
        if(world != null)  {
            world.setDifficulty(diff);
        }
    }

    @Deprecated
    public static void setWeather(World world,Weather weather) {
        if(weather.equals(Weather.RAIN)) {
            world.setThundering(false);
            world.setStorm(true);
        } else if(weather.equals(Weather.THUNDER)) {
            world.setStorm(true);
            world.setThundering(true);
        } else {
            world.setThundering(false);
            world.setStorm(false);
        }
    }

    @Deprecated
    public static Difficulty resolveDifficulty(String difficulty) {
        if(difficulty.equalsIgnoreCase("peaceful")) {
            return Difficulty.PEACEFUL;
        } else if(difficulty.equalsIgnoreCase("0")) {
            return Difficulty.PEACEFUL;
        } else if(difficulty.equalsIgnoreCase("easy")) {
            return Difficulty.EASY;
        } else if(difficulty.equalsIgnoreCase("1")) {
            return Difficulty.EASY;
        } else if(difficulty.equalsIgnoreCase("normal")) {
            return Difficulty.NORMAL;
        } else if(difficulty.equalsIgnoreCase("2")) {
            return Difficulty.NORMAL;
        } else if(difficulty.equalsIgnoreCase("hard")) {
            return Difficulty.HARD;
        } else if(difficulty.equalsIgnoreCase("3")) {
            return Difficulty.HARD;
        } else {
            return null;
        }
    }

    @Deprecated
    public static void createWorld(String mapName) {
        new WorldCreator("plugins/SkyBlock/Islands/"+mapName).environment(World.Environment.NORMAL).createWorld();
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.DO_FIRE_TICK,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.MOB_GRIEFING,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.SHOW_DEATH_MESSAGES,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
    }

    @Deprecated
    public static void createWorld(String mapName, World.Environment environment, WorldType type, Boolean generateStructures, Boolean cleanRoom) {
        if(cleanRoom) {
            new WorldCreator("plugins/SkyBlock/Islands/"+mapName).environment(environment).generator(new CleanroomGenerator()).generateStructures(generateStructures).type(type).createWorld();
        } else {
            new WorldCreator("plugins/SkyBlock/Islands/"+mapName).environment(environment).generateStructures(generateStructures).type(type).createWorld();
        }
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.DO_FIRE_TICK,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.MOB_GRIEFING,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.SHOW_DEATH_MESSAGES,false);
        Bukkit.getServer().getWorld("plugins/SkyBlock/Islands/"+mapName).setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
    }

    @Deprecated
    public static void kickAllPlayersInWorld(World world) {
        if(world!=null) {
            for (Player all : world.getPlayers()) {
                if(all!=null) {
                    API.sendErrorMessage(all, "§cDu kannst nicht mehr in auf dieser Insel sein!");
                    all.performCommand("spawn");
                    if(WarpAPI.isWarpEnabled("spawn")) {
                        all.teleport(WarpAPI.getWarp("spawn"));
                    }
                }
            }
        }
    }

    @Deprecated
    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    @Deprecated
    public static String getIslandName(OfflinePlayer player,boolean extended) {
        File playerFile = new File("plugins/SkyBlock/Players/"+player.getUniqueId().toString().replace("-","")+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.contains("Insel.Name")) {
            if(extended) {
                return pF.getString("Insel.Name")+" ("+player.getName()+")";
            } else {
                return pF.getString("Insel.Name");
            }
        } else {
            return player.getName();
        }
    }

    @Deprecated
    public static int getIslandLevel(String SID) {
        int i = (int)Math.round(SkyBlock.getEco().getBalance(UUID.fromString(SID)).getBalance()/10000);
        if(i<1) {
            i=1;
        }
        return i;
    }
}