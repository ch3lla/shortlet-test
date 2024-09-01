package com.payment.service.contract.repositories;

import com.payment.service.contract.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    /**
     * Find contracts where the given id matches either the contractorId or clientId.
     * @param id The ID of the contractor or client.
     * @return A list of contracts associated with the provided ID.
     */
    @Query("SELECT c FROM Contract c WHERE c.contractor.id = :id OR c.client.id = :id")
    List<Contract> findByContractorIdOrClientId(@Param("id") Integer id);
}
