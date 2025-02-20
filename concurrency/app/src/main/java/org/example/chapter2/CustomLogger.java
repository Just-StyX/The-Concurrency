package org.example.chapter2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomLogger {
    private static final ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();
    private static final Thread thread;
    private static final String LOG_FILE = Paths.get("/home/lu/Documents/Workspace/concurrency/app/src/main/resources/server.log").toString();

    static {
        LogTask task = new LogTask();
        thread = new Thread(task);
    }

    public static void sendMessage(String message) {
        logQueue.offer(LocalDateTime.now() + ": " + message);
    }
    public static void writeLogs() {
        String message;
        Path path = Paths.get(LOG_FILE);
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            while ((message = logQueue.poll()) != null) {
                fileWriter.write(LocalDateTime.now() + ": " + message);
                fileWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initializeLog() {
        Path path = Paths.get(LOG_FILE);
        if (Files.exists(path)) {
            try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING)){

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        thread.start();
    }
    public static void shutdown() { thread.interrupt(); }
}
