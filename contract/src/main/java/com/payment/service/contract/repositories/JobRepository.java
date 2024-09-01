package com.payment.service.contract.repositories;

import com.payment.service.contract.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    /**
     * Find unpaid jobs associated with a list of contract IDs.
     * @param contractIds The list of contract IDs.
     * @return A list of unpaid jobs.
     */
    List<Job> findByContractIdInAndIsPaidFalse(List<Integer> contractIds);
}
