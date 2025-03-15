package com.remitly.swift_api.controller;

import com.remitly.swift_api.service.SwiftCodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;

    @DeleteMapping("/{swift-code}")
    public String deleteSwiftCode(@PathVariable("swift-code") String swiftCode) {
        return swiftCodeService.deleteSwiftCode(swiftCode);
    }
}
