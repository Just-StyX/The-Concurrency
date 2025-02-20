package org.example.chapter2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PipDao {
    public record PipData(
            String countryCode, String year, String reportingLevel, String welfareType, Float percentile, Float averageWelfare,
            Float populationShare, Float welfareShare, Float quantile, Float population
    ) {}

    public List<PipData> dataList = new ArrayList<>();
    private PipDao() {}
    public static PipDao getInstance() { return new PipDao(); }
    public String loadCsvData(String filepath, Charset charset, String delimiter) {
        Path filePath = Paths.get(filepath);
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (i > 0) {
                    String[] values = line.split(Pattern.quote(delimiter));
                    for (int k = 0; k < values.length; k++) {
                        if (Objects.equals(values[k], "NA") || Objects.equals(values[k], "na")) {
                            values[k] = "0";

                        }
                    }
                    dataList.add(
                            new PipData(
                                    values[0],
                                    values[1],
                                    values[2],
                                    values[3],
                                    Float.parseFloat(values[4]),
                                    Float.parseFloat(values[5]),
                                    Float.parseFloat(values[6]),
                                    Float.parseFloat(values[7]),
                                    Float.parseFloat(values[8]),
                                    Float.parseFloat(values[9])
                            )
                    );
                }
                i++;
            }
            DataCenter.dataList = dataList;
            return "Data uploaded successfully";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public SummaryResult queryReport(String countryCode, String indicator, String year) {

        if (indicator.split(" ").length == 2) indicator = String.join("_", indicator.split(" "));

        Stream<PipData> list = DataCenter.dataList.stream()
                .filter(pipData -> (pipData.countryCode.equals(countryCode) && pipData.year.equals(year)));
        SummaryResult summaryResult;
        DoubleSummaryStatistics doubleSummaryStatistics;
        Indicators indicators = Indicators.valueOf(indicator.toUpperCase());
        switch (indicators) {
            case PERCENTILE -> {
                doubleSummaryStatistics = list.map(PipData::percentile)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            case AVERAGE_WELFARE -> {
                doubleSummaryStatistics = list.map(PipData::averageWelfare)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            case POPULATION_SHARE -> {
                doubleSummaryStatistics = list.map(PipData::populationShare)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            case WELFARE_SHARE -> {
                doubleSummaryStatistics = list.map(PipData::welfareShare)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            case QUANTILE -> {
                doubleSummaryStatistics = list.map(PipData::quantile)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            case POPULATION -> {
                doubleSummaryStatistics = list.map(PipData::population)
                        .collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
                summaryResult = getSummaryResult(countryCode, year, indicator, doubleSummaryStatistics);
            }
            default -> {
                throw new IllegalArgumentException(ErrorCommand.getInstance().execute()); }
        }
        return summaryResult;
    }
    private SummaryResult getSummaryResult(String countryCode, String year, String indicator, DoubleSummaryStatistics doubleSummaryStatistics) {
        return new SummaryResult(
                countryCode, year, indicator,
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getSum(),
                doubleSummaryStatistics.getAverage()
        );
    }
}
