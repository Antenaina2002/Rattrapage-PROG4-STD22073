package com.std22073.patrimoine;

import com.std22073.patrimoine.controller.PatrimoineController;
import com.std22073.patrimoine.model.Patrimoine;
import com.std22073.patrimoine.service.PatrimoineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatrimoineController.class)
public class PatrimoineControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatrimoineService patrimoineService;

    @Test
    void testGetPatrimoineExistant() throws Exception {
        Patrimoine testPatrimoine = new Patrimoine("1", "John Doe", null);

        when(patrimoineService.getById("1")).thenReturn(testPatrimoine);
        mockMvc.perform(get("/patrimoines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.possessor").value("John Doe"));
    }

    @Test
    void testGetPatrimoineInexistant() throws Exception {
        when(patrimoineService.getById("999")).thenReturn(null);
        mockMvc.perform(get("/patrimoines/999"))
                .andExpect(status().isNotFound());
    }
}
