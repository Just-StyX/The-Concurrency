package org.example.chapter2;

public abstract class Command<T> {
    private boolean isCacheable = false;
    public abstract T execute();

    public boolean isCacheable() {
        return isCacheable;
    }

    public void setCacheable(boolean cacheable) {
        isCacheable = cacheable;
    }
}
