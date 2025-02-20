package org.example.chapter2;

import java.util.concurrent.TimeUnit;

public class CleanCacheTask implements Runnable {
    private final ParallelCache parallelCache;

    public CleanCacheTask(ParallelCache parallelCache) {
        this.parallelCache = parallelCache;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(10);
                parallelCache.cleanCache();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
