package fr.koneiii.restapi.services;

import fr.koneiii.restapi.models.ProfileModel;
import fr.koneiii.restapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private Map<UUID, ProfileModel> profiles = new HashMap<>();

    public ProfileModel createProfile(UUID uniqueId, String name) {
        if (profiles.containsKey(uniqueId)) {
            ProfileModel profileModel = profiles.get(uniqueId);
            if(!profileModel.getName().equals(name)) {
                profileModel.setName(name);
                this.profileRepository.save(profileModel);
            }
            return profileModel;
        }
        if (this.profileRepository.existsById(uniqueId.toString())) {
            ProfileModel profile = this.profileRepository.findById(uniqueId.toString()).get();
            if(!profile.getName().equals(name)) {
                profile.setName(name);
                this.profileRepository.save(profile);
            }
            this.profiles.put(uniqueId, profile);
            return profile;
        }
        ProfileModel profile = new ProfileModel(uniqueId, name);
        this.profiles.put(uniqueId, profile);
        this.profileRepository.save(profile);
        return profile;
    }


    public ProfileModel getProfile(UUID uniqueId) {
        if (profiles.containsKey(uniqueId)) {
            return profiles.get(uniqueId);
        }
        if (this.profileRepository.existsById(uniqueId.toString())) {
            ProfileModel profile = this.profileRepository.findById(uniqueId.toString()).get();
            this.profiles.put(uniqueId, profile);
            return profile;
        }
        return null;
    }

    public void saveProfile(ProfileModel profile) {
        this.profiles.put(profile.getUniqueId(), profile);
        this.profileRepository.save(profile);
    }

    public void saveProfile(UUID uniqueId) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return;
        }
        this.profileRepository.save(profile);
    }


    public ProfileModel updateProfile(ProfileModel profile) {
        this.profiles.put(profile.getUniqueId(), profile);
        return profile;
    }

    public ProfileModel updateCoins(UUID uniqueId, int coins) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return null;
        }
        profile.setCoins(coins);
        return profile;
    }

    public ProfileModel updateOnline(UUID uniqueId, boolean isOnline) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return null;
        }
        profile.setOnline(isOnline);
        return profile;
    }



}
