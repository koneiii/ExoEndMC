package fr.koneiii.common;

import fr.koneiii.common.profile.ProfileManager;

public class Common {

    private final ProfileManager profileManager;

    public Common(String token, String url) {
        this.profileManager = new ProfileManager(token, url);
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

}