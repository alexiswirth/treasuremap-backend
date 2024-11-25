package com.alexiswirth.treasuremap.controller;

import com.alexiswirth.treasuremap.model.SimulationRequest;
import com.alexiswirth.treasuremap.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class TreasuremapController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping(value = "/simulate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringBuilder> simulate(@RequestBody SimulationRequest simulationRequest) {
        simulationService.execute(simulationRequest);
        return ResponseEntity.ok(simulationService.result());
    }
}
