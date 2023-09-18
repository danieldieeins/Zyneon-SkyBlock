package com.zyneonstudios.nerotvlive.skyblock.utils.economy;

import com.zyneonstudios.nerotvlive.skyblock.objects.island.IslandBalance;

import java.util.List;
import java.util.UUID;

public interface Economy {

    boolean createAccount(UUID paramUUID);
    boolean hasAccount(UUID paramUUID);
    boolean delete(UUID paramUUID);
    boolean withdraw(UUID paramUUID, double paramDouble);
    boolean deposit(UUID paramUUID, double paramDouble);
    boolean set(UUID paramUUID, double paramDouble);
    boolean has(UUID paramUUID, double paramDouble);
    IslandBalance getBalance(UUID paramUUID);
    List<IslandBalance> getPlayers();
}