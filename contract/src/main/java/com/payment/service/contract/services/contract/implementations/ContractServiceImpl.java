package com.payment.service.contract.services.contract.implementations;

import com.payment.service.contract.dto.ContractDto;
import com.payment.service.contract.models.Contract;
import com.payment.service.contract.models.ContractStatus;
import com.payment.service.contract.repositories.ContractRepository;
import com.payment.service.contract.services.contract.ContractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository repository;

    public ContractServiceImpl(ContractRepository repository) {
        this.repository = repository;
    }


    @Override
    public Contract createContract(ContractDto contract) {
        return null;
    }

    @Override
    public Optional<Contract> getContractById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Contract> getActiveContractsByUserId(Integer userId) {
        List<Contract> contracts = repository.findByContractorIdOrClientId(userId);
        return contracts.stream()
                .filter(contract -> contract.getStatus() == ContractStatus.IN_PROGRESS)
                .collect(Collectors.toList());
    }

    @Override
    public Contract updateContract(Contract contract) {
        return null;
    }

    @Override
    public void deleteContract(Integer id) {

    }
}
