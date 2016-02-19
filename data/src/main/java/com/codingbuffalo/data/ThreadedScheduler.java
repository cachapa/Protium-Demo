package com.codingbuffalo.data;

import com.codingbuffalo.data.protium.Scheduler;
import com.codingbuffalo.data.protium.StateHolder;
import com.codingbuffalo.data.protium.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedScheduler implements Scheduler {
    private static final int THREAD_POOL_SIZE = 1;
    
    private ExecutorService mExecutorService;
    
    public ThreadedScheduler() {
        mExecutorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }
    
    @Override
    public synchronized void execute(final Task task, final StateHolder stateHolder) {
        stateHolder.setState(StateHolder.State.WORKING);
        
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    task.execute();
                    stateHolder.setState(StateHolder.State.IDLE);
                } catch (Exception e) {
                    stateHolder.setFailure(e);
                    e.printStackTrace();
                }
            }
        });
    }
}
