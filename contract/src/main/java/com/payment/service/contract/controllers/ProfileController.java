package com.payment.service.contract.controllers;

import com.payment.service.contract.models.Profile;
import com.payment.service.contract.services.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Endpoint to deposit money into a client's balance.
     * @param userId The ID of the client.
     * @param amount The amount to be deposited.
     * @return The updated profile with the new balance.
     */
    @PostMapping("/balances/deposit/{userId}")
    public ResponseEntity<?> depositToBalance(@RequestAttribute("profile") Profile profile, @PathVariable Integer userId, @RequestParam Double amount) {
        try {
            Profile updatedProfile = profileService.depositToBalance(profile, userId, amount);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
