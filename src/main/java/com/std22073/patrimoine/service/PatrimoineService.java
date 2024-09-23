package com.std22073.patrimoine.service;

import com.std22073.patrimoine.model.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class PatrimoineService {

    private static final String STORAGE_DIR = "patrimoines";
    private final ObjectMapper objectMapper;

    public PatrimoineService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        createStorageDirectoryIfNotExists();
    }

    public Patrimoine saveOrUpdate(String id, Patrimoine patrimoine) throws IOException {
        patrimoine.setId(id);
        patrimoine.setLastUpdate(LocalDateTime.now());
        Path filePath = Paths.get(STORAGE_DIR, id + ".json");
        String json = objectMapper.writeValueAsString(patrimoine);
        Files.write(filePath, json.getBytes());
        return patrimoine;
    }

    public Patrimoine getById(String id) throws IOException {
        Path filePath = Paths.get(STORAGE_DIR, id + ".json");
        if (Files.exists(filePath)) {
            String json = new String(Files.readAllBytes(filePath));
            return objectMapper.readValue(json, Patrimoine.class);
        }
        return null;
    }

    private void createStorageDirectoryIfNotExists() {
        try {
            Files.createDirectories(Paths.get(STORAGE_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create storage directory", e);
        }
    }
}