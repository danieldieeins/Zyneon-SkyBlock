package com.zyneonstudios.nerotvlive.skyblock.commands;

import com.zyneonstudios.nerotvlive.skyblock.manager.ItemManager;
import com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager;
import com.zyneonstudios.nerotvlive.skyblock.objects.items.BiomeStick;
import com.zyneonstudios.nerotvlive.skyblock.api.API;
import com.zyneonstudios.nerotvlive.skyblock.api.PlayerAPI;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("Item")) {
            if(s instanceof Player) {
                Player p = (Player)s;
                if(PlayerAPI.isTeam(p)) {
                    if(args.length > 0) {
                        if(args[0].equalsIgnoreCase("biomestick")) {
                            p.getInventory().addItem(new BiomeStick(Biome.PLAINS).getItem());
                        } else {
                            if(ItemManager.checkItemStack(args[0].toUpperCase().replace("-","_"))) {
                                p.getInventory().addItem(new ItemStack(Material.valueOf(args[0])));
                            } else {
                                API.sendErrorMessage(p,"§c/item [item]");
                            }
                        }
                    } else {
                        API.sendErrorMessage(p,"§c/item [item]");
                    }
                } else {
                    API.sendErrorMessage(p, MessageManager.getMessage(MessageManager.Message.NoPerms,PlayerAPI.getLanguage(p)));
                }
            } else {
                API.sendErrorMessage(s, MessageManager.getMessage(MessageManager.Message.NeedPlayer, MessageManager.Language.GERMAN));
            }
        }
        return false;
    }
}
