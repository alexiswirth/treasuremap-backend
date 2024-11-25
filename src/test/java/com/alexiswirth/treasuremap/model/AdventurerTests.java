package com.alexiswirth.treasuremap.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTests {

    @Test
    void testInitialization() {
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AGDA");

        assertEquals("Alice", adventurer.getName());
        assertEquals(1, adventurer.getPositionX());
        assertEquals(1, adventurer.getPositionY());
        assertEquals("N", adventurer.getOrientation());
        assertTrue(adventurer.isStillSearching());
        assertEquals('A', adventurer.nextMove());
    }

    @Test
    void testTurnLeft() {
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AGDA");

        adventurer.turnLeft();
        assertEquals("W", adventurer.getOrientation());

        adventurer.turnLeft();
        assertEquals("S", adventurer.getOrientation());

        adventurer.turnLeft();
        assertEquals("E", adventurer.getOrientation());

        adventurer.turnLeft();
        assertEquals("N", adventurer.getOrientation());
    }

    @Test
    void testTurnRight() {
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AGDA");

        adventurer.turnRight();
        assertEquals("E", adventurer.getOrientation());

        adventurer.turnRight();
        assertEquals("S", adventurer.getOrientation());

        adventurer.turnRight();
        assertEquals("W", adventurer.getOrientation());

        adventurer.turnRight();
        assertEquals("N", adventurer.getOrientation());
    }

    @Test
    void testMoveForward() {
        Map map = new Map(3, 3);
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AGDA");

        adventurer.moveForward(map, List.of(adventurer));
        assertEquals(1, adventurer.getPositionX());
        assertEquals(0, adventurer.getPositionY());
    }

    @Test
    void testMoveBlockedByAnotherAdventurer() {
        Map map = new Map(3, 3);
        Adventurer adventurer1 = new Adventurer("Alice", 1, 1, "N", "A");
        Adventurer adventurer2 = new Adventurer("Bob", 1, 0, "N", "A");

        adventurer1.moveForward(map, List.of(adventurer1, adventurer2));
        assertEquals(1, adventurer1.getPositionX());
        assertEquals(1, adventurer1.getPositionY(), "Adventurer should not move because the position is occupied");
    }

    @Test
    void testCollectTreasure() {
        Map map = new Map(3, 3);
        map.addTreasure(1, 1, 3);

        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "A");
        adventurer.collectTreasure(map);

        assertEquals(1, adventurer.getTreasures(), "Adventurer should collect one treasure");
        assertEquals(2, ((Treasure) map.getCase(1, 1)).getNumber(), "Treasure count should decrease by one");
    }

    @Test
    void testExecuteMoveForward() {
        Map map = new Map(3, 3);
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "A");

        adventurer.execute(map, List.of(adventurer));
        assertEquals(1, adventurer.getPositionX());
        assertEquals(0, adventurer.getPositionY());
        assertFalse(adventurer.isStillSearching());
    }

    @Test
    void testExecuteTurnLeft() {
        Map map = new Map(3, 3);
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "G");

        adventurer.execute(map, List.of(adventurer));
        assertEquals("W", adventurer.getOrientation());
        assertFalse(adventurer.isStillSearching());
    }

    @Test
    void testExecuteTurnRight() {
        Map map = new Map(3, 3);
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "D");

        adventurer.execute(map, List.of(adventurer));
        assertEquals("E", adventurer.getOrientation());
        assertFalse(adventurer.isStillSearching());
    }

    @Test
    void testSequenceExecution() {
        Map map = new Map(3, 3);
        map.addTreasure(1, 0, 2);

        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AAG");

        adventurer.execute(map, List.of(adventurer)); // Move forward
        adventurer.execute(map, List.of(adventurer)); // Move forward again and collect treasure
        adventurer.execute(map, List.of(adventurer)); // Turn left

        assertEquals(1, adventurer.getPositionX());
        assertEquals(0, adventurer.getPositionY());
        assertEquals("W", adventurer.getOrientation());
        assertEquals(1, adventurer.getTreasures(), "Adventurer should have collected one treasure");
    }

    @Test
    void testToString() {
        Adventurer adventurer = new Adventurer("Alice", 1, 1, "N", "AAG");

        String expected = "A - Alice - 1 - 1 - N - 0";
        assertEquals(expected, adventurer.toString(), "toString output is incorrect");
    }
}
