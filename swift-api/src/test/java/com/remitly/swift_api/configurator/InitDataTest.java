package com.remitly.swift_api.configurator;

import com.remitly.swift_api.repository.SwiftCodeRepository;
import com.remitly.swift_api.service.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InitDataTest {

    @Mock
    private CsvReader csvReader;

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private InitData initData;

    @BeforeEach
    void setUp() {
        initData = new InitData(csvReader, swiftCodeRepository);
    }

    @Test
    void shouldCallParseAndSaveCSVWhenRepositoryIsEmpty() {
        when(swiftCodeRepository.count()).thenReturn(0L);

        initData.init();

        verify(csvReader, times(1)).parseAndSaveCSV(any(Resource.class));
    }

    @Test
    void shouldNotCallParseAndSaveCSVWhenRepositoryIsNotEmpty() {
        when(swiftCodeRepository.count()).thenReturn(10L);

        initData.init();

        verify(csvReader, never()).parseAndSaveCSV(any(Resource.class));
    }
}
