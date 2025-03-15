package com.remitly.swift_api.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewSwiftCodeDTO {
    @NotNull(message = "Address cannot be null")
    @Size(min = 1, message = "Address cannot be empty")
    private String address;

    @NotNull(message = "Bank name cannot be null")
    @Size(min = 1, message = "Bank name cannot be empty")
    private String bankName;

    @NotNull(message = "Country ISO2 cannot be null")
    @Size(min = 2, max = 2, message = "Country ISO2 must be 2 characters")
    private String countryISO2;

    @NotNull(message = "Country name cannot be null")
    @Size(min = 1, message = "Country name cannot be empty")
    private String countryName;

    @NotNull(message = "Headquarter status cannot be null")
    private Boolean isHeadquarter;

    @NotNull(message = "Swift code cannot be null")
    @Size(min = 8, max = 11, message = "Swift code must be between 8 and 11 characters")
    private String swiftCode;
}
