package org.example.chapter2;

import java.util.regex.Pattern;

public class ReportCommand extends Command<SummaryResult> {
    private final String query;
    private final String delimiter;
    private static final String USAGE = "<Country Code>;<Indicator>;<Year>";

    private ReportCommand(String query, String delimiter) {
        this.query = query;
        this.delimiter = delimiter;
    }
    public static ReportCommand getInstance(String query, String delimiter) { return new ReportCommand(query, delimiter); }

    @Override
    public SummaryResult execute() {
        String[] parameters = query.split(Pattern.quote(delimiter));
        if (parameters.length < 3) throw new IllegalArgumentException(USAGE);
        return PipDao.getInstance().queryReport(parameters[0].toUpperCase().trim(), parameters[1].trim(), parameters[2].trim());
    }
}
