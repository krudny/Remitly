package com.remitly.swift_api.configurator;

import com.remitly.swift_api.repository.SwiftCodeRepository;
import com.remitly.swift_api.service.CsvReader;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class InitData {
    private final CsvReader csvReader;
    private final SwiftCodeRepository swiftCodeRepository;

    public InitData(CsvReader csvReader, SwiftCodeRepository swiftCodeRepository) {
        this.csvReader = csvReader;
        this.swiftCodeRepository = swiftCodeRepository;
    }

    @PostConstruct
    public void init() {
        if (swiftCodeRepository.count() == 0) {
            Resource resource = new ClassPathResource("Interns_2025_SWIFT_CODES.csv");
            csvReader.parseAndSaveCSV(resource);
        }
    }
}
