package com.alexiswirth.treasuremap.service;

import com.alexiswirth.treasuremap.model.SimulationRequest;
import com.alexiswirth.treasuremap.model.Adventurer;
import com.alexiswirth.treasuremap.model.Map;
import com.alexiswirth.treasuremap.utils.Writer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulationService {
    private Map map;
    List<Adventurer> adventurers = new ArrayList<Adventurer>();

    @Autowired
    private Writer writer;

    public SimulationService() {
        this.map = new Map();
    }

    private void setupHunt(String content) {
        String[] sequences = content.trim().split("\n");

        for (String sequence: sequences) {
            var seq = sequence.split(" - ");
            switch (sequence.charAt(0)) {
                case 'C':
                    this.map = new Map(Integer.parseInt(seq[1]), Integer.parseInt(seq[2]));
                    break;
                case 'M':
                    this.map.addMountain(Integer.parseInt(seq[1]), Integer.parseInt(seq[2]));
                    break;
                case 'T':
                    this.map.addTreasure(Integer.parseInt(seq[1]), Integer.parseInt(seq[2]), Integer.parseInt(seq[3]));
                    break;
                case 'A':
                    adventurers.add(new Adventurer(seq[1], Integer.parseInt(seq[2]), Integer.parseInt(seq[3]), seq[4], seq[5]));
                    break;
                default:
                    break;
            }
        }
    }
    public void resetSimulation() {
        this.map = new Map();
        this.adventurers.clear();
    }

    public void execute(SimulationRequest request) {
        this.resetSimulation();
        this.setupHunt(request.getContent());

        while(true) {
            if (adventurers.stream().noneMatch(Adventurer::isStillSearching)) break;
            adventurers.stream()
                    .filter(Adventurer::isStillSearching)
                    .forEach(adventurer -> {
                        adventurer.execute(this.map, adventurers);
                    });
        }
    }

    public List<Adventurer> getAdventurers() {
        return this.adventurers;
    }

    public StringBuilder result() {
        return writer.writeResult(map, adventurers);
    }
}
