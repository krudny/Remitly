package com.remitly.swift_api.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.remitly.swift_api.DTO.request.NewSwiftCodeDTO;
import com.remitly.swift_api.DTO.response.CountryDetailsResponseDTO;
import com.remitly.swift_api.DTO.response.DetailsResponseDTO;
import com.remitly.swift_api.DTO.response.MessageResponseDTO;
import com.remitly.swift_api.model.SwiftCode;
import com.remitly.swift_api.repository.SwiftCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

class SwiftCodeServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    private SwiftCode swiftCode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        swiftCode = SwiftCode.builder()
                .swiftCode("EXAMPLE123")
                .countryCode("US")
                .name("Test Bank")
                .country("US")
                .address("123 Example St")
                .town("Example City")
                .country("Test Country")
                .build();
    }

    @Test
    void testGetDetails_shouldReturnDetails_whenSwiftCodeExists() {
        when(swiftCodeRepository.existsById("EXAMPLE123")).thenReturn(true);
        when(swiftCodeRepository.findBySwiftCode("EXAMPLE123")).thenReturn(swiftCode);

        DetailsResponseDTO result = swiftCodeService.getDetails("EXAMPLE123");

        assertEquals("Test Bank", result.getBankName());
        assertEquals("US", result.getCountryISO2());
        assertEquals("Test Country", result.getCountryName());
        assertFalse(result.getIsHeadquarter());
    }

    @Test
    void testGetDetails_shouldThrowException_whenSwiftCodeDoesNotExist() {
        when(swiftCodeRepository.findById("NONEXISTENT123")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> swiftCodeService.getDetails("NONEXISTENT123"));
    }

    @Test
    void testAddSwiftCode_shouldAddNewSwiftCode_whenValidDataProvided() {
        NewSwiftCodeDTO newSwiftCodeDTO = new NewSwiftCodeDTO("123 Example St", "Test Bank", "US", "Test Country", true, "EXAMPLEXXX");

        when(swiftCodeRepository.existsById("EXAMPLEXXX")).thenReturn(false);

        MessageResponseDTO result = swiftCodeService.addSwiftCode(newSwiftCodeDTO);

        assertEquals("New swift code added successfully", result.getMessage());
    }

    @Test
    void testAddSwiftCode_shouldThrowException_whenSwiftCodeAlreadyExists() {
        NewSwiftCodeDTO newSwiftCodeDTO = new NewSwiftCodeDTO("123 Example St", "Test Bank", "US", "Test Country", true, "EXAMPLEXXX");

        when(swiftCodeRepository.existsById("EXAMPLEXXX")).thenReturn(true);


        assertThrows(IllegalArgumentException.class, () -> swiftCodeService.addSwiftCode(newSwiftCodeDTO));
    }

    @Test
    void testAddSwiftCode_shouldThrowException_whenHeadquarterSwiftCodeDoesNotEndWithXXX() {
        NewSwiftCodeDTO newSwiftCodeDTO = new NewSwiftCodeDTO("123 Example St", "Test Bank", "US", "Test Country", true, "EXAMPLE124");

        when(swiftCodeRepository.existsById("EXAMPLE124")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> swiftCodeService.addSwiftCode(newSwiftCodeDTO));
    }

    @Test
    void testDeleteSwiftCode_shouldDeleteSwiftCode_whenSwiftCodeExists() {
        when(swiftCodeRepository.existsById("EXAMPLE123")).thenReturn(true);

        MessageResponseDTO result = swiftCodeService.deleteSwiftCode("EXAMPLE123");

        assertEquals("Swift code deleted successfully", result.getMessage());
    }

    @Test
    void testDeleteSwiftCode_shouldThrowException_whenSwiftCodeDoesNotExist() {
        when(swiftCodeRepository.existsById("NONEXISTENT123")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> swiftCodeService.deleteSwiftCode("NONEXISTENT123"));
    }

    @Test
    void testGetCountryDetails_shouldReturnCountryDetails_whenCountryCodeExists() {
        List<SwiftCode> swiftCodes = List.of(SwiftCode.builder()
                .swiftCode("EXAMPLE123")
                .countryCode("US")
                .name("Test Bank")
                .address("123 Example St")
                .town("Example City")
                .country("Test Country")
                .build());
        when(swiftCodeRepository.findByCountryCode("US")).thenReturn(swiftCodes);

        CountryDetailsResponseDTO result = swiftCodeService.getCountryDetails("US");

        assertEquals("US", result.getCountryISO());
        assertEquals("Test Country", result.getCountryName());
        assertEquals(1, result.getSwiftCodes().size());
    }

    @Test
    void testGetCountryDetails_shouldThrowException_whenCountryCodeDoesNotExist() {
        when(swiftCodeRepository.findByCountryCode("NONEXISTENT")).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> swiftCodeService.getCountryDetails("NONEXISTENT"));
    }
}
