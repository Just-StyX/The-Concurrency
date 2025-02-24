package org.example.chapter2;

import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentStatus extends Command<String> {
    public static ConcurrentStatus getInstance() { return new ConcurrentStatus(); }

    @Override
    public String execute() {
        StringBuilder builder = new StringBuilder();
        ThreadPoolExecutor executor = ConcurrentServer.getExecutor();
        builder.append("Active count: ").append(executor.getActiveCount()).append(" ").append("Task Count: ").append(executor.getTaskCount())
                .append(" ").append("Queue siz: ").append(executor.getQueue().size());
        CustomLogger.sendMessage(builder.toString());
        return builder.toString();
    }
}
