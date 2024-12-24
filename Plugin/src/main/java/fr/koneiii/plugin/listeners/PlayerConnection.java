package fr.koneiii.plugin.listeners;

import fr.koneiii.common.profile.ProfileManager;
import jdk.jfr.Label;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    private final ProfileManager profileManager;

    public PlayerConnection(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        try {
            profileManager.createProfile(player.getUniqueId(), player.getName());
            profileManager.connect(player.getUniqueId());
            player.sendMessage("§eVotre profil a été chargé avec succès !");
        } catch (Exception e) {
            player.sendMessage("§cUne erreur est survenue lors du chargement de votre profil...");
            e.printStackTrace();
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        try {
            profileManager.disconnect(player.getUniqueId());
            player.sendMessage("§eVotre profil a été sauvegardé avec succès !");
        } catch (Exception e) {
            player.sendMessage("§cUne erreur est survenue lors de la sauvegarde de votre profil...");
            e.printStackTrace();
        }
    }
}
