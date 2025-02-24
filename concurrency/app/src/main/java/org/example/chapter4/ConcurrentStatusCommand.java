package org.example.chapter4;

import org.example.chapter2.CustomLogger;

import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentStatusCommand extends ConcurrentCommand {
    private final ThreadPoolExecutor executor;
    protected ConcurrentStatusCommand(Socket socket, String[] command, ThreadPoolExecutor executor) {
        super(socket, command);
        this.executor = executor;
    }

    @Override
    public String execute() {
        StringBuilder builder = new StringBuilder();
        builder.append("Active count: ").append(executor.getActiveCount()).append(" ").append("Task Count: ").append(executor.getTaskCount())
                .append(" ").append("Queue siz: ").append(executor.getQueue().size());
        CustomLogger.sendMessage(builder.toString());
        return builder.toString();
    }
}
