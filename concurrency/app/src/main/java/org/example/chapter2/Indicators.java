package org.example.chapter2;

public enum Indicators {
    PERCENTILE("PERCENTILE"),
    AVERAGE_WELFARE("AVERAGE_WELFARE"),
    POPULATION_SHARE("POPULATION_SHARE"),
    WELFARE_SHARE("WELFARE_SHARE"),
    QUANTILE("QUANTILE"), POPULATION("POPULATION");

    public final String name;

    Indicators(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
