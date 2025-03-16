package com.remitly.swift_api.DTO.response;
import java.util.List;

public record CountryDetailsResponseDTO(
        String countryISO,
        String countryName,
        List<SwiftCodeResponseDTO> swiftCodes
) {}
