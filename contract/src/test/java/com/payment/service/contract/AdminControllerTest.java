package com.payment.service.contract;

import com.payment.service.contract.auth.AuthenticationInterceptor;
import com.payment.service.contract.controllers.AdminController;
import com.payment.service.contract.models.Profile;
import com.payment.service.contract.models.ProfilesRole;
import com.payment.service.contract.services.admin.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private Profile mockProfile;

    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate startDate = LocalDate.parse("2023-01-01", formatter);

    @BeforeEach
    public void setup() throws Exception {
        mockProfile = new Profile();
        mockProfile.setId(1);
        mockProfile.setFirstName("John");
        mockProfile.setLastName("Doe");
        mockProfile.setRole(ProfilesRole.client);
        mockProfile.setBalance(100000.00);
        mockProfile.setCreatedAt(startDate.atStartOfDay());

        Profile mockContractorProfile = new Profile();
        mockContractorProfile.setId(2);
        mockContractorProfile.setFirstName("James");
        mockContractorProfile.setLastName("Doe");
        mockContractorProfile.setRole(ProfilesRole.contractor);
        mockContractorProfile.setProfession("Engineer");
        mockContractorProfile.setCreatedAt(startDate.atStartOfDay());

        given(authenticationInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    @Test
    public void shouldReturnBestProfession() throws Exception {
        String bestProfession = "Engineer";
        given(adminService.getBestProfession(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(bestProfession);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/admin/best-profession")
                .param("start", "2023-01-01")
                .param("end", "2023-12-31"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Engineer"));
    }

    @Test
    public void shouldReturnBestClients() throws Exception {
        Profile client1 = new Profile();
        client1.setId(1);
        client1.setFirstName("Client 1");
        Profile client2 = new Profile();
        client2.setId(2);
        client2.setFirstName("Client 2");
        Page<Profile> mockPage = new PageImpl<>(List.of(client1, client2), PageRequest.of(0, 2), 2);

        given(adminService.getBestClients(any(LocalDate.class), any(LocalDate.class), any(Integer.class)))
                .willReturn(mockPage);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/admin/best-clients")
                .param("start", "2023-01-01")
                .param("end", "2023-12-31")
                .param("limit", "2"));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    public void shouldReturnBadRequestWhenGettingBestProfessionFails() throws Exception {
        given(adminService.getBestProfession(any(LocalDate.class), any(LocalDate.class)))
                .willThrow(new IllegalArgumentException("Invalid date range"));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/admin/best-profession")
                .param("start", "2024-11-10")
                .param("end", "2023-12-31"));

        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid date range"));
    }
}
