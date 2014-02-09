package com.github.axet.threads;

import java.util.ArrayList;
import java.util.List;

import com.github.axet.threads.RecursiveThreadExecutor.Task;

public class RecursiveThreadTasks {
    RecursiveThreadExecutor es;

    List<Task> tasks = new ArrayList<Task>();

    public RecursiveThreadTasks(RecursiveThreadExecutor e) {
        this.es = e;
    }

    public void execute(Runnable r) {
        Task t = new Task(r);
        tasks.add(t);
        es.execute(t);
    }

    public void waitTermination() throws InterruptedException {
        for (Task r : tasks) {
            es.waitTermination(r);
        }
    }
}
