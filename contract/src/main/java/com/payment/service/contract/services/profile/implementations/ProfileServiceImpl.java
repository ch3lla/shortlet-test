package com.payment.service.contract.services.profile.implementations;

import com.payment.service.contract.dto.ProfileDto;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.JobRepository;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.profile.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository, JobRepository jobRepository) {
        this.profileRepository = profileRepository;
        this.jobRepository = jobRepository;
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
    @Transactional
    public Profile depositToBalance(Profile client, Integer userId, Double amount) {
        double totalOutstandingPayments = jobRepository.sumOutstandingPaymentsByUserId(userId).orElse(0.0);
        if (amount > 0.25 * totalOutstandingPayments) {
            throw new IllegalStateException("Deposit exceeds 25% of total outstanding payments");
        }

        client.setBalance(client.getBalance() + amount);
        return profileRepository.save(client);

    }
}
