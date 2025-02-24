package org.example.chapter4;

import java.util.concurrent.FutureTask;

public class ServerTask<V> extends FutureTask<V> implements Comparable<ServerTask<V>> {
    private ConcurrentCommand command;
    private ServerTask(ConcurrentCommand command) {
        super(command, null);
        this.command = command;
    }
    public static <V> ServerTask<V> getInstance(ConcurrentCommand command) {
        return new ServerTask<>(command);
    }

    public ConcurrentCommand getCommand() {
        return command;
    }

    public void setCommand(ConcurrentCommand command) {
        this.command = command;
    }

    @Override
    public int compareTo(ServerTask<V> other) {
        return command.compareTo(other.getCommand());
    }
}
