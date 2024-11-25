package com.alexiswirth.treasuremap.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreasureTests {

    @Test
    void testTreasureInitializationWithPositiveNumber() {
        Treasure treasure = new Treasure(5);
        assertEquals(5, treasure.getNumber(), "Treasure should be initialized with the correct number of items");
    }

    @Test
    void testTreasureInitializationWithNegativeNumber() {
        Treasure treasure = new Treasure(-3);
        assertEquals(0, treasure.getNumber(), "Treasure should not allow a negative number and default to 0");
    }

    @Test
    void testRetireTreasureWhenNumberIsGreaterThanZero() {
        Treasure treasure = new Treasure(3);
        Treasure updatedTreasure = treasure.retireTreasure();

        assertEquals(2, updatedTreasure.getNumber(), "Treasure count should decrease by 1 when retireTreasure is called");
    }

    @Test
    void testRetireTreasureWhenNumberIsZero() {
        Treasure treasure = new Treasure(0);
        Treasure updatedTreasure = treasure.retireTreasure();

        assertEquals(0, updatedTreasure.getNumber(), "Treasure count should remain 0 when retireTreasure is called on an empty treasure");
    }

    @Test
    void testAddTreasure() {
        Treasure treasure = new Treasure(3);
        Treasure updatedTreasure = treasure.addTreasure();

        assertEquals(4, updatedTreasure.getNumber(), "Treasure count should increase by 1 when addTreasure is called");
    }

    @Test
    void testMultipleTreasureOperations() {
        Treasure treasure = new Treasure(2);

        // Add two treasures
        Treasure afterAddition = treasure.addTreasure().addTreasure();
        assertEquals(4, afterAddition.getNumber(), "Treasure count should increase correctly after multiple additions");

        // Retire two treasures
        Treasure afterRetirement = afterAddition.retireTreasure().retireTreasure();
        assertEquals(2, afterRetirement.getNumber(), "Treasure count should decrease correctly after multiple retirements");
    }

    @Test
    void testImmutability() {
        Treasure treasure = new Treasure(5);

        // Call retireTreasure and addTreasure
        Treasure retiredTreasure = treasure.retireTreasure();
        Treasure addedTreasure = treasure.addTreasure();

        // Original treasure should remain unchanged
        assertEquals(5, treasure.getNumber(), "Original treasure should remain unchanged after retireTreasure or addTreasure");
        assertEquals(4, retiredTreasure.getNumber(), "Retired treasure should reflect the updated count");
        assertEquals(6, addedTreasure.getNumber(), "Added treasure should reflect the updated count");
    }
}
