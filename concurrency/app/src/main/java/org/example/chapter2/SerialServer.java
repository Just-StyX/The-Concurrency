package org.example.chapter2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SerialServer {
    public static void startServer() {
        System.out.println("Server listening on port 8189");
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            try (Socket incoming = serverSocket.accept()) {
                InputStream inputStream = incoming.getInputStream();
                OutputStream outputStream = incoming.getOutputStream();
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
                    printWriter.println("Connection received. Enter BYE to exit.");
                    boolean done = false;
                    while (!done && scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        Commander commander = Commander.getInstance(line);
                        printWriter.println("Echo: " + commander.command());
                        done = commander.isStopServer();
                        if (line.trim().equalsIgnoreCase("BYE")) {
                            done = true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
