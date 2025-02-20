package org.example.chapter2;

public class ErrorCommand extends Command<String> {
    public static ErrorCommand getInstance() { return new ErrorCommand(); }
    @Override
    public String execute() {
        return "Unknown indicator";
    }
}
