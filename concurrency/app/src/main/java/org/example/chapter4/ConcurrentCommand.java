package org.example.chapter4;

import org.example.chapter2.CustomLogger;
import org.example.chapter2.ParallelCache;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ConcurrentCommand extends CustomCommand implements Runnable, Comparable<ConcurrentCommand> {
    private final String username;
    private final byte priority;
    private final Socket socket;

    protected ConcurrentCommand(Socket socket, String[] command) {
        super(command);
        this.username = command[1];
        this.priority = Byte.parseByte(command[2]);
        this.socket = socket;
    }

    public Socket getSocket() throws IOException {
        return this.socket;
    }
    public String getUsername() { return username; }
    public Byte getPriority() { return priority; }

    @Override
    public abstract String execute();

    @Override
    public void run() {
        String message = String.format("Running a Task: Username: %s; Priority: %s", username, priority);
        CustomLogger.sendMessage(message);
        String ret = execute();
        ParallelCache cache = ServerConcurrent.getCache();
        if (isCacheable()) {
            cache.put(String.join(";", command), ret);
        }
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            System.out.println(ret);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ret);
    }
    @Override
    public int compareTo(ConcurrentCommand other) {
        return Byte.compare(other.getPriority(), this.getPriority());
    }
}
