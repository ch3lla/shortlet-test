package com.payment.service.contract;

import com.payment.service.contract.auth.AuthenticationInterceptor;
import com.payment.service.contract.controllers.JobController;
import com.payment.service.contract.models.*;
import com.payment.service.contract.services.job.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobController.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    private Profile mockProfile;
    private Job mockJob;
    private Contract mockContract;

    @BeforeEach
    public void setup() throws Exception {
        mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setFirstName("John");
        mockProfile.setLastName("Doe");
        mockProfile.setRole(ProfilesRole.client);

        Profile mockContractorProfile = new Profile();
        mockContractorProfile.setId(2);
        mockContractorProfile.setFirstName("James");
        mockContractorProfile.setLastName("Doe");
        mockContractorProfile.setRole(ProfilesRole.contractor);

        mockContract = new Contract();
        mockContract.setId(1);
        mockContract.setClient(mockProfile);
        mockContract.setContractor(mockContractorProfile);
        mockContract.setStatus(ContractStatus.in_progress);

        List<Job> mockJobs = new ArrayList<>();
        mockJob = new Job();
        mockJob.setId(1);
        mockJob.setContract(mockContract);
        mockJob.setIsPaid(false);
        mockJob.setPrice(20000.00);
        mockJobs.add(mockJob);

        given(jobService.getUnpaidJobsByUserId(mockProfile.getId())).willReturn(mockJobs);
        given(authenticationInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    @Test
    public void shouldReturnUnpaidJobsWhenAuthenticated() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/jobs/unpaid")
                .header("profileId", 1));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(mockJob.getId()))
                .andExpect(jsonPath("$[0].price").value(mockJob.getPrice()))
                .andExpect(jsonPath("$[0].isPaid").value(mockJob.getIsPaid()));
    }

    @Test
    public void shouldReturnBadRequestWhenPayingForJobFails() throws Exception {
        given(jobService.payForJob(anyInt(), anyInt(), any(Profile.class)))
                .willThrow(new IllegalArgumentException("Payment error"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/jobs/1/pay")
                .header("profileId", 1)
                .requestAttr("profile", mockProfile));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error processing payment: Payment error"));
    }
}