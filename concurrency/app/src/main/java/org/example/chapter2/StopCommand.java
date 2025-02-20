package org.example.chapter2;

public class StopCommand extends Command<String> {
    public static StopCommand getInstance() { return new StopCommand(); }
    @Override
    public String execute() {
        return "Server shutting down ...";
    }
}
