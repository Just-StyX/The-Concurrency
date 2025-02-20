package org.example.chapter1.first_example;

import java.util.ArrayList;
import java.util.List;

public class ParallelMultiplier {
    public static void multiplyIndividualThreads(double[][] first_matrix, double[][] second_matrix, double[][] result) {
        List<Thread> threads = new ArrayList<>();
        int first_row_length = first_matrix.length;
        int second_row_length = second_matrix.length;
        for (int i = 0; i < first_row_length; i++) {
            for (int j = 0; j < second_row_length; j++) {
                IndividualMultiplierTask task = new IndividualMultiplierTask(result, first_matrix, second_matrix, i, j);
                Thread thread = new Thread(task, "individual task");
                thread.start();
                threads.add(thread);
                if (threads.size() % 10 == 0) {
                    waitForThreads(threads);
                }
            }
        }
    }

    public static void multiplyRowThread(double[][] first_matrix, double[][] second_matrix, double[][] result) {
        List<Thread> threads = new ArrayList<>();
        int first_row_length = first_matrix.length;
        for (int i = 0; i < first_row_length; i++) {
            RowMultiplierTask task = new RowMultiplierTask(result, first_matrix, second_matrix, i);
            Thread thread = new Thread(task, "row task");
            thread.start();
            threads.add(thread);
            if (threads.size() % 10 == 0) {
                waitForThreads(threads);
            }
        }
    }

    public static void multiplyGroupThread(double[][] first_matrix, double[][] second_matrix, double[][] result) {
        List<Thread> threads = new ArrayList<>();
        int first_row = first_matrix.length;
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        int startIndex, endIndex, step;
        step = first_row / numOfThreads;
        startIndex = 0;
        endIndex = step;
        for (int i = 0; i < numOfThreads; i++) {
            GroupMultiplierTask task = new GroupMultiplierTask(result, first_matrix, second_matrix, startIndex, endIndex);
            Thread thread = new Thread(task, "Group threads");
            thread.start();
            threads.add(thread);
            startIndex = endIndex;
            endIndex = i == numOfThreads - 2 ? first_row : endIndex + step;
        }
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        threads.clear();
    }
}
