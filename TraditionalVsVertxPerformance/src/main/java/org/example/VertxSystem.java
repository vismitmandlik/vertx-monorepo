package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class VertxSystem extends AbstractVerticle {

    private static final int NUM_REQUESTS = 100;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(20));
        vertx.deployVerticle(new VertxSystem(startTime));
    }

    private final long startTime;

    public VertxSystem(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        // Use an array to hold the current index
        final int[] completedRequests = {0};

        for (int i = 0; i < NUM_REQUESTS; i++) {
            vertx.executeBlocking(promise -> {
                try {
                    // Simulating a blocking I/O operation
                    Thread.sleep(100); // Simulating I/O delay
                } catch (InterruptedException e) {
                    promise.fail(e);
                }
                promise.complete();
            },false, res -> {
                // Increment the completed request count
                completedRequests[0]++;

                // Check if all tasks are done
                if (completedRequests[0] == NUM_REQUESTS) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Total time taken (Vert.x System): " + (endTime - startTime) + " ms");
                }
            });
        }
    }
}
