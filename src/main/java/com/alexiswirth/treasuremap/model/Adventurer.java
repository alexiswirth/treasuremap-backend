package com.alexiswirth.treasuremap.model;

import java.util.ArrayList;
import java.util.List;

public class Adventurer {
    private final String name;
    private int x, y;
    private String orientation;
    private final List<Character> sequences = new ArrayList<Character>();
    private int treasures;
    public Adventurer(String name, int x, int y, String orientation, String sequence) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        for (char seq : sequence.toCharArray()) {
            sequences.add(seq);
        }
        this.treasures = 0;
    }
    public void moveForward(Map map, List<Adventurer> adventurers) {
        int newPosX = x, newPosY = y;
        switch (orientation) {
            case "N" -> newPosY--;
            case "S" -> newPosY++;
            case "E" -> newPosX++;
            case "W" -> newPosX--;
        }
        int finalNewPosX = newPosX;
        int finalNewPosY = newPosY;
        if (map.isAccessible(newPosX, newPosY) && adventurers.stream()
                .noneMatch(adventurer -> adventurer.getPositionX() == finalNewPosX && adventurer.getPositionY() == finalNewPosY)) {
            x = newPosX;
            y = newPosY;
            collectTreasure(map);
        }
    }
    public void turnLeft() {
        orientation = switch (orientation) {
            case "N" -> "W";
            case "W" -> "S";
            case "S" -> "E";
            case "E" -> "N";
            default -> orientation;
        };
    }
    public void turnRight() {
        orientation = switch (orientation) {
            case "N" -> "E";
            case "E" -> "S";
            case "S" -> "W";
            case "W" -> "N";
            default -> orientation;
        };
    }
    public void collectTreasure(Map map) {
        if (map.getCase(x, y) instanceof Treasure) {
            treasures++;
            map.collectTreasure(x, y);
        }
    }
    public void execute(Map map, List<Adventurer> adventurers) {
        char movement = this.sequences.get(0);
        switch (movement) {
            case 'A' -> moveForward(map, adventurers);
            case 'G' -> turnLeft();
            case 'D' -> turnRight();
        }
        this.sequences.remove(0);
    }
    public char nextMove() {
        return this.sequences.get(0);
    }
    public boolean isStillSearching() {
        return !this.sequences.isEmpty();
    }

    public int getPositionX() {
        return this.x;
    }
    public String getName() { return this.name; }
    public int getPositionY() {
        return this.y;
    }
    public String getOrientation() { return this.orientation; }
    public int getTreasures() { return this.treasures; }

    @Override
    public String toString() {
        return String.format("A - %s - %d - %d - %s - %d", name, x, y, orientation, treasures);
    }
}
