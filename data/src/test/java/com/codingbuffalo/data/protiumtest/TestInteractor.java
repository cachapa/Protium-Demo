package com.codingbuffalo.data.protiumtest;

import android.databinding.ObservableInt;

import com.codingbuffalo.data.interactor.Interactor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestInteractor extends Interactor {
    private TestRepository fastRepository;
    private TestRepository slowRepository;
    
    private ObservableInt value;
    private Future        currentTask;
    
    public TestInteractor() {
        super(Executors.newCachedThreadPool());
        
        fastRepository = new TestRepository(2);
        slowRepository = new TestRepository(5);
        
        value = new ObservableInt(0);
    }
    
    /**
     * Simple getter which executes in the background and updates the observable when finished
     */
    public ObservableInt getValueSimple() {
        currentTask = execute(new SimpleGetTask());
        return value;
    }
    
    /**
     * Composed getter which fetches from two repositories sequentially
     */
    public ObservableInt getSequentialSum() {
        currentTask = execute(new SequentialSumTask());
        return value;
    }
    
    public ObservableInt getParallelSum() {
        currentTask = execute(new ParallelSumTask());
        return value;
    }
    
    /**
     * Waits until tasks finish.
     * Useful for testing, but not to be used in production code
     */
    public void join() throws ExecutionException, InterruptedException {
        currentTask.get();
    }
    
    private class SimpleGetTask implements Runnable {
        @Override
        public void run() {
            try {
                int value = slowRepository.getValue();
                TestInteractor.this.value.set(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class SequentialSumTask implements Runnable {
        @Override
        public void run() {
            try {
                int value1 = fastRepository.getValue();
                int value2 = slowRepository.getValue();
                
                value.set(value1 + value2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private class ParallelSumTask implements Runnable {
        @Override
        public void run() {
            try {
                Callable<Integer> task1 = new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return fastRepository.getValue();
                    }
                };
    
                Callable<Integer> task2 = new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return slowRepository.getValue();
                    }
                };
                
                Future<Integer> future1 = execute(task1);
                Future<Integer> future2 = execute(task2);
                
                int value1 = future1.get();
                int value2 = future2.get();
                
                value.set(value1 + value2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
