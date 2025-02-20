package org.example.chapter1.second_example;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGroupFileTask implements Runnable {
    private final String filename;
    private final ConcurrentLinkedQueue<File> directories;
    private final Result parallelResult;
    private boolean found;

    public ParallelGroupFileTask(String filename, ConcurrentLinkedQueue<File> directories, Result parallelResult) {
        this.filename = filename;
        this.directories = directories;
        this.parallelResult = parallelResult;
        this.found = false;
    }

    @Override
    public void run() {
        while (!directories.isEmpty()) {
            File file = directories.poll();
            try {
                processDirectory(file, filename, parallelResult);
                if (found) {
                    System.out.printf("%s has found the file%n", Thread.currentThread().getName());
                    System.out.printf("Parallel Search: Path: %s%n", parallelResult.path());
                }
            } catch (InterruptedException e) {
                System.out.printf("%s has been interrupted%n", Thread.currentThread().getName());
            }
        }
    }

    private void processDirectory(File file, String filename, Result parallelResult) throws InterruptedException {
        File[] contents = file.listFiles();
        if (contents == null) return;
        for (File content: contents) {
            if (content.isDirectory()) {
                processDirectory(content, filename, parallelResult);
            } else {
                processFile(content, filename, parallelResult);
            }
            if (Thread.currentThread().isInterrupted()) throw new InterruptedException();
            if (found) return;
        }
    }

    private void processFile(File file, String filename, Result parallelResult) {
        if (file.getName().equals(filename)) {
            parallelResult = new Result(true, file.getAbsolutePath());
            this.found = parallelResult.found();
        }
    }

    public boolean getFound() { return this.found; }
}
