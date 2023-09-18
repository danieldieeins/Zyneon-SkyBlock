package com.zyneonstudios.nerotvlive.skyblock.api;

import org.bukkit.Sound;

public class SoundAPI {

    public static Sound getNewSound(NewSound NewSound) {
        return Sound.valueOf(NewSound.toString());
    }
}