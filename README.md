# threads

Threads utils

# Examples LimitThreadPool

When you have limited, independent tasks, and you have limited amount of threads and you want to control when all jobs done use LimitThreadPool

  * limited number of tasks
  * tasks are independend and can be processed parallelry
  * need to know when all tasks done

![threads](/docs/LimitThreadPool.png)

```java
package com.github.axet.threads;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.axet.threads.LimitThreadPool;

public class LimitThreadPoolExample {

    static AtomicInteger c = new AtomicInteger();

    public static void main(String[] args) {
        try {
            // create 4 threads
            LimitThreadPool l = new LimitThreadPool(4);

            // put 100 tasks
            for (int i = 0; i < 100; i++) {
                l.blockExecute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        c.addAndGet(1);
                    }
                });
            }

            // wait until all tasks ends
            l.waitUntilTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c);
    }
}
```

# Example Recursive Thread Pool

When you do not know how many tasks here, and tasks can produce dependend tasks, and you have limited threads use RecursiveThreadPool.

  * tasks which produce another tasks and here is no way you can make it linear or independent
  * limited threads to process jobs
  * some tasks indepeneded and can be processed parralely

![threads](/docs/RecursiveThreadPool.png)

```java
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
                    // do work
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
        // wait thread tasks and its dependencies
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
```

## Cetral Maven Repo

```xml
<dependency>
  <groupId>com.github.axet</groupId>
  <artifactId>threads</artifactId>
  <version>0.0.14</version>
</dependency>
```
