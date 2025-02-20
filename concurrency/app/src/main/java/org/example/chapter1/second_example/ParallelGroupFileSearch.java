package org.example.chapter1.second_example;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGroupFileSearch {
    public static void searchFiles(File file, String filename, Result parallelResult) {
        ConcurrentLinkedQueue<File> directories = new ConcurrentLinkedQueue<>();
        File[] contents = file.listFiles();
        assert contents != null;
        for (File content: contents) {
            if (content.isDirectory()) directories.add(content);
        }
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numOfThreads];
        ParallelGroupFileTask[] tasks = new ParallelGroupFileTask[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            tasks[i] = new ParallelGroupFileTask(filename, directories, parallelResult);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }
        boolean finish = false;
        int numFinished = 0;
        while (!finish) {
            numFinished = 0;
            for (int i = 0; i < threads.length; i++) {
                if (threads[i].getState() == Thread.State.TERMINATED) {
                    numFinished++;
                    if (tasks[i].getFound()) finish = true;
                }
            }
            if (numFinished == threads.length) finish = true;
        }
        if (numFinished != threads.length) {
            for (Thread thread: threads) thread.interrupt();
        }
    }
}
