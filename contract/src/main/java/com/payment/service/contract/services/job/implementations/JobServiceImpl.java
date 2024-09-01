package com.payment.service.contract.services.job.implementations;

import com.payment.service.contract.dto.JobDto;
import com.payment.service.contract.models.Contract;
import com.payment.service.contract.models.ContractStatus;
import com.payment.service.contract.models.Job;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.repositories.ContractRepository;
import com.payment.service.contract.repositories.JobRepository;
import com.payment.service.contract.repositories.ProfileRepository;
import com.payment.service.contract.services.job.JobService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ContractRepository contractRepository;
    private final ProfileRepository profileRepository;

    public JobServiceImpl(JobRepository jobRepository, ContractRepository contractRepository, ProfileRepository profileRepository) {
        this.jobRepository = jobRepository;
        this.contractRepository = contractRepository;
        this.profileRepository = profileRepository;
    }


    @Override
    public Job createJob(JobDto job) {
        return null;
    }

    @Override
    public Optional<Job> getJobById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Job> getUnpaidJobsByUserId(Integer userId) {
        List<Contract> activeContracts = contractRepository.findByContractorIdOrClientId(userId)
                .stream()
                .filter(contract -> contract.getStatus() == ContractStatus.IN_PROGRESS)
                .toList();

        List<Integer> activeContractIds = activeContracts.stream()
                .map(Contract::getId)
                .toList();

        return jobRepository.findByContractIdInAndIsPaidFalse(activeContractIds);
    }

    @Override
    @Transactional
    public Job payForJob(Integer jobId, Integer clientId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalArgumentException("Job not found"));
        Profile client = profileRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Profile contractor = job.getContract().getContractor();

        // Ensure the job is unpaid and belongs to the client
        if (!job.getIsPaid() && job.getContract().getClient().getId().equals(clientId)) {
            if (client.getBalance() >= job.getPrice()) {
                // Transfer funds from client to contractor
                client.setBalance(client.getBalance() - job.getPrice());
                contractor.setBalance(contractor.getBalance() + job.getPrice());

                job.setIsPaid(true);
                job.setPaidDate(LocalDate.now());

                // Save the updated entities
                profileRepository.save(client);
                profileRepository.save(contractor);
                return jobRepository.save(job);
            } else {
                throw new IllegalStateException("Insufficient balance to pay for the job");
            }
        } else {
            throw new IllegalStateException("Job is either already paid or does not belong to this client");
        }
    }

    @Override
    public Job updateJob(Job job) {
        return null;
    }

    @Override
    public void deleteJob(Integer id) {

    }
}
