package com.pool.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sucl
 * @since 2018/12/12
 */
public class ExecutorsTest {

    private ExecutorService es1 = Executors.newFixedThreadPool(10);
    private ExecutorService es2 = Executors.newFixedThreadPool(10);
    private ExecutorService es3 = Executors.newScheduledThreadPool(10);
    private ExecutorService es4 = Executors.newSingleThreadExecutor();
    private ExecutorService es5 = Executors.newSingleThreadScheduledExecutor();
    private ExecutorService es6 = Executors.newWorkStealingPool();
    private ExecutorService es7 = Executors.unconfigurableExecutorService(es1);

}
