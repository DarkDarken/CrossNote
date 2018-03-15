package com.example.programmer.app_beta;


public enum  TrainingCategory {
    AMRAP("AMRAP"), EMOM("EMOM"), RFT("RFT"), BENCHMARK("Benchmark");

    private String name;

    private TrainingCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
