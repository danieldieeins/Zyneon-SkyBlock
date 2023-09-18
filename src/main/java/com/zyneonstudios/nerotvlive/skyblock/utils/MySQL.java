package com.zyneonstudios.nerotvlive.skyblock.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zyneonstudios.nerotvlive.skyblock.api.ConfigAPI;

public class MySQL {

    public static String host = ConfigAPI.cfg.getString("Core.Settings.MySQL.host");
    public static String port = ConfigAPI.cfg.getString("Core.Settings.MySQL.port");
    public static String database = ConfigAPI.cfg.getString("Core.Settings.MySQL.database");
    public static String username = ConfigAPI.cfg.getString("Core.Settings.MySQL.username");
    public static String password = ConfigAPI.cfg.getString("Core.Settings.MySQL.password");
    public static boolean isConnected() {
        return (con != null);
    }
    public static Connection getConnection() {
        return con;
    }
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database,username,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
    }
}