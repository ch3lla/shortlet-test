package com.payment.service.contract.controllers;

import com.payment.service.contract.models.Job;
import com.payment.service.contract.services.job.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * Endpoint to retrieve all unpaid jobs for a user under active contracts.
     * @param profileId The ID of the profile making the request (from request headers).
     * @return A list of unpaid jobs.
     */
    @GetMapping("/unpaid")
    public ResponseEntity<List<Job>> getUnpaidJobs(@RequestHeader("profileId") Integer profileId) {
        List<Job> unpaidJobs = jobService.getUnpaidJobsByUserId(profileId);
        return ResponseEntity.ok(unpaidJobs);
    }

    /**
     * Endpoint to pay for a job. The payment is made by the client.
     * @param jobId The ID of the job to be paid for.
     * @param profileId The ID of the profile making the request (from request headers).
     * @return The updated job.
     */
    @PostMapping("/{job_id}/pay")
    public ResponseEntity<Job> payForJob(@PathVariable("job_id") Integer jobId, @RequestHeader("profileId") Integer profileId) {
        Job paidJob = jobService.payForJob(jobId, profileId);
        return ResponseEntity.ok(paidJob);
    }
}
