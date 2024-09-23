package com.std22073.patrimoine.controller;

import com.std22073.patrimoine.model.Patrimoine;
import com.std22073.patrimoine.service.PatrimoineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> createOrUpdate(@PathVariable String id, @RequestBody Patrimoine patrimoine) {
        try {
            Patrimoine savedPatrimoine = patrimoineService.saveOrUpdate(id, patrimoine);
            return ResponseEntity.ok(savedPatrimoine);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getById(@PathVariable String id) {
        try {
            Patrimoine patrimoine = patrimoineService.getById(id);
            if (patrimoine != null) {
                return ResponseEntity.ok(patrimoine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
