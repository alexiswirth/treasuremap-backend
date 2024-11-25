package com.alexiswirth.treasuremap.model;

public record Treasure(int number) implements CaseType {
    public Treasure {
        if (number < 0) number = 0;
    }

    public Treasure retireTreasure() {
        if (number > 0) {
            return new Treasure(number - 1);
        }
        return new Treasure(number);
    }

    public Treasure addTreasure() {
        return new Treasure(number + 1);
    }

    public int getNumber() {
        return number;
    }
}
