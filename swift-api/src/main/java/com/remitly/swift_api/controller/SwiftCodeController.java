package com.remitly.swift_api.controller;

import com.remitly.swift_api.DTO.MessageResponseDTO;
import com.remitly.swift_api.DTO.NewSwiftCodeDTO;
import com.remitly.swift_api.service.SwiftCodeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

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
