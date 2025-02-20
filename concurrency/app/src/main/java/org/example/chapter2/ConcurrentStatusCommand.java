package org.example.chapter2;

import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentStatusCommand extends Command<String> {
    public static ConcurrentStatusCommand getInstance() { return new ConcurrentStatusCommand(); }

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
