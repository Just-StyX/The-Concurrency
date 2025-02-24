package org.example.chapter4;

import org.example.chapter2.CustomLogger;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;

public class ServerExecutor extends ThreadPoolExecutor {
    private final ConcurrentHashMap<Runnable, Date> startTimes;
    private final ConcurrentHashMap<String, ExecutorStatistics> executionStatistics;
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final long KEEP_ALIVE_TIME = 10;
    private static final RejectedTaskController REJECTED_TASK_CONTROLLER = RejectedTaskController.getInstance();

    public ServerExecutor() {
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new PriorityBlockingQueue<>(), REJECTED_TASK_CONTROLLER);
        startTimes = new ConcurrentHashMap<>();
        executionStatistics = new ConcurrentHashMap<>();
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        startTimes.put(runnable, new Date());
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        super.afterExecute(runnable, throwable);
        ServerTask<?> task = (ServerTask<?>) runnable;
        ConcurrentCommand command = task.getCommand();

        if (throwable == null) {
            if (task.isCancelled()) {
                Date startDate = startTimes.remove(runnable);
                Date endDate = new Date();
                long executionTime = endDate.getTime() - startDate.getTime();
                ExecutorStatistics executorStatistics = executionStatistics.computeIfAbsent(command.getUsername(), n -> new ExecutorStatistics());
                executorStatistics.addExecutionTime(executionTime);
                executorStatistics.addTask();
                ServerConcurrent.finishTask(command.getUsername(), command);
            } else {
                var message = String.format("The task %s of user %s has been cancelled.", command.hashCode(), command.getUsername());
                CustomLogger.sendMessage(message);
            }
        } else {
            var message = String.format("The exception %s has been thrown.", throwable.getMessage());
            CustomLogger.sendMessage(message);
        }
    }
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return ServerTask.getInstance((ConcurrentCommand) runnable);
    }

    public void writeStatistics() {
        for (Map.Entry<String, ExecutorStatistics> entry: executionStatistics.entrySet()) {
            String user = entry.getKey();
            ExecutorStatistics statistics = entry.getValue();
            CustomLogger.sendMessage(user + " : " + statistics);
        }
    }
}
