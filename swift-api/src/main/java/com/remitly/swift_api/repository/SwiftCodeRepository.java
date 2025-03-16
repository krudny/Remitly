package com.remitly.swift_api.repository;

import com.remitly.swift_api.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
    List<SwiftCode> findByCountryCode(String countryCode);
    List<SwiftCode> findBySwiftCodeStartingWith(String swiftCodePrefix);
    SwiftCode findBySwiftCode(String swiftCode);
}
