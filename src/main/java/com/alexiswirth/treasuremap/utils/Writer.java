package com.alexiswirth.treasuremap.utils;

import com.alexiswirth.treasuremap.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component

public class Writer {
    public StringBuilder writeResult(Map map, List<Adventurer> adventurers) {
        StringBuilder mapToString = new StringBuilder();

        mapToString.append("C - ").append(map.getWidth()).append(" - ").append(map.getHeight()).append("\n");
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                CaseType caseType = map.getCase(j, i);
                if (caseType instanceof Mountain) {
                    mapToString.append("M - ").append(j).append(" - ").append(i).append("\n");
                } else if (caseType instanceof Treasure) {
                    Treasure treasure = (Treasure) caseType;
                    if (treasure.getNumber() == 0) continue;
                    mapToString.append("T - ").append(j).append(" - ").append(i).append(" - ")
                            .append(treasure.getNumber()).append("\n");
                }
            }
        }

        for (Adventurer adventurer : adventurers) {
            mapToString.append(adventurer);
            mapToString.append('\n');
        }
        return mapToString;
    }
}

