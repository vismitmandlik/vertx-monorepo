package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TraditionalSystem {
    public static void main(String[] args) {
        final int NUM_REQUESTS = 100;
        final int MAX_THREADS = 20;

        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_REQUESTS; i++) {
            executor.submit(() -> {
                try {
                    // Simulating a blocking I/O operation
                    Thread.sleep(100); // Simulating I/O delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken (Traditional System): " + (endTime - startTime) + " ms");
    }
}
