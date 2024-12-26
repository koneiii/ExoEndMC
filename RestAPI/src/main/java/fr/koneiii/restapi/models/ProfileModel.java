package fr.koneiii.restapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "profiles")
public class ProfileModel {

    @Id
    private final UUID uniqueId;
    private String name;
    private int coins;
    private transient boolean isOnline;

    public ProfileModel(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.coins = 10;
        this.isOnline = false;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
