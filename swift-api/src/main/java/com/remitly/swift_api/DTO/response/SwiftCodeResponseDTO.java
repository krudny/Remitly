package com.remitly.swift_api.DTO.response;

public record SwiftCodeResponseDTO(
        String address,
        String bankName,
        String countryISO2,
        Boolean isHeadquarter,
        String swiftCode
) {}
