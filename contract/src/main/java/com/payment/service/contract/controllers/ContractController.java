package com.payment.service.contract.controllers;

import com.payment.service.contract.models.Contract;
import com.payment.service.contract.services.contract.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(
            @PathVariable Integer id,
            @RequestHeader("profileId") Integer profileId) {
        Optional<Contract> contractOpt = contractService.getContractById(id);

        if (contractOpt.isPresent()) {
            Contract contract = contractOpt.get();
            if (contract.getClient().getId().equals(profileId) ||
                    contract.getContractor().getId().equals(profileId)) {
                return ResponseEntity.ok(contract);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint to retrieve a list of active contracts associated with the requesting user.
     * @param profileId The ID of the profile making the request (from request headers).
     * @return A list of active contracts.
     */
    @GetMapping
    public ResponseEntity<List<Contract>> getActiveContracts(@RequestHeader("profileId") Integer profileId) {
        List<Contract> activeContracts = contractService.getActiveContractsByUserId(profileId);
        return ResponseEntity.ok(activeContracts);
    }
}
