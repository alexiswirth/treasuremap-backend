package com.alexiswirth.treasuremap.utils;

import com.alexiswirth.treasuremap.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WriterTests {
    private Writer writer;
    private Map mapMock;
    private List<Adventurer> adventurersMock;

    @BeforeEach
    void setUp() {
        writer = new Writer();
        mapMock = Mockito.mock(Map.class);
        adventurersMock = Mockito.mock(List.class);
    }

    @AfterEach
    void resetMocks() {
        Mockito.reset(mapMock);
    }

    @Test
    void testWriteResult_withEmptyMapAndNoAdventurers() {
        Mockito.when(mapMock.getWidth()).thenReturn(3);
        Mockito.when(mapMock.getHeight()).thenReturn(3);
        Mockito.when(mapMock.getCase(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        StringBuilder result = writer.writeResult(mapMock, List.of());

        String expected = """
                C - 3 - 3
                """;
        assertEquals(expected, result.toString());
    }

    @Test
    void testWriteResult_withMountainsAndTreasures() {
        Map realMap = new Map(3, 3);
        realMap.setCase(0, 0, new Mountain());
        realMap.setCase(1, 1, new Treasure(2));
        realMap.setCase(2, 2, null);

        StringBuilder result = writer.writeResult(realMap, List.of());
        String expected = """
        C - 3 - 3
        M - 0 - 0
        T - 1 - 1 - 2
        """;
        assertEquals(expected, result.toString());
    }


    @Test
    void testWriteResult_withAdventurers() {
        Mockito.when(mapMock.getWidth()).thenReturn(3);
        Mockito.when(mapMock.getHeight()).thenReturn(3);
        Mockito.when(mapMock.getCase(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        Adventurer adventurer1 = Mockito.mock(Adventurer.class);
        Adventurer adventurer2 = Mockito.mock(Adventurer.class);

        Mockito.when(adventurer1.toString()).thenReturn("A - Lara - 1 - 1 - S - 2");
        Mockito.when(adventurer2.toString()).thenReturn("A - John - 2 - 2 - E - 1");

        StringBuilder result = writer.writeResult(mapMock, List.of(adventurer1, adventurer2));

        String expected = """
                C - 3 - 3
                A - Lara - 1 - 1 - S - 2
                A - John - 2 - 2 - E - 1
                """;
        assertEquals(expected, result.toString());

    }

    @Test
    void testWriteResult_withMixedData() {
        Mockito.when(mapMock.getWidth()).thenReturn(3);
        Mockito.when(mapMock.getHeight()).thenReturn(3);

        Map realMap = new Map(3, 3);
        realMap.setCase(0, 0, new Mountain());
        realMap.setCase(1, 1, new Treasure(3));


        Mockito.when(mapMock.getCase(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

        Adventurer adventurer = Mockito.mock(Adventurer.class);
        Mockito.when(adventurer.toString()).thenReturn("A - Alice - 1 - 1 - N - 5");

        StringBuilder result = writer.writeResult(realMap, List.of(adventurer));

        String expected = """
                C - 3 - 3
                M - 0 - 0
                T - 1 - 1 - 3
                A - Alice - 1 - 1 - N - 5
                """;
        assertEquals(expected, result.toString());
    }
}
