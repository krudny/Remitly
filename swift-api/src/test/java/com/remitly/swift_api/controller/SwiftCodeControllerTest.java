package com.remitly.swift_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remitly.swift_api.DTO.request.NewSwiftCodeDTO;
import com.remitly.swift_api.DTO.response.CountryDetailsResponseDTO;
import com.remitly.swift_api.DTO.response.DetailsResponseDTO;
import com.remitly.swift_api.DTO.response.MessageResponseDTO;
import com.remitly.swift_api.DTO.response.SwiftCodeResponseDTO;
import com.remitly.swift_api.service.SwiftCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SwiftCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftCodeService swiftCodeService;

    @InjectMocks
    private SwiftCodeController swiftCodeController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(swiftCodeController).build();
    }

    @Test
    void shouldReturnDetailsForSwiftCode() throws Exception {
        String swiftCode = "ABCDUSXXX";
        DetailsResponseDTO responseDTO = DetailsResponseDTO.builder()
                .address("XX")
                .bankName("Bank of America")
                .countryISO2("US")
                .countryName("USA")
                .isHeadquarter(Boolean.TRUE)
                .swiftCode(swiftCode)
                .build();

        when(swiftCodeService.getDetails(swiftCode)).thenReturn(responseDTO);

        mockMvc.perform(get("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("ABCDUSXXX"))
                .andExpect(jsonPath("$.bankName").value("Bank of America"))
                .andExpect(jsonPath("$.countryName").value("USA"))
                .andExpect(jsonPath("$.isHeadquarter").value(true));
    }

    @Test
    void shouldReturnCountryDetails() throws Exception {
        String countryCode = "US";
        SwiftCodeResponseDTO swiftCode1 = new SwiftCodeResponseDTO(
                "123 Wall Street", "Bank of America", "US", true, "BOFAUS3N"
        );
        SwiftCodeResponseDTO swiftCode2 = new SwiftCodeResponseDTO(
                "456 Broadway", "Chase Bank", "US", false, "CHASUS33"
        );

        CountryDetailsResponseDTO responseDTO = new CountryDetailsResponseDTO(
                "US", "United States", List.of(swiftCode1, swiftCode2)
        );

        when(swiftCodeService.getCountryDetails(countryCode)).thenReturn(responseDTO);

        mockMvc.perform(get("/v1/swift-codes/country/{countryISO2code}", countryCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO").value("US"))
                .andExpect(jsonPath("$.countryName").value("United States"))
                .andExpect(jsonPath("$.swiftCodes[0].bankName").value("Bank of America"))
                .andExpect(jsonPath("$.swiftCodes[1].bankName").value("Chase Bank"));
    }

    @Test
    void shouldAddNewSwiftCode() throws Exception {
        NewSwiftCodeDTO request = new NewSwiftCodeDTO(
                "789 Main St", "Wells Fargo", "US", "United States", false, "WFBIUS6S"
        );

        MessageResponseDTO responseDTO = new MessageResponseDTO("Swift code added successfully");

        when(swiftCodeService.addSwiftCode(any(NewSwiftCodeDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Swift code added successfully"));
    }

    @Test
    void shouldDeleteSwiftCode() throws Exception {
        String swiftCode = "BOFAUS3N";
        MessageResponseDTO responseDTO = new MessageResponseDTO("Swift code deleted");

        when(swiftCodeService.deleteSwiftCode(swiftCode)).thenReturn(responseDTO);

        mockMvc.perform(delete("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Swift code deleted"));
    }
}
