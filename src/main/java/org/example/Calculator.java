package org.example;

public class Calculator implements Runnable {
    private final TaskQueue taskQueue;
    private final ResultStore resultStore;

    public Calculator(TaskQueue taskQueue, ResultStore resultStore) {
        this.taskQueue = taskQueue;
        this.resultStore = resultStore;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Task task = taskQueue.getTask();
                double piApproximation = calculatePi(task.getIterations());
                String result = "Zadanie ID: " + task.getId() + ", Wartość Pi: " + piApproximation;

                // Dodanie wyniku do magazynu i wyświetlenie go od razu w terminalu
                resultStore.addResult(result);
                System.out.println(result);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private double calculatePi(long iterations) {
        double pi = 0.0;
        long completedIterations = 0;

        for (long i = 0; i < iterations; i++) {
            if (Thread.currentThread().isInterrupted()) {
                double completionPercentage = (double) i / iterations * 100;
                System.out.println("Wątek przerwany. Ukończono " + completionPercentage + "% iteracji.");
                return pi * 4;
            }
            pi += Math.pow(-1, i) / (2 * i + 1);
            completedIterations++;
        }

        System.out.println("Wątek ukończył 100% iteracji.");
        return pi * 4;
    }

}
