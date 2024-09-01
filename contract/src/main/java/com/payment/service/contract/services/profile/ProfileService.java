package com.payment.service.contract.services.profile;

import com.payment.service.contract.dto.ProfileDto;
import com.payment.service.contract.models.Profile;

import java.util.Optional;

public interface ProfileService {

    /**
     * Create a new profile.
     * @param profile The profile to be created.
     * @return The created profile.
     */
    Profile createProfile(ProfileDto profile);

    /**
     * Retrieve a profile by its ID.
     * @param id The ID of the profile.
     * @return An Optional containing the profile if found, or empty if not.
     */
    Optional<Profile> getProfileById(Integer id);

    /**
     * Update an existing profile.
     * @param profile The profile with updated details.
     * @return The updated profile.
     */
    Profile updateProfile(Profile profile);

    /**
     * Delete a profile by its ID.
     * @param id The ID of the profile to be deleted.
     */
    void deleteProfile(Integer id);

    /**
     * Deposit an amount into a client's balance.
     * @param userId The ID of the client.
     * @param amount The amount to be deposited.
     * @return The updated profile.
     */
    Profile depositToBalance(Integer userId, Double amount);
}

