package com.alexiswirth.treasuremap.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTests {

    @Test
    void testMapInitialization() {
        Map map = new Map(5, 5);

        assertEquals(5, map.getWidth(), "Width should be initialized to 5");
        assertEquals(5, map.getHeight(), "Height should be initialized to 5");

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                assertTrue(map.getCase(x, y) instanceof Plain, "All cases should be initialized to Plain");
            }
        }
    }

    @Test
    void testSetCaseAndGetCase() {
        Map map = new Map(3, 3);
        Mountain mountain = new Mountain();

        map.setCase(1, 1, mountain);
        assertEquals(mountain, map.getCase(1, 1), "The case at (1, 1) should be set to Mountain");

        Treasure treasure = new Treasure(5);
        map.setCase(2, 2, treasure);
        assertEquals(treasure, map.getCase(2, 2), "The case at (2, 2) should be set to Treasure");
    }

    @Test
    void testAddMountain() {
        Map map = new Map(4, 4);

        map.addMountain(2, 2);
        assertTrue(map.getCase(2, 2) instanceof Mountain, "The case at (2, 2) should be a Mountain");
    }

    @Test
    void testAddTreasure() {
        Map map = new Map(4, 4);

        map.addTreasure(1, 1, 3);
        CaseType caseType = map.getCase(1, 1);

        assertTrue(caseType instanceof Treasure, "The case at (1, 1) should be a Treasure");
        assertEquals(3, ((Treasure) caseType).getNumber(), "The treasure at (1, 1) should have 3 items");

        map.addTreasure(1, 1, 5); // Remplace l'ancien trésor
        assertEquals(5, ((Treasure) map.getCase(1, 1)).getNumber(), "The treasure at (1, 1) should now have 5 items");
    }

    @Test
    void testAddTreasureOnMountainThrowsException() {
        Map map = new Map(4, 4);
        map.addMountain(2, 2);

        assertThrows(IllegalArgumentException.class, () -> map.addTreasure(2, 2, 3),
                "Adding a treasure on a mountain should throw an exception");
    }

    @Test
    void testIsAccessible() {
        Map map = new Map(3, 3);

        assertTrue(map.isAccessible(0, 0), "Plain should be accessible");

        map.addMountain(1, 1);
        assertFalse(map.isAccessible(1, 1), "Mountain should not be accessible");

        map.addTreasure(2, 2, 5);
        assertTrue(map.isAccessible(2, 2), "Treasure should be accessible");
    }

    @Test
    void testIsAccessibleOutOfBounds() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(-1, 0), "Out-of-bounds coordinates should not be accessible");
        assertFalse(map.isAccessible(3, 3), "Out-of-bounds coordinates should not be accessible");
    }

    @Test
    void testCollectTreasure() {
        Map map = new Map(3, 3);

        map.addTreasure(1, 1, 3);
        map.collectTreasure(1, 1);

        CaseType caseType = map.getCase(1, 1);
        assertTrue(caseType instanceof Treasure, "The case should still be a Treasure after collection");
        assertEquals(2, ((Treasure) caseType).getNumber(), "The treasure count should decrease by 1");

        // Collect all treasures
        map.collectTreasure(1, 1);
        map.collectTreasure(1, 1);

        assertTrue(map.getCase(1, 1) instanceof Treasure, "The case should become an empty Treasure case after all treasures are collected");
        assertEquals(((Treasure) map.getCase(1, 1)).getNumber(), 0);
    }

    @Test
    void testSetCaseOutOfBoundsThrowsException() {
        Map map = new Map(3, 3);

        assertThrows(IndexOutOfBoundsException.class, () -> map.setCase(-1, 0, new Plain()),
                "Setting a case out of bounds should throw an exception");

        assertThrows(IndexOutOfBoundsException.class, () -> map.setCase(3, 3, new Plain()),
                "Setting a case out of bounds should throw an exception");
    }

    @Test
    void testToString() {
        Map map = new Map(2, 2);

        map.addMountain(0, 0);
        map.addTreasure(1, 1, 5);

        String expected = "Mountain[]Plain[]\n" +
                "Plain[]Treasure[number=5]\n";
        String actual = map.toString();

        assertEquals(expected.toString(), actual.toString(), "toString should correctly represent the map");
    }

    @Test
    void testToAddTreasureOnMountain() {
        Map map = new Map(2, 2);

        map.addMountain(0, 0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> map.addTreasure(0, 0, 3),
                "Adding a treasure on a mountain should throw an exception");

        assertEquals("Cannot add a treasure on a mountain", exception.getMessage());

    }

    @Test
    void testCollectTreasureOnNonTreasureCase() {
        Map map = new Map(3, 3);

        map.addMountain(1, 1);
        map.setCase(0, 0, new Plain());

        map.collectTreasure(1, 1);
        assertTrue(map.getCase(1, 1) instanceof Mountain, "The case (1, 1) should remain a Mountain");

        map.collectTreasure(0, 0);
        assertTrue(map.getCase(0, 0) instanceof Plain, "The case (0, 0) should remain a Plain");
    }

    @Test
    void testCollectTreasureOnTreasureCase() {
        Map map = new Map(3, 3);

        map.addTreasure(1, 1, 1);

        map.collectTreasure(1, 1);
        assertTrue(map.getCase(1, 1) instanceof Treasure, "The case (1, 1) should remain a Treasure");

        assertTrue(((Treasure) map.getCase(1, 1)).getNumber() == 0, "The case (1, 1) should have 0 treasure on it");
    }

    @Test
    void testValidIsAccessible() {
        Map map = new Map(3, 3);

        assertTrue(map.isAccessible(1, 1), "The case (1, 1) should be accessible");
    }

    @Test
    void testInvalidIsAccessibleYLessThanSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(4, -1), "The case (4, 4) should not be accessible");
    }

    @Test
    void testInvalidIsAccessibleYUpperThanSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(1, 4), "The case (4, 4) should not be accessible");
    }

    @Test
    void testInvalidIsAccessibleXUpperThanSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(4, 1), "The case (4, 4) should not be accessible");
    }

    @Test
    void testInvalidIsAccessibleXLessThanSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(-1, 1), "The case (4, 4) should not be accessible");
    }

    @Test
    void testInvalidIsAccessibleXEqualToSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(3, 1), "The case (4, 4) should not be accessible");
    }

    @Test
    void testInvalidIsAccessibleYEqualToSize() {
        Map map = new Map(3, 3);

        assertFalse(map.isAccessible(1, 3), "The case (4, 4) should not be accessible");
    }

}
