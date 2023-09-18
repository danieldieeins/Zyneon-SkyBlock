package com.zyneonstudios.nerotvlive.skyblock.objects.island;

import java.util.UUID;

public class IslandBalance {

    private UUID uuid;
    private double balance;

    public IslandBalance(UUID uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}