package org.example.chapter2;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Commander {
    private final String inputCommand;
    private boolean stopServer = false;

    private Commander(String inputCommand) {
        this.inputCommand = inputCommand;
    }
    public static Commander getInstance(String inputCommand) { return new Commander(inputCommand); }

    public String command() {
        String[] commands = inputCommand.split(Pattern.quote("#"));
        switch (commands[0].toLowerCase()) {
            case "l" -> {
                return LoadCsvCommand.getInstance(commands[1], StandardCharsets.UTF_8, ",").execute();
            }
            case "q" -> {
                return ReportCommand.getInstance(commands[1], ";").execute().toString();
            }
            case "r" -> {
                stopServer = true;
                return StopCommand.getInstance().execute();
            }
            default -> {
                return ErrorCommand.getInstance().execute();
            }
        }
    }

    public boolean isStopServer() {
        return stopServer;
    }
}
