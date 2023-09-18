package com.zyneonstudios.nerotvlive.skyblock.utils.economy;

import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.ConfigAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.objects.island.IslandBalance;
import com.zyneonstudios.nerotvlive.skyblock.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class IslandEco implements Economy {

    public static boolean checkTable() {
        if(!MySQL.isConnected()) {
            MySQL.connect();
        }
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ecodatabase (UUID VARCHAR(100),Eco DOUBLE(30,2))");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPayment(UUID uuid,double price) {
        if(SkyBlock.getEco().hasAccount(uuid)) {
            double balance = SkyBlock.getEco().getBalance(uuid).getBalance();
            if(balance-price >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createAccount(UUID uuid) {
        set(uuid,0);
        return true;
    }

    public boolean hasAccount(UUID uuid) {
        if(API.mySQL) {
            return hasAccountSQL(uuid);
        } else {
            String SID = uuid.toString();
            String SSID = SID.replace("-", "");
            File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
            YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
            if (pF.getBoolean("Insel.Team.Enable")) {
                String tSID = pF.getString("Insel.Team.Player");
                String tSSID = tSID.replace("-", "");
                File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                return tF.contains("Insel.Geld");
            } else {
                return pF.contains("Insel.Geld");
            }
        }
    }

    public boolean hasAccountSQL(UUID uuid) {
        checkTable();
        String SID = uuid.toString();
        String SSID = SID.replace("-", "");
        File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if (pF.getBoolean("Insel.Team.Enable")) {
            String tSID = pF.getString("Insel.Team.Player");
            return hasAccountSQL(UUID.fromString(tSID));
        } else {
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean delete(UUID uuid) {
        if(!API.mySQL) {
            String SID = uuid.toString();
            String SSID = SID.replace("-", "");
            File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
            YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
            pF.set("Insel.Geld",null);
            ConfigAPI.saveConfig(playerFile,pF);
            return true;
        } else {
            return deleteSQL(uuid);
        }
    }

    public boolean deleteSQL(UUID uuid) {
        try {
            if (hasAccountSQL(uuid)) {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean withdraw(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() - amount);
    }

    public boolean deposit(UUID uuid, double amount) {
        return set(uuid, getBalance(uuid).getBalance() + amount);
    }

    public boolean set(UUID uuid, double amount) {
        if(API.mySQL) {
            return setSQL(uuid,amount);
        } else {
            if (amount < 0.0D) {
                return false;
            } else {
                String SID = uuid.toString();
                String SSID = SID.replace("-", "");
                File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
                YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
                if (pF.getBoolean("Insel.Team.Enable")) {
                    String tSID = pF.getString("Insel.Team.Player");
                    String tSSID = tSID.replace("-", "");
                    File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                    YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                    tF.set("Insel.Geld", amount);
                    ConfigAPI.saveConfig(targetFile, tF);
                } else {
                    pF.set("Insel.Geld", amount);
                    ConfigAPI.saveConfig(playerFile, pF);
                }
                if(Bukkit.getPlayer(uuid)!=null) {
                    PlayerAPI.renewScoreboard(Bukkit.getPlayer(uuid));
                }
                return true;
            }
        }
    }

    public boolean setSQL(UUID uuid, double amount) {
        String SID = uuid.toString();
        checkTable();
        String SSID = SID.replace("-", "");
        File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if (pF.getBoolean("Insel.Team.Enable")) {
            String tSID = pF.getString("Insel.Team.Player");
            if(Bukkit.getPlayer(uuid)!=null) {
                PlayerAPI.renewScoreboard(Bukkit.getPlayer(uuid));
            }
            return setSQL(UUID.fromString(tSID),amount);
        } else {
            if (amount < 0.0D) {
                return false;
            } else {
                try {
                    if (hasAccountSQL(uuid)) {
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, amount);
                    ps.executeUpdate();
                    if(Bukkit.getPlayer(uuid)!=null) {
                        PlayerAPI.renewScoreboard(Bukkit.getPlayer(uuid));
                    }
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    public boolean has(UUID uuid, double amount) {
        return (getBalance(uuid).getBalance() >= amount);
    }

    public IslandBalance getBalance(UUID uuid) {
        if(API.mySQL) {
            return getBalanceSQL(uuid);
        } else {
            String SID = uuid.toString();
            String SSID = SID.replace("-", "");
            File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
            YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
            double data;
            if (pF.getBoolean("Insel.Team.Enable")) {
                String tSID = pF.getString("Insel.Team.Player");
                String tSSID = tSID.replace("-", "");
                File targetFile = new File("plugins/SkyBlock/Players/" + tSSID + ".yml");
                YamlConfiguration tF = YamlConfiguration.loadConfiguration(targetFile);
                data = tF.getDouble("Insel.Geld");
            } else {
                data = pF.getDouble("Insel.Geld");
            }
            return new IslandBalance(uuid, data);
        }
    }

    public IslandBalance getBalanceSQL(UUID uuid) {
        checkTable();
        String SID = uuid.toString();
        String SSID = SID.replace("-", "");
        File playerFile = new File("plugins/SkyBlock/Players/" + SSID + ".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if (pF.getBoolean("Insel.Team.Enable")) {
            String tSID = pF.getString("Insel.Team.Player");
            return getBalanceSQL(UUID.fromString(tSID));
        } else {
            double data;
            if (hasAccount(uuid)) {
                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        data = rs.getDouble("Eco");
                    } else {
                        data = 0.0;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    data = 0.0;
                }
            } else {
                data = 0.0;
            }
            return new IslandBalance(uuid, data);
        }
    }

    public List<IslandBalance> getPlayers() {
       return null;
    }
}