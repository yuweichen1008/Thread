# thread

Threads utils

# Examples

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

## Cetral Maven Repo

    <dependency>
      <groupId>com.github.axet</groupId>
      <artifactId>wget</artifactId>
      <version>1.2.9</version>
    </dependency>
