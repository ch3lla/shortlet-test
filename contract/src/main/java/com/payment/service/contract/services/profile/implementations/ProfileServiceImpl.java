package com.payment.service.contract.services.profile.implementations;

import com.payment.service.contract.dto.ProfileDto;
import com.payment.service.contract.models.ContractStatus;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ContractRepository;
import com.payment.service.contract.repositories.JobRepository;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.profile.ProfileService;
import jakarta.transaction.Transactional;
import com.payment.service.contract.models.Contract;
import com.payment.service.contract.models.Job;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final JobRepository jobRepository;
    private final ContractRepository contractRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository, JobRepository jobRepository, ContractRepository contractRepository) {
        this.profileRepository = profileRepository;
        this.jobRepository = jobRepository;
        this.contractRepository = contractRepository;
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
    public Profile depositToBalance(Integer userId, Double amount) {
        Profile client = profileRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Client not found"));

        // Calculate the total outstanding payments for unpaid jobs
        double totalOutstandingPayments = jobRepository.sumOutstandingPaymentsByUserId(userId).orElse(0.0);

        // Check if the deposit amount exceeds 25% of total outstanding payments
        if (amount > 0.25 * totalOutstandingPayments) {
            throw new IllegalStateException("Deposit exceeds 25% of total outstanding payments");
        }

        // Update the client's balance
        client.setBalance(client.getBalance() + amount);
        return profileRepository.save(client);

    }
}
