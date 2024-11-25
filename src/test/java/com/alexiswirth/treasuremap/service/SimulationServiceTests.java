package com.alexiswirth.treasuremap.service;

import com.alexiswirth.treasuremap.model.SimulationRequest;
import com.alexiswirth.treasuremap.model.Adventurer;
import com.alexiswirth.treasuremap.utils.Writer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationServiceTests {

    @InjectMocks
    private SimulationService simulationService;

    @Mock
    private Writer writer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResetSimulation() {
        String content = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA";

        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        simulationService.execute(new SimulationRequest(json));
        simulationService.resetSimulation();

        assertEquals(0, simulationService.getAdventurers().size());
    }

    @Test
    void testExecuteWithFiveAdventurers() {
        String content = "C - 10 - 10\n" +
                "A - Test - 0 - 0 - S - D\n" +
                "A - Test2 - 1 - 1 - S - D\n" +
                "A - Test3 - 2 - 2 - S - D\n" +
                "A - Test4 - 3 - 3 - S - D\n" +
                "A - Test5 - 4 - 5 - S - D";

        simulationService.execute(new SimulationRequest(content));

        assertTrue(simulationService.getAdventurers().stream()
                .noneMatch(Adventurer::isStillSearching), "All adventurers must finished their research");

        assertEquals(5, simulationService.getAdventurers().size());
    }

    @Test
    void testExecute() {

        String content = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA";

        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        simulationService.execute(new SimulationRequest(json));

        simulationService.result();

        assertTrue(simulationService.getAdventurers().stream()
                .noneMatch(Adventurer::isStillSearching), "All adventurers must finished their research");

        verify(writer, times(1)).writeResult(any(), any());
    }

    @Test
    void testResult() {
        StringBuilder mockResult = new StringBuilder("Result of the simulation");
        when(writer.writeResult(any(), any())).thenReturn(mockResult);

        StringBuilder result = simulationService.result();
        assertEquals(mockResult.toString(), result.toString(), "The result didn't match with the expected result");

        verify(writer, times(1)).writeResult(any(), any());
    }

    @Test
    void testSetupHunt() {
        String content = "C - 5 - 5\n" +
                "A - Test - 0 - 0 - S - D\n" +
                "M - 1 - 1\n" +
                "T - 2 - 2 - 1\n" +
                "Z - 0 - 1\n";

        simulationService.execute(new SimulationRequest(content));

        assertTrue(simulationService.getAdventurers().stream()
                .noneMatch(Adventurer::isStillSearching), "All adventurers must finished their research");
    }
}