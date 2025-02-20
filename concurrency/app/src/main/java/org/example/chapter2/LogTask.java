package org.example.chapter2;

import java.util.concurrent.TimeUnit;

public class LogTask implements Runnable {
    @Override
    public void run() {
        try {
            while ((Thread.currentThread().isInterrupted())) {
                TimeUnit.SECONDS.sleep(10);
                CustomLogger.writeLogs();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CustomLogger.writeLogs();
    }
}
