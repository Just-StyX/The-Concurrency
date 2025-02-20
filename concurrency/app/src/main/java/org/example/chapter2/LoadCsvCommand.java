package org.example.chapter2;

import java.nio.charset.Charset;

public class LoadCsvCommand extends Command<String> {
    private final String filepath;
    private final Charset charset;
    private final String delimiter;

    private LoadCsvCommand(String filepath, Charset charset, String delimiter) {
        this.filepath = filepath;
        this.charset = charset;
        this.delimiter = delimiter;
    }

    public static LoadCsvCommand getInstance(String filepath, Charset charset, String delimiter) {
        return new LoadCsvCommand(filepath, charset, delimiter);
    }
    @Override
    public String execute() {
        return PipDao.getInstance().loadCsvData(filepath, charset, delimiter);
    }
}
