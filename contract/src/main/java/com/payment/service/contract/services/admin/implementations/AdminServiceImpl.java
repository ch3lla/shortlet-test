package com.payment.service.contract.services.admin.implementations;

import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.admin.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must not be null");
        }

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        List<Object[]> results = profileRepository.findBestProfession(start, end);

        if (!results.isEmpty()) {
            return (String) results.get(0)[0];
        }
        throw new IllegalStateException("No profession data found for the given period");
    }

    @Override
    public Page<Profile> getBestClients(LocalDate start, LocalDate end, int limit) {
        if (limit <= 0) {
            limit = 2;
        }
        Pageable pageable = PageRequest.of(0, limit);
        return profileRepository.findBestClients(start, end, pageable);
    }
}
