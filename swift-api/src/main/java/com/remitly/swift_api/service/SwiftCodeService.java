package com.remitly.swift_api.service;

import com.remitly.swift_api.DTO.request.NewSwiftCodeDTO;
import com.remitly.swift_api.DTO.response.CountryDetailsResponseDTO;
import com.remitly.swift_api.DTO.response.DetailsResponseDTO;
import com.remitly.swift_api.DTO.response.MessageResponseDTO;
import com.remitly.swift_api.DTO.response.SwiftCodeResponseDTO;
import com.remitly.swift_api.model.SwiftCode;
import com.remitly.swift_api.repository.SwiftCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    private boolean swiftCodeExist(String swiftCode) {
        return swiftCodeRepository.existsById(swiftCode);
    }

    private List<SwiftCodeResponseDTO> mapToResponse(List<SwiftCode> swiftCodes) {
        return swiftCodes.stream()
                .map(swiftCode -> new SwiftCodeResponseDTO(
                        swiftCode.getAddress(),
                        swiftCode.getName(),
                        swiftCode.getCountryCode(),
                        swiftCode.getSwiftCode().endsWith("XXX") ? Boolean.TRUE : Boolean.FALSE,
                        swiftCode.getSwiftCode()))
                .toList();
    }

    public DetailsResponseDTO getDetails(String swiftCode) {
        if (!swiftCodeExist(swiftCode)) {
            throw new IllegalArgumentException("Such swift code does not exist.");
        }

        SwiftCode swiftCodeEntity = swiftCodeRepository.findBySwiftCode(swiftCode);
        DetailsResponseDTO response = DetailsResponseDTO.builder()
                .address(swiftCodeEntity.getAddress())
                .bankName(swiftCodeEntity.getName())
                .countryISO2(swiftCodeEntity.getCountryCode())
                .countryName(swiftCodeEntity.getCountry())
                .isHeadquarter(swiftCodeEntity.getSwiftCode().endsWith("XXX") ? Boolean.TRUE : Boolean.FALSE)
                .swiftCode(swiftCodeEntity.getSwiftCode())
                .build();

        if(!swiftCodeEntity.getSwiftCode().endsWith("XXX")) {
            return response;
        }

        String swiftCodePrefix = swiftCode.substring(0, 8);
        List<SwiftCode> branches = swiftCodeRepository.findBySwiftCodeStartingWith(swiftCodePrefix).stream()
                .filter(branch -> !branch.getSwiftCode().equals(swiftCode)).toList();

        response.setBranches(mapToResponse(branches));
        return response;
    }

    public CountryDetailsResponseDTO getCountryDetails(String countryCode) {
        List<SwiftCode> swiftCodes = swiftCodeRepository.findByCountryCode(countryCode);
        if (swiftCodes.isEmpty()) {
            throw new IllegalArgumentException("Such country code does not exist.");
        }

        return new CountryDetailsResponseDTO(countryCode, swiftCodes.getFirst().getCountry(), mapToResponse(swiftCodes));
    }

    public MessageResponseDTO addSwiftCode(NewSwiftCodeDTO request) {
        SwiftCode newSwiftCode = SwiftCode.builder()
                .address(request.getAddress())
                .name(request.getBankName())
                .countryCode(request.getCountryISO2())
                .country(request.getCountryName())
                .swiftCode(request.getSwiftCode())
                .build();

        String swiftCode = newSwiftCode.getSwiftCode();

        if (swiftCodeExist(swiftCode)) {
            throw new IllegalArgumentException("Such swiftcode already exists");
        }

        if (request.getIsHeadquarter() && !swiftCode.endsWith("XXX")) {
            throw new IllegalArgumentException("Headquarters swift code should end with XXX");
        }

        swiftCodeRepository.save(newSwiftCode);
        return new MessageResponseDTO("New swift code added successfully");
    }

    public MessageResponseDTO deleteSwiftCode(String swiftCode) {
        if (swiftCodeExist(swiftCode)) {
            swiftCodeRepository.deleteById(swiftCode);
            return new MessageResponseDTO("Swift code deleted successfully");
        } else {
            throw new IllegalArgumentException("No swift code found with the provided ID");
        }
    }
}
