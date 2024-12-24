package fr.koneiii.restapi.controllers;

import fr.koneiii.common.models.ProfilePostModel;
import fr.koneiii.restapi.models.ProfileModel;
import fr.koneiii.restapi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileModel> createProfile(@RequestBody ProfilePostModel request) {
        return ResponseEntity.ok(profileService.createProfile(request.uniqueId(), request.name()));
    }

    @GetMapping("/get")
    public ResponseEntity<ProfileModel> getProfile(UUID uniqueId) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/setcoins")
    public ResponseEntity<ProfileModel> setCoins(UUID uniqueId, int coins) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profileService.updateCoins(uniqueId, coins);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/setonline")
    public ResponseEntity<ProfileModel> setOnline(UUID uniqueId, boolean isOnline) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profileService.updateOnline(uniqueId, isOnline);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/disconnect")
    public ResponseEntity<ProfileModel> disconnect(UUID uniqueId) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profileService.saveProfile(uniqueId);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/connect")
    public ResponseEntity<ProfileModel> connect(UUID uniqueId) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profileService.updateOnline(uniqueId, true);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/save")
    public ResponseEntity<ProfileModel> saveProfile(UUID uniqueId) {
        ProfileModel profile = profileService.getProfile(uniqueId);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        profileService.saveProfile(profile);
        return ResponseEntity.ok(profile);
    }


}
