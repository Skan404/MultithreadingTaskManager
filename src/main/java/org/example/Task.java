package org.example;

public class Task {
    private final int id;
    private final long iterations;

    public Task(int id, long iterations) {
        this.id = id;
        this.iterations = iterations;
    }

    public int getId() {
        return id;
    }

    public long getIterations() {
        return iterations;
    }
}
