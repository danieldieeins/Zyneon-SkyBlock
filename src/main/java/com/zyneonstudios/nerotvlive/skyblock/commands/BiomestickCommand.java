package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.utils.economy.IslandEco;
import com.zyneonstudios.nerotvlive.skyblock.objects.items.BiomeStick;
import com.zyneonstudios.nerotvlive.skyblock.SkyBlock;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BiomestickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("BiomeStick")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("buy")) {
                        if(IslandEco.checkPayment(p.getUniqueId(),1000)) {
                            if(PlayerAPI.invFull(p)) {
                                API.sendErrorMessage(p,"§cDein Inventar ist voll§8!");
                            } else {
                                SkyBlock.getEco().withdraw(p.getUniqueId(),1000);
                                p.getInventory().addItem(new BiomeStick(Biome.PLAINS).getItem());
                                API.sendMessage(p,"§7Du hast erfolgreich §a1x BiomeStick§7 gekauft§8!");
                            }
                        } else {
                            API.sendErrorMessage(p,"§cDu hast dazu nicht genug Geld!");
                        }
                    }
                    return false;
                }
                TextComponent accepter = new TextComponent(API.Prefix+"§8» §aBiomeStick kaufen §71x für §e1000 Münzen§8. (Linksklick)");
                accepter.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/biomestick buy"));
                p.spigot().sendMessage(accepter);
            } else {
                API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NeedPlayer, MessageManager.Language.GERMAN));
            }
        }
        return false;
    }
}