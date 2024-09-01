package com.payment.service.contract.services.admin.implementations;

import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.admin.AdminService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProfileRepository profileRepository;

    public AdminServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public String getBestProfession(LocalDate start, LocalDate end) {
        List<Object[]> results = profileRepository.findBestProfession(start, end);

        if (!results.isEmpty()) {
            return (String) results.get(0)[0];
        }
        throw new IllegalStateException("No profession data found for the given period");
    }

    @Override
    public List<Profile> getBestClients(LocalDate start, LocalDate end, int limit) {
        return profileRepository.findBestClients(start, end, limit);
    }
}
