package com.alexiswirth.treasuremap.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Map {
    private final List<List<CaseType>> cases;
    private final int width;
    private final int height;

    public Map() {
        this.cases = new ArrayList<List<CaseType>>();
        this.width = 0;
        this.height = 0;
    }
    public Map(int width, int height) {
        this.width = width;
        this.height = height;


        this.cases = IntStream.range(0, width)
            .mapToObj(x -> IntStream.range(0, height)
                    .mapToObj(y -> (CaseType) new Plain())
                    .collect(Collectors.toCollection(ArrayList::new)))
            .collect(Collectors.toCollection(ArrayList::new));

    }

    public CaseType getCase(int x, int y) {
        return cases.get(x).get(y);
    }

    public void setCase(int x, int y, CaseType type) {
        verifyCoordinates(x, y);
        cases.get(x).set(y, type);
    }
    public boolean isAccessible(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) return false;

        CaseType type = getCase(x, y);
        if (type instanceof Plain) {
            return true;
        } else if (type instanceof Mountain) {
            return false;
        } else if (type instanceof Treasure) {
            return true;
        } else {
            throw new IllegalStateException("Unknown type : " + type);
        }
    }

    public void addMountain(int x, int y) {
        setCase(x, y, new Mountain());
    }

    public void addTreasure(int x, int y, int nbTreasures) {
        CaseType caseType = getCase(x, y);
        if (caseType instanceof Plain || caseType instanceof Treasure) {
            setCase(x, y, new Treasure(nbTreasures));
        } else {
            throw new IllegalArgumentException("Cannot add a treasure on a mountain");
        }
    }

    public void collectTreasure(int x, int y) {
        CaseType caseType = getCase(x, y);
        if (caseType instanceof Treasure treasure) {
            setCase(x, y, treasure.retireTreasure());
        }
    }

    private boolean isValidCoordinates(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    private void verifyCoordinates(int x, int y) {
        if (!isValidCoordinates(x, y)) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds : " + x + ", " + y);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        String mapToString = "";

        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                mapToString += this.getCase(j, i);
            }
            mapToString += '\n';
        }

        return mapToString;
    }

}
