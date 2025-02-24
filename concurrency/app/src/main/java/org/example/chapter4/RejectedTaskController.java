package org.example.chapter4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedTaskController implements RejectedExecutionHandler {

    public static RejectedTaskController getInstance() {
        return new RejectedTaskController();
    }

    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
        ConcurrentCommand command = (ConcurrentCommand) task;

        try (Socket clientSocket = command.getSocket();
             PrintWriter printWriterOut = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String message = String.format(
                    """
                            The server is shutting down ...
                            Your request cannot be served. Shutting Down: %s
                            Terminated: %s
                            Terminating: %s
                            """, executor.isShutdown(), executor.isTerminated(), executor.isTerminating()
            );
            System.out.println(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
