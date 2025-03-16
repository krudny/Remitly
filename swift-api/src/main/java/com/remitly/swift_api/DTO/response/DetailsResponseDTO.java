package com.remitly.swift_api.DTO.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DetailsResponseDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private Boolean isHeadquarter;
    private String swiftCode;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<SwiftCodeResponseDTO> branches;
}
