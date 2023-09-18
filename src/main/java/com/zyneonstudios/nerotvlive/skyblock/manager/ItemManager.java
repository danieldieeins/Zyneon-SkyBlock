package com.zyneonstudios.nerotvlive.skyblock.manager;

import com.zyneonstudios.nerotvlive.skyblock.api.ConfigAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ItemManager {

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore((List) Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack Placeholder = createGuiItem(
            Material.BLACK_STAINED_GLASS_PANE,
            "§0"
    );

    public static ItemStack noIsland = createGuiItem(
            Material.RED_CONCRETE,
            "§cKeine Insel",
            "§7Klicke um eine Insel zu erstellen§8."
    );

    public static ItemStack enableVisits(Player p) {
        ItemStack Return;
        String SID = p.getUniqueId().toString();
        String SSID = SID.replace("-","");
        File playerFile = new File("plugins/SkyBlock/Players/"+SSID+".yml");
        YamlConfiguration pF = YamlConfiguration.loadConfiguration(playerFile);
        if(pF.contains("Insel.Besuch")) {
            if(pF.getBoolean("Insel.Besuch")) {
                Return = createGuiItem(
                        Material.LIME_CONCRETE,
                        "§aBesuch",
                        "§7Besucher können deine Insel mit §e/insel [Inselname]§7 besuchen§8."
                );
            } else {
                Return = createGuiItem(
                        Material.RED_CONCRETE,
                        "§cBesuch",
                        "§7Besucher können deine Insel nicht besuchen§8."
                );
            }
        } else {
            pF.set("Insel.Besuch",false);
            ConfigAPI.saveConfig(playerFile,pF);
            return enableVisits(p);
        }
        return Return;
    }

    public static ItemStack resetIsland = createGuiItem(
            Material.RED_CONCRETE,
            "§4Insel löschen",
            "§cDamit löscht du:",
            "§8- §7Dein Insellevel",
            "§8- §7Alles auf deiner Insel",
            "§8- §7Dein Inselguthaben (Geld)",
            "§8- §7Dein Inventar und deine Level"
    );

    public static ItemStack islandReset = createGuiItem(
            Material.BARRIER,
            "§cInsel zurücksetzen",
            "§7Öffnet das Insel-Zurücksetzmenü"
    );

    public static ItemStack GameModeSurvival = createGuiItem(
            Material.APPLE,
            "§bÜberlebensmodus§r"
    );

    public static ItemStack GameModeCreative = createGuiItem(
            Material.GOLDEN_APPLE,
            "§bKreativmodus§r"
    );

    public static ItemStack GameModeAdventure = createGuiItem(
            Material.STICK,
            "§bAbenteuermodus§r"
    );

    public static ItemStack GameModeSpectator = createGuiItem(
            Material.DIAMOND_HELMET,
            "§bZuschauermodus§r"
    );

    public static ItemStack Close = createGuiItem(
            Material.BARRIER,
            "§cSchließen"
    );

    public static ItemStack backItem = createGuiItem(
            Material.SLIME_BALL,
            "§aAktionsmenü"
    );

    public static ItemStack entityRemover = createGuiItem(
            Material.STICK,
            "§n§o§cEntity-Remover§r"
    );

    public static boolean checkItemStack(String item) {
        item = item.toUpperCase().replace("-","_");
        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Material.getMaterial(item));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static ItemStack langAUTOMATIC = getLangFlag(MessageManager.Language.AUTOMATIC);
    public static ItemStack langGERMAN = getLangFlag(MessageManager.Language.GERMAN);
    public static ItemStack langENGLISH = getLangFlag(MessageManager.Language.ENGLISH);

    public static ItemStack getLangFlag(MessageManager.Language lang) {
        ItemStack Return = new ItemStack(Material.PLAYER_HEAD);
        if(lang.equals(MessageManager.Language.AUTOMATIC)) {
            //Return = getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGUwM2IwYzI0NjMzYThkNTRiNWQwNDYzYTlmNWNjZjdlZDg1MGU2Y2U4MTY3YWMwNjVlZTg5ODg5MDE2MjkwOSJ9fX0=");
            ItemMeta returnMeta = Return.getItemMeta();
            returnMeta.setDisplayName("§9Automatic §8| §9Automatisch §8(Client)");
            Return.setItemMeta(returnMeta);
        } else if(lang.equals(MessageManager.Language.GERMAN)) {
            //Return = getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==");
            ItemMeta returnMeta = Return.getItemMeta();
            returnMeta.setDisplayName("§9Deutsch §8(German)");
            Return.setItemMeta(returnMeta);
        } else if(lang.equals(MessageManager.Language.ENGLISH)) {
            //Return = getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=");
            ItemMeta returnMeta = Return.getItemMeta();
            returnMeta.setDisplayName("§9English §8(Englisch)");
            Return.setItemMeta(returnMeta);
        } else {
            //Return = getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGUwM2IwYzI0NjMzYThkNTRiNWQwNDYzYTlmNWNjZjdlZDg1MGU2Y2U4MTY3YWMwNjVlZTg5ODg5MDE2MjkwOSJ9fX0=");
            ItemMeta returnMeta = Return.getItemMeta();
            returnMeta.setDisplayName(lang.toString());
            Return.setItemMeta(returnMeta);
        }
        return Return;
    }
}