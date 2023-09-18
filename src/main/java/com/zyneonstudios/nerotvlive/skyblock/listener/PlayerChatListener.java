package com.zyneonstudios.nerotvlive.skyblock.listener;

import com.zyneonstudios.api.paper.events.ZyneonChatEvent;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(ZyneonChatEvent e) {
        if(API.isStringBlocked(e.getMessage())) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            p.sendMessage("§4Achtung:§c Achte auf deine Wortwahl, oder es wird eine Strafe mit sich führen.");
            PlayerAPI.playNewSound(p, NewSound.ENTITY_BAT_DEATH);
            PlayerAPI.playNewSound(p, NewSound.ENTITY_BLAZE_DEATH);
            PlayerAPI.playNewSound(p, NewSound.BLOCK_ANVIL_BREAK);
            API.sendErrorMessage(Bukkit.getConsoleSender(), "§4" + p.getName() + "§c hat versucht §4\"" + e.getMessage() + "§4\"§c zu schreiben, die Nachricht wurde aber blockiert!");
        }
    }

    @EventHandler
    public void on(PlayerGameModeChangeEvent e) {
        if(e.getNewGameMode()!=GameMode.SPECTATOR) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.setCancelled(true);
        }
    }
}