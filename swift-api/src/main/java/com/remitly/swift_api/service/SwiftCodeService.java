package com.remitly.swift_api.service;

import com.remitly.swift_api.repository.SwiftCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    public String deleteSwiftCode(String swiftCode) {
        try {
            if (swiftCodeRepository.findById(swiftCode).isPresent()) {
                swiftCodeRepository.deleteById(swiftCode);
                return "Swift code deleted successfully";
            } else {
                return "No swift code found with the provided ID";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while deleting the Swift code: " + e.getMessage();
        }
    }
}
