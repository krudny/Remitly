package com.remitly.swift_api.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CountryDetailsResponseDTO {
    private String countryISO;
    private String countryName;
    private List<SwiftCodeResponseDTO> swiftCodes;
}
