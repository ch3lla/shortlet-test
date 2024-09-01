package com.payment.service.contract.dto;

import jakarta.persistence.Column;

public class ProfileDto {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String profession;

    @Column(nullable = false)
    private Double balance;
}
