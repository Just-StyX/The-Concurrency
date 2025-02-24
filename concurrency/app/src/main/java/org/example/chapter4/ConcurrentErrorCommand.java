package org.example.chapter4;

import java.net.Socket;

public class ConcurrentErrorCommand extends ConcurrentCommand {
    protected ConcurrentErrorCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        return "";
    }
}
