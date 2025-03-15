package com.remitly.swift_api.repository;

import com.remitly.swift_api.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {
}
