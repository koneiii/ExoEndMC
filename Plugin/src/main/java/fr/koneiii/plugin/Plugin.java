package fr.koneiii.plugin;

import fr.koneiii.common.Common;
import fr.koneiii.plugin.commands.ProfileCommand;
import fr.koneiii.plugin.listeners.PlayerConnection;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public class Plugin extends JavaPlugin {

    private Common common;
    private Lamp<BukkitCommandActor> lamp;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if(getConfig().getString("token") == null || getConfig().getString("url") == null) {
            getLogger().severe("Token or URL is null, please set it in the config.yml");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.common = new Common(getConfig().getString("token"), getConfig().getString("url"));

        this.lamp = BukkitLamp.builder(this)
                .build();
        this.lamp.register(new ProfileCommand(common.getProfileManager()));

        this.getServer().getPluginManager().registerEvents(new PlayerConnection(common.getProfileManager()), this);
        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }

    public Common getCommon() {
        return common;
    }

}