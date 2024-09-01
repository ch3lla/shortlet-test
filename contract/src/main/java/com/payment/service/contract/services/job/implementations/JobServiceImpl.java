package com.payment.service.contract.services.job.implementations;

import com.payment.service.contract.dto.JobDto;
import com.payment.service.contract.models.Job;
import com.payment.service.contract.services.job.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
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
        return List.of();
    }

    @Override
    public Job payForJob(Integer jobId, Integer clientId) {
        return null;
    }

    @Override
    public Job updateJob(Job job) {
        return null;
    }

    @Override
    public void deleteJob(Integer id) {

    }
}
