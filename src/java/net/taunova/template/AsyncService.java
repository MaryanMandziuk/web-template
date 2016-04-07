/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package net.taunova.template;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Provides a basic asynchronous job execution service.
 * 
 * @author Renat.Gilmanov
 */
public class AsyncService {

    /**
     * 
     */
    private final ExecutorService pool;

    /**
     * Constructs a service with thread pool initialized by a number of available cores.
     */
    public AsyncService() {
        final int cores = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(cores);
    }

    /**
     * Executes a task.
     * 
     * @param task a task to be executed
     */
    public void execute(Runnable task) {
        pool.execute(task);
    }

    /**
     * Terminates all tasks and stopes the service.
     */
    public void shutdown() {
        pool.shutdown(); // Disable new tasks from being submitted
        
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); 
                
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();            
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }
}
