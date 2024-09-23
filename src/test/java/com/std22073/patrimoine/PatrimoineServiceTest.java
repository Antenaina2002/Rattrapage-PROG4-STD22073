package com.std22073.patrimoine;

import com.std22073.patrimoine.model.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.std22073.patrimoine.service.PatrimoineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatrimoineServiceTest {

    @InjectMocks
    private PatrimoineService patrimoineService;

    @Mock
    private ObjectMapper objectMapper;

    private static final String STORAGE_DIR = "patrimoines";
    private String testId = "1";
    private Patrimoine testPatrimoine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPatrimoine = new Patrimoine(testId, "John Doe", LocalDateTime.now());
        createStorageDirectory();
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        when(objectMapper.writeValueAsString(testPatrimoine)).thenReturn("{\"id\":\"1\",\"possessor\":\"John Doe\"}");
        Patrimoine savedPatrimoine = patrimoineService.saveOrUpdate(testId, testPatrimoine);
        assertNotNull(savedPatrimoine);
        assertEquals(testId, savedPatrimoine.getId());
        assertEquals("John Doe", savedPatrimoine.getPossessor());
        Path filePath = Paths.get(STORAGE_DIR, testId + ".json");
        assertTrue(Files.exists(filePath));
        Files.deleteIfExists(filePath);
    }

    @Test
    void testGetByIdExists() throws Exception {
        when(objectMapper.writeValueAsString(testPatrimoine)).thenReturn("{\"id\":\"1\",\"possessor\":\"John Doe\"}");
        patrimoineService.saveOrUpdate(testId, testPatrimoine);

        when(objectMapper.readValue(anyString(), eq(Patrimoine.class))).thenReturn(testPatrimoine);

        Patrimoine retrievedPatrimoine = patrimoineService.getById(testId);

        assertNotNull(retrievedPatrimoine);
        assertEquals(testId, retrievedPatrimoine.getId());
        assertEquals("John Doe", retrievedPatrimoine.getPossessor());

        // Clean up
        Files.deleteIfExists(Paths.get(STORAGE_DIR, testId + ".json"));
    }

    @Test
    void testGetByIdNotFound() throws IOException {
        Patrimoine retrievedPatrimoine = patrimoineService.getById("999");

        assertNull(retrievedPatrimoine);
    }

    private void createStorageDirectory() {
        try {
            Files.createDirectories(Paths.get(STORAGE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create storage directory", e);
        }
    }
}
