package com.github.axet.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class RecursiveThreadTest {

    // create 4 working threads. all tasks will be distributed between.
    static final RecursiveThreadExecutor threadsHolder = new RecursiveThreadExecutor(4);

    // Recursive function, which do some job, and produce more job
    public static int recurciveTaks(final int level, final int maxLevel) {
        RecursiveThreadTasks threadTasks = new RecursiveThreadTasks(threadsHolder);

        final AtomicInteger r = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            // put first task
            threadTasks.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                        r.incrementAndGet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // create depened tasks which should ends before current
                    // task, which can also create another tasks
                    recurciveTaks(level + 1, maxLevel);
                }
            });
        }
        try {
            threadTasks.waitTermination();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return r.get();
    }

    public static void main(String[] args) {
        int result = recurciveTaks(0, 5);
        System.out.println(result);
    }
}