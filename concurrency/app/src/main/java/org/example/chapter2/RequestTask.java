package org.example.chapter2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RequestTask implements Runnable {
    private final Socket incoming;

    private RequestTask(Socket incoming) {
        this.incoming = incoming;
    }
    public static RequestTask getInstance(Socket socket) { return new RequestTask(socket); }

    @Override
    public void run() {
        try (InputStream inputStream = incoming.getInputStream();
             OutputStream outputStream = incoming.getOutputStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);) {

            printWriter.println("Connection received. Enter BYE to exit.");
            boolean done = false;
            while (!done && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                CustomLogger.sendMessage(line);
                CustomLogger.writeLogs();
                ParallelCache cache = ConcurrentServer.getParallelCache();
                String response = cache.get(line);
                if (response == null) {
                    Commander commander = Commander.getInstance(line);
                    String theResponse = commander.command();
                    cache.put(line, theResponse);
                    printWriter.println("Echo: " + theResponse);
                    printWriter.println("Echo: " + ConcurrentStatus.getInstance().execute());
                    done = commander.isStopServer();
                } else {
                    CustomLogger.sendMessage("Command " + line + " was found in the cache");
                }
                if (line.trim().equalsIgnoreCase("BYE")) {
                    ConcurrentServer.shutdown();
                    done = true;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
