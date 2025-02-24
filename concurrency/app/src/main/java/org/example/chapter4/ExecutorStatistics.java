package org.example.chapter4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorStatistics {
    private final AtomicInteger numTasks = new AtomicInteger(0);
    private final AtomicLong executionTime = new AtomicLong(0);

    public AtomicInteger getNumTasks() {
        return numTasks;
    }

    public void addTask() {
        this.numTasks.incrementAndGet();
    }

    public AtomicLong getExecutionTime() {
        return executionTime;
    }

    public void addExecutionTime(long executionTime) {
        this.executionTime.addAndGet(executionTime);
    }

    @Override
    public String toString() {
        return String.format("Executed Tasks: %s. Executed Time: %s", getNumTasks(), getExecutionTime());
    }
}
