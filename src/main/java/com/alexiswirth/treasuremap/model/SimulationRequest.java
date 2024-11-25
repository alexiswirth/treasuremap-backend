package com.alexiswirth.treasuremap.model;

public class SimulationRequest {
    private String content;

    public SimulationRequest() {}

    public SimulationRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
