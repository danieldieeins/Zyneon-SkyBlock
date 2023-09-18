package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.NewSound;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import com.zyneonstudios.nerotvlive.skyblock.api.ServerAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TellCommand implements CommandExecutor {

    private void sendSyntax(Player p) {
        PlayerAPI.sendPlayerMessage(p, "§4Fehler: §c/msg §c[Spieler] [Nachricht]", NewSound.BLOCK_ANVIL_BREAK);
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Tell")) {
            if(!(s instanceof Player)) {
                s.sendMessage("Dazu musst du ein Spieler sein!");
            } else {
                Player p = (Player)s;
                String PN = p.getName();
                if(args.length >= 2) {
                    String msg="";
                    for(int i=1;i<args.length;i++) {
                        msg=msg+args[i]+" ";
                    }
                    msg = ServerAPI.formatMessage(msg);
                    if(Bukkit.getPlayer(args[0]) != null) {
                        Player p2 = Bukkit.getPlayer(args[0]);
                        String P2N = p2.getName();
                        if(PN.equals(P2N)) {
                            p.sendMessage("§cSchreibe mit anderen, nicht mit dir selbst.");
                            PlayerAPI.playNewSound(p,NewSound.BLOCK_ANVIL_BREAK);
                        } else {
                            PlayerAPI.sendPlayerMessage(p, "§8[§7MSG§8] §6Du §f-> §e" + P2N + "§8: §7" + msg, NewSound.ENTITY_CAT_AMBIENT);
                            PlayerAPI.sendPlayerMessage(p2, "§8[§7MSG§8] §e" + PN + "§f -> §6Dir §8: §7" + msg, NewSound.ENTITY_CHICKEN_EGG);
                        }
                    } else {
                        API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPlayer, PlayerAPI.getLanguage(p)));
                    }
                } else {
                    sendSyntax(p);
                }
            }
        }
        return false;
    }
}