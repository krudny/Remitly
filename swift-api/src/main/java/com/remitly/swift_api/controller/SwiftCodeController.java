package com.remitly.swift_api.controller;

import com.remitly.swift_api.DTO.response.CountryDetailsResponseDTO;
import com.remitly.swift_api.DTO.response.DetailsResponseDTO;
import com.remitly.swift_api.DTO.response.MessageResponseDTO;
import com.remitly.swift_api.DTO.request.NewSwiftCodeDTO;
import com.remitly.swift_api.service.SwiftCodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

    @GetMapping("/{swift-code}")
    public ResponseEntity<DetailsResponseDTO> getDetails(@PathVariable("swift-code") String swiftCode) {
        return new ResponseEntity<>(swiftCodeService.getDetails(swiftCode), HttpStatus.OK);
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<CountryDetailsResponseDTO> getCountryDetails(@PathVariable("countryISO2code") String countryCode) {
        return new ResponseEntity<>(swiftCodeService.getCountryDetails(countryCode), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MessageResponseDTO> addSwiftCode(@Valid @RequestBody NewSwiftCodeDTO request) {
        String response = swiftCodeService.addSwiftCode(request);
        return new ResponseEntity<>(new MessageResponseDTO(response), HttpStatus.CREATED);
    }

    @DeleteMapping("/{swift-code}")
    public ResponseEntity<MessageResponseDTO> deleteSwiftCode(@PathVariable("swift-code") String swiftCode) {
        String response = swiftCodeService.deleteSwiftCode(swiftCode);
        return new ResponseEntity<>(new MessageResponseDTO(response), HttpStatus.OK);
    }
}
