package org.example;

import java.util.ArrayList;
import java.util.List;

public class ResultStore {
    private final List<String> results = new ArrayList<>();

    public synchronized void addResult(String result) {
        results.add(result);
    }

    public synchronized List<String> getResults() {
        return new ArrayList<>(results);
    }
}

