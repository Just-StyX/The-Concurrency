package org.example.chapter4;

import java.net.Socket;

public class ConcurrentQueryCommand extends ConcurrentCommand {
    protected ConcurrentQueryCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        WorldDataDao dataDao = WorldDataDao.getDao();
        if (command.length == 5) return WorldDataDao.query(command[3], command[4]);
        else if (command.length == 6) {
            try {
                return WorldDataDao.query(command[3], command[4], Short.parseShort(command[5]));
            } catch (NumberFormatException numberFormatException) {
                return "Error; Bad Command";
            }
        } else {
            return "Error; Bad Command";
        }
    }
}
