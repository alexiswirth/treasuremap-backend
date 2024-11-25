package com.alexiswirth.treasuremap.model;

import com.alexiswirth.treasuremap.model.SimulationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationRequestTests {

    @Test
    void testConstructorWithContent() {
        String content = "C - 3 - 4\n" +
                "A - Lara - 1 - 1 - N - AAAG";
        SimulationRequest request = new SimulationRequest(content);

        assertEquals(content, request.getContent(), "Content should be correctly initialized via constructor");
    }

    @Test
    void testGetContent() {
        String content = "C - 10 - 10\nA - Test - 0 - 0 - N - A";
        SimulationRequest request = new SimulationRequest(content);

        assertEquals(content, request.getContent(), "getContent should return the correct content");
    }

    @Test
    void testSetContent() {
        String content = "C - 10 - 10\nA - Test - 0 - 0 - N - A";
        SimulationRequest request = new SimulationRequest();
        request.setContent(content);

        assertEquals(content, request.getContent(), "getContent should return the correct content");
    }
}
