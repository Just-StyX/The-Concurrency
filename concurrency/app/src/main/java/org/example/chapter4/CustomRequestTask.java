package org.example.chapter4;

import org.example.chapter2.CustomLogger;
import org.example.chapter2.ParallelCache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CustomRequestTask implements Runnable {
    private final LinkedBlockingQueue<Socket> pendingConnections;
    private final ServerExecutor executor = new ServerExecutor();
    private final ConcurrentMap<String, ConcurrentHashMap<ConcurrentCommand, ServerTask<?>>> taskController;

    public CustomRequestTask(LinkedBlockingQueue<Socket> pendingConnections, ConcurrentMap<String, ConcurrentHashMap<ConcurrentCommand, ServerTask<?>>> taskController) {
        this.pendingConnections = pendingConnections;
        this.taskController = taskController;
    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = pendingConnections.take();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String line = in.readLine();
                CustomLogger.sendMessage(line);
                ConcurrentCommand command;
                ParallelCache cache = ServerConcurrent.getCache();
                String ret = cache.get(line);
                if (ret == null) {
                    String[] commandData = line.split(";");
                    System.out.println("Command: " + commandData[0]);
                    switch (commandData[0]) {
                        case "q" -> {
                            System.out.println("Query");
                            command = new ConcurrentQueryCommand(clientSocket, commandData);
                        }
                        case "r" -> {
                            System.out.println("Reporting");
                            command = new ConcurrentReportCommand(clientSocket, commandData);
                        }
                        case "s" -> {
                            System.out.println("Status");
                            command = new ConcurrentStatusCommand(clientSocket, commandData, executor);
                        }
                        case "z" -> {
                            System.out.println("Stop");
                            command = new ConcurrentStopCommand(clientSocket, commandData);
                        }
                        case "c" -> {
                            System.out.println("Cancel");
                            command = new ConcurrentCancelCommand(clientSocket, commandData);
                        }
                        default -> {
                            System.out.println("Error");
                            command = new ConcurrentErrorCommand(clientSocket, commandData);
                        }
                    }
                    ServerTask<?> controller = (ServerTask<?>) executor.submit(command);
                    storeController(command.getUsername(), controller, command);
                } else {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println(ret);
                    clientSocket.close();
                }
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void storeController(String username, ServerTask<?> controller, ConcurrentCommand command) {
        taskController.computeIfAbsent(username, k -> new ConcurrentHashMap<>()).put(command, controller);
    }
    public void shutdown() {
        String message = String.format("Request Task: %s pending connections.", pendingConnections.size());
        CustomLogger.sendMessage(message);
        executor.shutdown();
    }
    public void terminate() {
        try {
            boolean value = executor.awaitTermination(1, TimeUnit.DAYS);
            executor.writeStatistics();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
