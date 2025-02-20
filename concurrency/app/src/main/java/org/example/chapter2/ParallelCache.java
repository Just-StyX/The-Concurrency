package org.example.chapter2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelCache {
    private static final ConcurrentHashMap<String, CacheItem> cacheItemConcurrentHashMap =new ConcurrentHashMap<>();
    private final Thread thread;
    public static int Max_LIVING_TIME_MILLIS = 600_000;

    private ParallelCache() {
        thread = new Thread(new CleanCacheTask(this));
        thread.start();
    }
    public static ParallelCache getInstance() { return new ParallelCache(); }
    public void put(String command, String response) {
        CacheItem cacheItem = CacheItem.getInstance(command, response);
        cacheItemConcurrentHashMap.put(command, cacheItem);
    }
    public String get(String command) {
        CacheItem cacheItem = cacheItemConcurrentHashMap.get(command);
        if (cacheItem == null) return null;
        cacheItem.setLastTimeAccessed(LocalDateTime.now());
        return cacheItem.getResponse();
    }
    public void cleanCache() {
        LocalDateTime localDateTime = LocalDateTime.now();
        cacheItemConcurrentHashMap.values().removeIf(cacheItem -> Duration.between(localDateTime, cacheItem.getLocalDateTime()).toMillis() > Max_LIVING_TIME_MILLIS);
    }
    public void shutdown() { thread.interrupt(); }
    public int getItemCount() { return cacheItemConcurrentHashMap.size(); }
}
