package com.remitly.swift_api.service;

import com.remitly.swift_api.DTO.request.NewSwiftCodeDTO;
import com.remitly.swift_api.DTO.response.CountryDetailsResponseDTO;
import com.remitly.swift_api.DTO.response.DetailsResponseDTO;
import com.remitly.swift_api.DTO.response.SwiftCodeResponseDTO;
import com.remitly.swift_api.model.SwiftCode;
import com.remitly.swift_api.repository.SwiftCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    private boolean swiftCodeExist(String swiftCode) {
        return swiftCodeRepository.findById(swiftCode).isPresent();
    }

    public DetailsResponseDTO getDetails(String swiftCode) {
        SwiftCode swiftCodeEntity = swiftCodeRepository.findBySwiftCode(swiftCode);
        if (swiftCodeEntity == null) {
            throw new IllegalArgumentException("Such country code does not exist.");
        }


        String swiftCodePrefix = swiftCode.substring(0, 8);
        List<SwiftCode> branches = swiftCodeRepository.findBySwiftCodeStartingWith(swiftCodePrefix).stream()
                .filter(branch -> !branch.getSwiftCode().equals(swiftCode)).toList();


        List<SwiftCodeResponseDTO> swiftCodeResponseDTOs = branches.stream()
                .map(branch -> new SwiftCodeResponseDTO(
                        branch.getAddress(),
                        branch.getName(),
                        branch.getCountryCode(),
                        branch.getSwiftCode().endsWith("XXX") ? Boolean.TRUE : Boolean.FALSE,
                        branch.getSwiftCode()))
                .toList();

        return DetailsResponseDTO.builder()
                .address(swiftCodeEntity.getAddress())
                .bankName(swiftCodeEntity.getName())
                .countryISO2(swiftCodeEntity.getCountryCode())
                .countryName(swiftCodeEntity.getCountry())
                .isHeadquarter(swiftCodeEntity.getSwiftCode().endsWith("XXX") ? Boolean.TRUE : Boolean.FALSE)
                .swiftCode(swiftCodeEntity.getSwiftCode())
                .branches(swiftCodeResponseDTOs)
                .build();

    }

    public CountryDetailsResponseDTO getCountryDetails(String countryCode) {
        List<SwiftCode> swiftCodes = swiftCodeRepository.findByCountryCode(countryCode);
        if (swiftCodes.isEmpty()) {
            throw new IllegalArgumentException("Such country code does not exist.");
        }

        List<SwiftCodeResponseDTO> swiftCodeResponseDTOs = swiftCodes.stream()
                .map(swiftCode -> new SwiftCodeResponseDTO(
                        swiftCode.getAddress(),
                        swiftCode.getName(),
                        swiftCode.getCountryCode(),
                        swiftCode.getSwiftCode().endsWith("XXX") ? Boolean.TRUE : Boolean.FALSE,
                        swiftCode.getSwiftCode()))
                .collect(Collectors.toList());

        return new CountryDetailsResponseDTO(countryCode, swiftCodes.getFirst().getCountry(), swiftCodeResponseDTOs);
    }

    public String addSwiftCode(NewSwiftCodeDTO request) {
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
        return "New swift code added successfully";
    }

    public String deleteSwiftCode(String swiftCode) {
        if (swiftCodeExist(swiftCode)) {
            swiftCodeRepository.deleteById(swiftCode);
            return "Swift code deleted successfully";
        } else {
            throw new IllegalArgumentException("No swift code found with the provided ID");
        }
    }
}
