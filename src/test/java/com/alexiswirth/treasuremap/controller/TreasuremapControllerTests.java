package com.alexiswirth.treasuremap.controller;

import com.alexiswirth.treasuremap.service.SimulationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TreasuremapControllerTests {

    // Injecte le contrôleur dans le test
    @Autowired
    private TreasuremapController treasuremapController;

    // Utilisation de @MockBean pour mocker SimulationService et l'injecter dans le contrôleur
    @MockBean
    private SimulationService simulationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Configure MockMvc pour appeler le contrôleur
        mockMvc = MockMvcBuilders.standaloneSetup(treasuremapController).build();
    }

    @Test
    public void testSimulate() throws Exception {
        StringBuilder result = new StringBuilder("Simulation Result");

        when(simulationService.result()).thenReturn(result);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString("test");

        mockMvc.perform(post("/api/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(content().string(result.toString()));
    }
}
