package com.zyneonstudios.nerotvlive.skyblock.manager;

import static com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager.Message.*;
import static com.zyneonstudios.nerotvlive.skyblock.manager.MessageManager.Language.*;

public class MessageManager {

    public static String getMessage(Message message,Language language) {
        String Return;

        if (message.equals(NoPerms)) {
            if (language.equals(GERMAN)) {
                Return = "Dir ist nicht erlaubt, das zu tun§8!";
            } else {
                Return = "§cYou aren't allowed to do that§8!";
            }

        } else if (message.equals(NoPlayer)) {
            if (language.equals(GERMAN)) {
                Return = "§cSpieler nicht gefunden§8!";
            } else {
                Return = "§cPlayer not found§8!";
            }

        } else if (message.equals(NeedPlayer)) {
            if (language.equals(GERMAN)) {
                Return = "§cDazu musst du ein Spieler sein§8!";
            } else {
                Return = "§cYou need to be a player to do that§8!";
            }

        } else if (message.equals(ChangedGamemode)) {
            if (language.equals(GERMAN)) {
                Return = "§7Dein Spielmodus wurde auf §e%gamemode%§7 gesetzt§8!";
            } else {
                Return = "§7Your gamemode has been changed to §e%gamemode%§8!";
            }

        } else if (message.equals(Gamemode_SURVIVAL)) {
            if (language.equals(GERMAN)) {
                Return = "Überleben";
            } else {
                Return = "survival mode";
            }

        } else if (message.equals(Gamemode_CREATIVE)) {
            if (language.equals(GERMAN)) {
                Return = "Kreativ";
            } else {
                Return = "creative mode";
            }

        } else if (message.equals(Gamemode_ADVENTURE)) {
            if (language.equals(GERMAN)) {
                Return = "Abenteuer";
            } else {
                Return = "adventure mode";
            }

        } else if (message.equals(Gamemode_SPECTATOR)) {
            if (language.equals(GERMAN)) {
                Return = "Zuschauer";
            } else {
                Return = "spectator mode";
            }

        } else if (message.equals(ChangedAnotherGamemode)) {
            if (language.equals(GERMAN)) {
                Return = "§7Du hast den Spielmodus von §e%player%§7 auf §a%gamemode%§7 gesetzt§8!";
            } else {
                Return = "§7You've changed §e%player%'s§7 gamemode to §a%gamemode%§8!";
            }

        } else if (message.equals(Syntax_GAMEMODE)) {
            if (language.equals(GERMAN)) {
                Return = "§4Syntax: §c/gamemode [0-3] §7[Spieler]";
            } else {
                Return = "§4Syntax: §c/gamemode [0-3] §7[Player]";
            }

        } else if (message.equals(Message.Syntax_BUILD)) {
            if (language.equals(GERMAN)) {
                Return = "§4Syntax: §c/build §7[Spieler]";
            } else {
                Return = "§4Syntax: §c/build §7[Player]";
            }

        } else if (message.equals(NeedIslandOwner)) {
            if (language.equals(GERMAN)) {
                Return = "§cDazu müsstest du der Inselinhaber sein§8!";
            } else {
                Return = "§cYou aren't the island owner§8!";
            }

        } else if (message.equals(Island_TEAM_CLOSED)) {
            if (language.equals(GERMAN)) {
                Return = "§cDas Team, indem du warst, wurde aufgelöst§8!";
            } else {
                Return = "§cThe team you were in has been deleted§8!";
            }

        } else if (message.equals(Island_RESET)) {
            if (language.equals(GERMAN)) {
                Return = "§7Du hast deine Insel §cgelöscht§8!§7 Mit §e/insel§7 kannst du dir eine neue erstellen§8!";
            } else {
                Return = "§7Your island has been deleted§8! §7Type §e/island§7 to create a new one§8!";
            }

        } else if (message.equals(Island_TOP_HEADER)) {
            if (language.equals(GERMAN)) {
                Return = "§7Top-Inseln§8:";
            } else {
                Return = "§7Top-Islands§8:";
            }

        } else if (message.equals(Island_TOP_LINE)) {
            if (language.equals(GERMAN)) {
                Return = "§7Platz §8- §7Name §8- §fLevel";
            } else {
                Return = "§7Place §8- §7Name §8- §fLevel";
            }

        } else if (message.equals(Message.Island_TOP_LOADING)) {
            if (language.equals(GERMAN)) {
                Return = "§cEs sind nicht genug Spieler in der Liste§8. §cVersuche es später erneut§8!";
            } else {
                Return = "§cThere aren't enough players on the list§8. §cPlease try again later§8!";
            }

        } else if (message.equals(Message.Island_TOP_ERROR)) {
            if (language.equals(GERMAN)) {
                Return = "§cDie Topliste konnte nicht geladen werden, da keine Datenbankverbindung besteht§8!";
            } else {
                Return = "§cThe toplist couldn't be loaded because there is no database connection§8!";
            }

        } else if (message.equals(Message.Serverswitch_ERROR)) {
            if (language.equals(GERMAN)) {
                Return = "§cEs ist ein Fehler beim Senden des Spielers aufgetreten§8. §cZiel§8: §c%server%";
            } else {
                Return = "§cThere was an error sending the player to \"%server%\"§8!";
            }

        } else if (message.equals(Message.init_COMMAND)) {
            if (language.equals(GERMAN)) {
                Return = "§f  -> §7lade Kommando §e\"%command%§e\"§7...";
            } else {
                Return = "§f  -> §7loading command §e\"%command%§e\"§7...";
            }

        } else if (message.equals(init_LISTENER)) {
            if (language.equals(GERMAN)) {
                Return = "§f  -> §7lade Listener §e\"%listener%§e\"§7...";
            } else {
                Return = "§f  -> §7loading listener §e\"%listener%§e\"§7...";
            }

        } else if (message.equals(BedrockUser)) {
            if (language.equals(GERMAN)) {
                Return = "§e%player%§7 ist ein Bedrock-User§8!";
            } else {
                Return = "§e%player%§7 is a Bedrock-user§8!";
            }

        } else if (message.equals(restart_TITLE)) {
            if (language.equals(GERMAN)) {
                Return = "§fIn §e%seconds%§f Sekunden§8...";
            } else {
                Return = "§fThe server is restarting§8...";
            }

        } else if (message.equals(restart_SUBTITLE)) {
            if (language.equals(GERMAN)) {
                Return = "§8...§7startet der Server neu§8!";
            } else {
                Return = "§8...§7in §e%seconds% §7seconds§8!";
            }

        } else if (message.equals(restart_MESSAGE)) {
            if (language.equals(GERMAN)) {
                Return = "§cWICHTIG§8: §7Serverneustart in §e%seconds% Sekunden§8!";
            } else {
                Return = "§cWARNING§8: §7Restart in §e%seconds% seconds§8!";
            }

        } else if (message.equals(restart_START)) {
            if (language.equals(GERMAN)) {
                Return = "§7Du hast den §eStopvorgang§7 gestartet§8.";
            } else {
                Return = "§7You started the §estop process§7§8.";
            }

        } else if (message.equals(restart_ERROR)) {
            if (language.equals(GERMAN)) {
                Return = "§cDer Server fährt bereits herunter§8!";
            } else {
                Return = "§cThe server is already shutting down§8!";
            }

        } else if (message.equals(balanceHUD_TOGGLED)) {
            if (language.equals(GERMAN)) {
                Return = "§7Die Sichtbarkeit deiner Geldanzeige steht nun auf§8: §e%bool%";
            } else {
                Return = "§7Changed the visibility of your balance HUD to§8: §e%bool%";
            }

        } else if(message.equals(Message.Build_SELF)) {
            if(language.equals(GERMAN)) {
                Return = "§7Dein §eBaumodus§7 steht nun auf§8: §a%bool%";
            } else {
                Return = "§7Toggled your §eBuild-Mode§7 to§8: §a%bool%";
            }

        } else if(message.equals(Message.Build_OTHER)) {
            if (language.equals(GERMAN)) {
                Return = "§e%player%'s §7Baumodus steht nun auf§8: §a%bool%";
            } else {
                Return = "§7Toggled §e%player%'s §7Build-Mode to§8: §a%bool%";
            }

        } else if(message.equals(Message.Chatclear)) {
            if (language.equals(GERMAN)) {
                Return = "§7Der Chat wurde geleert, du kannst ihn jedoch immer noch sehen§8!";
            } else {
                Return = "§7The chat was cleared but you still have the permission to see it§8.";
            }

        } else if(message.equals(Message.Language_BEDROCKTITLE)) {
            if (language.equals(GERMAN)) {
                Return = "§9Wähle deine Sprache aus";
            } else {
                Return = "§9Select your language";
            }

        } else if(message.equals(Message.Exit)) {
            if (language.equals(GERMAN)) {
                Return = "Verlassen/Schließen";
            } else {
                Return = "Exit/Close";
            }

        } else if(message.equals(Message.Language_SELF)) {
            if (language.equals(GERMAN)) {
                Return = "§7Du hast die §aSprache§7 auf §eDeutsch§7 gestellt§8!";
            } else {
                Return = "§7You set the §alanguage§7 to §eEnglish§8!";
            }

        } else if(message.equals(Message.afk_KICK)) {
            if (language.equals(GERMAN)) {
                Return = "§cDu warst zu lange inaktiv.";
            } else {
                Return = "§cYou've been inactive for too long.";
            }

        } else if(message.equals(Message.afk_MESSAGE1)) {
            if(language.equals(GERMAN)) {
                Return = "§7Du bist §eAFK§8! §cAFK-Kick in 10 Minuten§8.";
            } else {
                Return = "§7You are §eAFK§8! §c10 Minutes remaining until the AFK-kick§8!";
            }

        } else if(message.equals(Message.afk_MESSAGE2)) {
            if(language.equals(GERMAN)) {
                Return = "§7Du bist seit 10 Minuten §eAFK§8! §cAFK-Kick in 5 Minuten§8.";
            } else {
                Return = "§7You are §eAFK§7 since 10 minutes§8! §c5 Minutes remaining until the AFK-kick§8!";
            }

        } else if(message.equals(Message.coins)) {
            if(language.equals(GERMAN)) {
                Return = "Münzen";
            } else {
                Return = "Coins";
            }

        } else if(message.equals(rankLeading)) {
            if(language.equals(GERMAN)) {
                Return = "Leitung";
            } else {
                Return = "Leading";
            }

        } else if(message.equals(rankTeam)) {
            Return = "Team";

        } else if(message.equals(rankCreator)) {
            Return = "Creator";

        } else if(message.equals(rankPremium)) {
            if(language.equals(GERMAN)) {
                Return = "Premium";
            } else {
                Return = "VIP";
            }

        } else if(message.equals(rank)) {
            if(language.equals(GERMAN)) {
                Return = "Rang";
            } else {
                Return = "Rank";
            }

        } else if(message.equals(rankUser)) {
            if(language.equals(GERMAN)) {
                Return = "Spieler";
            } else {
                Return = "Player";
            }

        } else if(message.equals(rainToggle_success)) {
            if(language.equals(GERMAN)) {
                Return = "§7Der §eNiederschlag§7 deiner Insel steht nun auf§8: §a%type%";
            } else {
                Return = "§7You've set the §edownfall§7 to§8: §a%type%";
            }

        } else if(message.equals(noPremium)) {
            if(language.equals(GERMAN)) {
                Return = "§7Du hast eine §6Premiumfuntkion§7 entdeckt§8. §7Diese kannst du nur mit dem §6Premium§8-§7Rang §8(oder höher)§7 nutzen§8. §7Mehr dazu§8: §9§nhttps://zyneonstudios.com/premium";
            } else {
                Return = "§7You've discovered a §6VIP-feature§8! §7This is only accessable with the §6VIP§8-§7rank §8(or higher)§8. §7More§8: §9§nhttps://zyneon.org/vip";
            }

        } else if(message.equals(Automatic)) {
            if(language.equals(GERMAN)) {
                Return = "Automatisch";
            } else {
                Return = "Automatic";
            }

        } else if(message.equals(Enabled)) {
            if(language.equals(GERMAN)) {
                Return = "Aktiviert";
            } else {
                Return = "Enabled";
            }

        } else if(message.equals(Disabled)) {
            if(language.equals(GERMAN)) {
                Return = "Deaktiviert";
            } else {
                Return = "Disabled";
            }

        } else if(message.equals(Thunder)) {
            if(language.equals(GERMAN)) {
                Return = "Gewitter";
            } else {
                Return = "Thunder";
            }

        } else if(message.equals(Downfall)) {
            if(language.equals(GERMAN)) {
                Return = "Niederschlag";
            } else {
                Return = "Downfall";
            }

        } else if(message.equals(biomeStickDescription)) {
            if(language.equals(GERMAN)) {
                Return = "§7Klicke in die Luft§8,§7 um das Biom auszuwählen§8,";
            } else {
                Return = "§7Click in the air to select the biome§8.";
            }

        } else if(message.equals(biomeStickDescription1)) {
            if(language.equals(GERMAN)) {
                Return = "§7welches du setzen willst§8.)";
            } else {
                Return = "§7Click at any block of the chunk§8,)";
            }


        } else if(message.equals(biomeStickDescription2)) {
            if(language.equals(GERMAN)) {
                Return = "§7Klicke auf den Block§8,";
            } else {
                Return = "you want do replace with the new biome§8!";
            }


        } else if(message.equals(biomeStickDescription3)) {
            if(language.equals(GERMAN)) {
                Return = "§7dessen Chunk du in das ausgewählte Biom setzen willst§8! §c(Dieses Item verändert keine Blöcke)";
            } else {
                Return = "§c(This item doesn't change any block)";
            }


        } else if(message.equals(biomeStickSuccess)) {
            if(language.equals(GERMAN)) {
                Return = "§7Du hast das §eBiom§7 zu §a%biome%§7 geändert§8! §cWICHTIG§8: §7Rejoine§8,§7 um den Unterschied zu sehen§8!";
            } else {
                Return = "§7You§8'§7ve changed the §ebiome§7 to §a%biome%§8.§c WARNING§8: §7You need to rejoin§8,§7 to be able to see the change§8!";
            }

        } else {
            if (language.equals(GERMAN)) {
                Return = "§cNachricht nicht gefunden§8!";
            } else {
                Return = "§cMessage not found§8!";
            }
        }
        return Return;
    }

    public enum Message {
        NoPerms,
        NoPlayer,
        NeedPlayer,
        NeedIslandOwner,
        BedrockUser,
        ChangedGamemode,
        ChangedAnotherGamemode,
        Syntax_GAMEMODE,
        Gamemode_SURVIVAL,
        Gamemode_CREATIVE,
        Gamemode_ADVENTURE,
        Gamemode_SPECTATOR,
        Island_TEAM_CLOSED,
        Island_RESET,
        Island_TOP_HEADER,
        Island_TOP_LINE,
        Island_TOP_ERROR,
        Island_TOP_LOADING,
        Serverswitch_ERROR,
        init_COMMAND,
        init_LISTENER,
        restart_TITLE,
        restart_SUBTITLE,
        restart_MESSAGE,
        restart_START,
        restart_ERROR,
        balanceHUD_TOGGLED,
        Syntax_BUILD,
        Build_SELF,
        Build_OTHER,
        Chatclear,
        Language_BEDROCKTITLE,
        Language_SELF,
        Exit,
        afk_KICK,
        afk_MESSAGE1,
        afk_MESSAGE2,
        coins,
        rank,
        rankLeading,
        rankTeam,
        rankCreator,
        rankPremium,
        rankUser,
        noPremium,
        rainToggle_success,
        Automatic,
        Disabled,
        Enabled,
        Thunder,
        Downfall,
        biomeStickDescription,
        biomeStickDescription1,
        biomeStickDescription2,
        biomeStickDescription3,
        biomeStickSuccess
    }

    public enum Language {
        AUTOMATIC,
        ENGLISH,
        GERMAN
    }
}