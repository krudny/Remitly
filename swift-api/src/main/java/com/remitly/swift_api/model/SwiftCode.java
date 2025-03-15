package com.remitly.swift_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "swift_codes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SwiftCode {
    @Id
    private String swiftCode;
    private String countryCode;
    private String codeType;
    private String name;
    private String address;
    private String town;
    private String country;
    private String timezone;
}
