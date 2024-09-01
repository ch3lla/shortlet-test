package com.payment.service.contract.repositories;

import com.payment.service.contract.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    /**
     * Find unpaid jobs associated with a list of contract IDs.
     * @param contractIds The list of contract IDs.
     * @return A list of unpaid jobs.
     */
    List<Job> findByContractIdInAndIsPaidFalse(List<Integer> contractIds);

    @Query("SELECT SUM(j.price) FROM Job j " +
            "JOIN j.contract c " +
            "WHERE c.status = 'IN_PROGRESS' " +
            "AND j.isPaid = false " +
            "AND (c.client.id = :userId OR c.contractor.id = :userId)")
    Optional<Double> sumOutstandingPaymentsByUserId(@Param("userId") Integer userId);
}
