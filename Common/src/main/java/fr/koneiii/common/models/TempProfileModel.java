package fr.koneiii.common.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TempProfileModel {
    private UUID uniqueId;
    private String name;
    private int coins;

    @JsonProperty("online")
    private boolean isOnline;

    public TempProfileModel() {
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
