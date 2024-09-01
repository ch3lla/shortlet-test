package com.payment.service.contract.repositories;

import com.payment.service.contract.models.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    @Query("SELECT p.profession, SUM(j.price) as totalEarned " +
            "FROM Profile p " +
            "JOIN Contract c ON p = c.contractor " +
            "JOIN Job j ON c.id = j.contract.id " +
            "WHERE j.isPaid = true AND j.paidDate BETWEEN :start AND :end " +
            "GROUP BY p.profession " +
            "ORDER BY totalEarned DESC")

    List<Object[]> findBestProfession(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT p FROM Profile p WHERE p.role = 'client' AND " +
            "(SELECT SUM(j.price) FROM Job j WHERE j.contract.client.id = p.id " +
            "AND j.isPaid = true AND j.paidDate BETWEEN :start AND :end) > 0 " +
            "ORDER BY (SELECT SUM(j.price) FROM Job j WHERE j.contract.client.id = p.id " +
            "AND j.isPaid = true AND j.paidDate BETWEEN :start AND :end) DESC")
    Page<Profile> findBestClients(@Param("start") LocalDate start, @Param("end") LocalDate end, Pageable pageable);
}
