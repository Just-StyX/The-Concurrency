package org.example.chapter2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConcurrentServer {
    private static boolean stopped = false;
    private static ThreadPoolExecutor executor;
    private static ParallelCache parallelCache;

    public static void startServer() {
        parallelCache = ParallelCache.getInstance();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CustomLogger.initializeLog();

        System.out.println("Initialization completed.");
        System.out.println("Server listening on port 8189 ...");

        try (ServerSocket serverSocket = new ServerSocket(8189)){
            int i = 1;
            while (!stopped) {
                Socket incoming = serverSocket.accept();
                System.out.println("Spawning " + i);
                executor.execute(RequestTask.getInstance(incoming));
                i++;
            }
            boolean timeWaiting = executor.awaitTermination(1, TimeUnit.DAYS);
            if (timeWaiting) {
                System.out.println("Shutting down cache ...");
                parallelCache.shutdown();
                System.out.println("Cache OK");
                System.out.println("Main server thread ended.");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdown() {
        stopped = true;
        System.out.println("Shutting down the server ...");
        System.out.println("Shutting down executor");
        executor.shutdown();
        System.out.println("Executor OK");
        System.out.println("Closing the socket ...");
        System.out.println("Socket OK");
        System.out.println("Shutting down logger ...");
        CustomLogger.sendMessage("Shutting down the logger");
        CustomLogger.shutdown();
        System.out.println("Logger OK");
    }

    public static ParallelCache getParallelCache() {
        return parallelCache;
    }
    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }
}
