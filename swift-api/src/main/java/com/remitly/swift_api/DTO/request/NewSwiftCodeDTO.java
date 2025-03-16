package com.remitly.swift_api.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record NewSwiftCodeDTO(
        @NotNull(message = "Address cannot be null")
        @Size(min = 1, message = "Address cannot be empty")
        String address,

        @NotNull(message = "Bank name cannot be null")
        @Size(min = 1, message = "Bank name cannot be empty")
        String bankName,

        @NotNull(message = "Country ISO2 cannot be null")
        @Size(min = 2, max = 2, message = "Country ISO2 must be 2 characters")
        String countryISO2,

        @NotNull(message = "Country name cannot be null")
        @Size(min = 1, message = "Country name cannot be empty")
        String countryName,

        @NotNull(message = "Headquarter status cannot be null")
        Boolean isHeadquarter,

        @NotNull(message = "Swift code cannot be null")
        @Size(min = 8, max = 11, message = "Swift code must be between 8 and 11 characters")
        String swiftCode
) {}
