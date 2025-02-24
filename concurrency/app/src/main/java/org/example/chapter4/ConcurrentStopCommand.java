package org.example.chapter4;

import java.net.Socket;

public class ConcurrentStopCommand extends ConcurrentCommand {
    protected ConcurrentStopCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        return "Server shutting down ...";
    }
}
