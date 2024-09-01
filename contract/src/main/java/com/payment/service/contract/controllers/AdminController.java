package com.payment.service.contract.controllers;

import com.payment.service.contract.models.Profile;
import com.payment.service.contract.services.admin.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Endpoint to get the profession that earned the most money in the specified date range.
     * @param start The start date.
     * @param end The end date.
     * @return The best profession.
     */
    @GetMapping("/best-profession")
    public ResponseEntity<String> getBestProfession(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end) {
        try {
            String bestProfession = adminService.getBestProfession(start, end);
            return ResponseEntity.ok(bestProfession);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * Endpoint to get the two best clients who paid the most for jobs in the specified date range.
     * @param start The start date.
     * @param end The end date.
     * @return The two best clients.
     */
    @GetMapping("/best-clients")
    public ResponseEntity<Page<Profile>> getBestClients(
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {
        Page<Profile> bestClients = adminService.getBestClients(start, end, limit);
        return ResponseEntity.ok(bestClients);
    }
}
