package org.example.chapter2;

import java.time.LocalDateTime;

public class CacheItem {
    private String command;
    private String response;
    private LocalDateTime localDateTime;
    private LocalDateTime lastTimeAccessed;

    private CacheItem(String command, String response) {
        this.command = command;
        this.response = response;
        this.localDateTime = LocalDateTime.now();
    }

    public static CacheItem getInstance(String command, String response) {
        return new CacheItem(command, response);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLastTimeAccessed() {
        return lastTimeAccessed;
    }

    public void setLastTimeAccessed(LocalDateTime lastTimeAccessed) {
        this.lastTimeAccessed = lastTimeAccessed;
    }
}
