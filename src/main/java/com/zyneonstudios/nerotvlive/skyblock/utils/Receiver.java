package com.zyneonstudios.nerotvlive.skyblock.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.gson.JsonObject;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.UUID;

public class Receiver implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if ( !channel.equalsIgnoreCase("base:bungee")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("spigot")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
        } else if(subChannel.contains("stopServer")) {
            ServerAPI.scheduledShutdown();
        } else if(subChannel.contains("playSound_")) {
            String data1 = in.readUTF();
            int data2 = in.readInt();
            if(Bukkit.getPlayer(data1)!=null) {
                String nS = subChannel.replace("playSound_","");
                Player p = Bukkit.getPlayer(data1);
                PlayerAPI.playNewSound(p, NewSound.valueOf(nS));
            }
        }
    }

    public JsonObject addSecret(JsonObject jsonObject, String hasKey, String key, UUID secret, String domain) {
        jsonObject.addProperty( hasKey, true );
        jsonObject.addProperty( key, secret.toString() + ":" + domain );
        return jsonObject;
    }
}