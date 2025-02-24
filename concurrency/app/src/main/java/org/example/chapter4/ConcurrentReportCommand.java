package org.example.chapter4;

import java.net.Socket;

public class ConcurrentReportCommand extends ConcurrentCommand {
    protected ConcurrentReportCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        return WorldDataDao.report(command[3]);
    }
}
