package org.example.chapter4;

public abstract class CustomCommand {
    protected final String[] command;

    protected CustomCommand(String[] command) {
        this.command = command;
    }

    public abstract String execute();

    private boolean isCacheable = false;

    public boolean isCacheable() {
        return isCacheable;
    }

    public void setCacheable(boolean cacheable) {
        isCacheable = cacheable;
    }
}
