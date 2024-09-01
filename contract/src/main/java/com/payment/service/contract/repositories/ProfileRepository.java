package com.payment.service.contract.repositories;

import com.payment.service.contract.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Object[]> findBestProfession(LocalDate start, LocalDate end);

    List<Profile> findBestClients(LocalDate start, LocalDate end, int limit);
}
