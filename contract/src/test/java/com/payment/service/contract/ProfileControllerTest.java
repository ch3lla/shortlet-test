package com.payment.service.contract;

import com.payment.service.contract.auth.AuthenticationInterceptor;
import com.payment.service.contract.controllers.ProfileController;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.services.profile.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    @Test
    public void shouldDepositToBalanceWhenAuthenticated() throws Exception {
        Profile mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setFirstName("John");
        mockProfile.setLastName("Doe");

        given(authenticationInterceptor.preHandle(any(), any(), any())).willAnswer(invocation -> {
            HttpServletRequest request = invocation.getArgument(0);
            request.setAttribute("profile", mockProfile);
            return true;
        });

        Profile updatedProfile = new Profile();
        updatedProfile.setId(1);
        updatedProfile.setFirstName("John");
        updatedProfile.setLastName("Doe");
        updatedProfile.setBalance(125.0);
        given(profileService.depositToBalance(mockProfile, 1, 25.0)).willReturn(updatedProfile);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/profile/balances/deposit/1")
                .param("amount", "25.0")
                .header("profileId", 1));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(125.0));
    }

    @Test
    public void shouldReturnBadRequestForInvalidDeposit() throws Exception {
        Profile mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setFirstName("John");
        mockProfile.setLastName("Doe");

        given(authenticationInterceptor.preHandle(any(), any(), any())).willAnswer(invocation -> {
            HttpServletRequest request = invocation.getArgument(0);
            request.setAttribute("profile", mockProfile);
            return true;
        });

        given(profileService.depositToBalance(mockProfile, 1, 1000.0)).willThrow(new IllegalStateException("Deposit exceeds 25% of total outstanding payments"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/profile/balances/deposit/1")
                .param("amount", "1000.0")
                .header("profileId", 1));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Deposit exceeds 25% of total outstanding payments"));
    }
}
