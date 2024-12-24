package fr.koneiii.plugin.commands;

import fr.koneiii.common.models.TempProfileModel;
import fr.koneiii.common.profile.ProfileManager;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.CommandPlaceholder;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command("profile")
public class ProfileCommand {

    private final ProfileManager profileManager;

    public ProfileCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @CommandPlaceholder
    public void profile(Player player) {
        try {
            TempProfileModel profile = profileManager.getProfile(player.getUniqueId());
            StringBuilder builder = new StringBuilder();
            builder.append("§eVoici votre profil: \n");
            builder.append("§eNom: §f").append(profile.getName()).append("\n");
            builder.append("§eCoins: §f").append(profile.getCoins()).append("\n");
            builder.append("§eEn ligne: §f").append(profile.isOnline() ? "Oui" : "Non").append("\n");
            player.sendMessage(builder.toString());
        } catch (Exception e) {
            player.sendMessage("Une erreur est survenue... Veuillez réessayer plus tard.");
            e.printStackTrace();
        }
    }

    @Command("setcoins")
    @CommandPermission("profile.setcoins")
    public void setCoins(Player player, int coins) {
        try {
            profileManager.updateCoins(player.getUniqueId(), coins);
            player.sendMessage("§aLes coins ont été mis à jour avec succès.");
        } catch (Exception e) {
            player.sendMessage("Une erreur est survenue... Veuillez réessayer plus tard.");
            e.printStackTrace();
        }
    }

}
