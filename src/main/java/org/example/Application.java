package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("Aktywnych wątków przed uruchomieniem: " + Thread.activeCount());

        TaskQueue taskQueue = new TaskQueue();
        ResultStore resultStore = new ResultStore();
        List<Thread> threads = new ArrayList<>();

        int numberOfThreads = args.length > 0 ? Integer.parseInt(args[0]) : 2;

        // Uruchomienie wątków
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(new Calculator(taskQueue, resultStore)));
            threads.get(i).start();
        }


        int taskId = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wprowadź liczbę iteracji dla metody Leibnitza (lub wpisz 'quit' aby zakończyć):");

        while (!Thread.currentThread().isInterrupted()) {
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if ("quit".equalsIgnoreCase(input)) {
                    break;
                }

                try {
                    long iterations = Long.parseLong(input);
                    taskQueue.addTask(new Task(taskId++, iterations));
                } catch (NumberFormatException e) {
                    System.out.println("Niepoprawny format. Wprowadź liczbę całkowitą.");
                }
            } else {
                try {
                    Thread.sleep(100); // uśpienie
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }


        // Zakończenie wątków
        threads.forEach(Thread::interrupt);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Przerwano oczekiwanie na zakończenie wątku: " + e.getMessage());
            }
        }

        System.out.println("Aktywnych wątków po zakończeniu aplikacji: " + Thread.activeCount());
        resultStore.getResults().forEach(System.out::println);
    }
}
