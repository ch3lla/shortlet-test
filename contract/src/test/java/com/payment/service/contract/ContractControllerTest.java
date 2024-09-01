package com.payment.service.contract;

import com.payment.service.contract.auth.AuthenticationInterceptor;
import com.payment.service.contract.controllers.ContractController;
import com.payment.service.contract.models.*;
import com.payment.service.contract.services.contract.ContractService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContractController.class)
@Import(AuthenticationInterceptor.class)
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractService contractService;

    @Mock
    private Profile mockProfile;

    @Mock
    private Contract mockContract;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    @BeforeEach
    public void setup() throws Exception {
        // mock client
        mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setFirstName("John");
        mockProfile.setLastName("Doe");
        mockProfile.setRole(ProfilesRole.client);

        // mock contractor
        Profile mockContractorProfile = new Profile();
        mockContractorProfile.setId(2);
        mockContractorProfile.setFirstName("James");
        mockContractorProfile.setLastName("Doe");
        mockContractorProfile.setRole(ProfilesRole.contractor);

        // mock contract
        mockContract.setId(1);
        mockContract.setClient(mockProfile);
        mockContract.setContractor(mockContractorProfile);
        mockContract.setStatus(ContractStatus.in_progress);


        given(contractService.getContractById(1)).willReturn(Optional.of(mockContract));
        given(contractService.getActiveContractsByUserId(1)).willReturn(List.of(mockContract));

        given(authenticationInterceptor.preHandle(any(), any(), any())).willAnswer(invocation -> {
            HttpServletRequest request = invocation.getArgument(0);
            request.setAttribute("profile", mockProfile);
            return true;
        });
    }

    @Test
    public void shouldReturnContractWhenFound() throws Exception {
        given(contractService.getContractById(1)).willReturn(Optional.of(mockContract));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/contracts/1")
                .header("profileId", 1));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void shouldReturnForbiddenWhenProfileNotAuthorized() throws Exception {
        Profile mockProfile2 = new Profile();
        mockProfile2.setId(2);
        mockProfile2.setFirstName("James");
        mockProfile2.setLastName("Doe");

        Profile mockContractorProfile2 = new Profile();
        mockContractorProfile2.setId(3);
        mockContractorProfile2.setFirstName("Jina");
        mockContractorProfile2.setLastName("Doe");


        Contract mockContract = new Contract();
        mockContract.setId(3);
        mockContract.setClient(mockProfile);
        mockContract.setContractor(mockContractorProfile2);
        given(contractService.getContractById(3)).willReturn(Optional.of(mockContract));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/contracts/3")
                .header("profileId", 2));

        resultActions.andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnActiveContractsWhenRequested() throws Exception {
        Contract mockContract = new Contract();
        mockContract.setId(1);
        given(contractService.getActiveContractsByUserId(1)).willReturn(List.of(mockContract));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/contracts")
                .header("profileId", 1));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
