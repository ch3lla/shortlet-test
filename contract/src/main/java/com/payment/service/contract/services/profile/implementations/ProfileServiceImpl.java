package com.payment.service.contract.services.profile.implementations;

import com.payment.service.contract.dto.ProfileDto;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile createProfile(ProfileDto profile) {
        return null;
    }

    @Override
    public Optional<Profile> getProfileById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return null;
    }

    @Override
    public void deleteProfile(Integer id) {

    }

    @Override
    public Profile depositToBalance(Integer userId, Double amount) {
        return null;
    }
}
