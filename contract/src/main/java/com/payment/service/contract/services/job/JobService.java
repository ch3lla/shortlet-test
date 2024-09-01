package com.payment.service.contract.services.job;

import com.payment.service.contract.dto.JobDto;
import com.payment.service.contract.models.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {

    /**
     * Create a new job.
     * @param job The job to be created.
     * @return The created job.
     */
    Job createJob(JobDto job);

    /**
     * Retrieve a job by its ID.
     * @param id The ID of the job.
     * @return An Optional containing the job if found, or empty if not.
     */
    Optional<Job> getJobById(Integer id);

    /**
     * Retrieve a list of unpaid jobs for a specific user (client or contractor) and active contracts.
     * @param userId The ID of the user.
     * @return A list of unpaid jobs.
     */
    List<Job> getUnpaidJobsByUserId(Integer userId);

    /**
     * Pay for a job by its ID.
     * @param jobId The ID of the job to be paid for.
     * @param clientId The ID of the client making the payment.
     * @return The updated job.
     */
    Job payForJob(Integer jobId, Integer clientId);

    /**
     * Update an existing job.
     * @param job The job with updated details.
     * @return The updated job.
     */
    Job updateJob(Job job);

    /**
     * Delete a job by its ID.
     * @param id The ID of the job to be deleted.
     */
    void deleteJob(Integer id);
}

