package fr.koneiii.restapi.services;

import fr.koneiii.restapi.models.ProfileModel;
import fr.koneiii.restapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableCaching
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @CachePut(value = "profiles", key = "#uniqueId")
    public ProfileModel createProfile(UUID uniqueId, String name) {
        if (this.profileRepository.existsById(uniqueId.toString())) {
            ProfileModel profile = this.profileRepository.findById(uniqueId.toString()).get();
            if(!profile.getName().equals(name)) {
                profile.setName(name);
                return this.profileRepository.save(profile);
            }
            return profile;
        }
        ProfileModel profile = new ProfileModel(uniqueId, name);
        return this.profileRepository.save(profile);
    }

    @Cacheable(value = "profiles", key = "#uniqueId")
    public ProfileModel getProfile(UUID uniqueId) {
        return this.profileRepository.findById(uniqueId.toString()).orElse(null);
    }

    @CachePut(value = "profiles", key = "#profile.getUniqueId()")
    public ProfileModel saveProfile(ProfileModel profile) {
        return this.profileRepository.save(profile);
    }

    @CachePut(value = "profiles", key = "#uniqueId")
    public ProfileModel saveProfile(UUID uniqueId) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return null;
        }
        return this.profileRepository.save(profile);
    }

    @CachePut(value = "profiles", key = "#profile.getUniqueId()")
    public ProfileModel updateProfile(ProfileModel profile) {
        return this.profileRepository.save(profile);
    }

    @CachePut(value = "profiles", key = "#uniqueId")
    public ProfileModel updateCoins(UUID uniqueId, int coins) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return null;
        }
        profile.setCoins(coins);
        return this.profileRepository.save(profile);
    }

    @CachePut(value = "profiles", key = "#uniqueId")
    public ProfileModel updateOnline(UUID uniqueId, boolean isOnline) {
        ProfileModel profile = getProfile(uniqueId);
        if (profile == null) {
            return null;
        }
        profile.setOnline(isOnline);
        return this.profileRepository.save(profile);
    }
}