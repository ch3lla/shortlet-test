package com.payment.service.contract.services.contract;

import com.payment.service.contract.models.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    /**
     * Create a new contract.
     * @param contract The contract to be created.
     * @return The created contract.
     */
    Contract createContract(Contract contract);

    /**
     * Retrieve a contract by its ID.
     * @param id The ID of the contract.
     * @return An Optional containing the contract if found, or empty if not.
     */
    Optional<Contract> getContractById(Integer id);

    /**
     * Retrieve a list of active contracts for a specific user (client or contractor).
     * @param userId The ID of the user.
     * @return A list of active contracts.
     */
    List<Contract> getActiveContractsByUserId(Integer userId);

    /**
     * Update an existing contract.
     * @param contract The contract with updated details.
     * @return The updated contract.
     */
    Contract updateContract(Contract contract);

    /**
     * Delete a contract by its ID.
     * @param id The ID of the contract to be deleted.
     */
    void deleteContract(Integer id);
}
