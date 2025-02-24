package org.example.chapter4;

import org.example.chapter2.CustomLogger;

import java.net.Socket;

public class ConcurrentCancelCommand extends ConcurrentCommand {
    protected ConcurrentCancelCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        ServerConcurrent.cancelTasks(getUsername());
        String message = String.format("Tasks of user %s has been cancelled.", getUsername());
        CustomLogger.sendMessage(message);
        return message;
    }
}
