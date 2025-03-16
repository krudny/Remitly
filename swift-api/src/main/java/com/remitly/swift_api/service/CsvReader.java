package com.remitly.swift_api.service;

import com.remitly.swift_api.model.SwiftCode;
import com.remitly.swift_api.repository.SwiftCodeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class CsvReader {
    private final SwiftCodeRepository swiftCodeRepository;

    public void parseAndSaveCSV(Resource resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withFirstRecordAsHeader()
                    .parse(reader);

            List<SwiftCode> swiftCodes = StreamSupport.stream(records.spliterator(), false)
                    .filter(record -> !swiftCodeRepository.existsById(record.get("SWIFT CODE")))
                    .map(record -> SwiftCode.builder()
                            .swiftCode(record.get("SWIFT CODE").trim())
                            .countryCode(record.get("COUNTRY ISO2 CODE").trim())
                            .codeType(record.get("CODE TYPE").trim())
                            .name(record.get("NAME").trim())
                            .address(record.get("ADDRESS").trim())
                            .town(record.get("TOWN NAME").trim())
                            .country(record.get("COUNTRY NAME").trim())
                            .timezone(record.get("TIME ZONE").trim())
                            .build())
                    .collect(Collectors.toList());

            swiftCodeRepository.saveAll(swiftCodes);
        } catch (Exception e) {
            throw new RuntimeException("Błąd podczas parsowania CSV: " + e.getMessage(), e);
        }
    }
}