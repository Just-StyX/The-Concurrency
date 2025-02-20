package org.example.chapter2;

public record SummaryResult(
        String countryCode, String year, String indicator, long count, double min, double max, double sum, double average
) {
}
