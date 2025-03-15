package com.remitly.swift_api.service;

import com.remitly.swift_api.DTO.NewSwiftCodeDTO;
import com.remitly.swift_api.model.SwiftCode;
import com.remitly.swift_api.repository.SwiftCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
@AllArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    private boolean swiftCodeExist(String swiftCode) {
        return swiftCodeRepository.findById(swiftCode).isPresent();
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
