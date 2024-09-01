package com.payment.service.contract.services.admin;

import com.payment.service.contract.models.Profile;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    /**
     * Get the profession that earned the most money between the given dates.
     * @param start The start date.
     * @param end The end date.
     * @return The profession that earned the most.
     */
    String getBestProfession(LocalDate start, LocalDate end);

    /**
     * Get the clients who paid the most for jobs within the specified period.
     * @param start The start date.
     * @param end The end date.
     * @param limit The maximum number of clients to return.
     * @return A list of the best clients.
     */
    List<Profile> getBestClients(LocalDate start, LocalDate end, int limit);
}
