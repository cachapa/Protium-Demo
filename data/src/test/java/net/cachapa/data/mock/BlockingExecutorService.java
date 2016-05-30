package net.cachapa.data.mock;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * An ExecutorService that runs all tasks in the calling thread rather than in the background.
 * Useful for unit testing.
 */
public class BlockingExecutorService extends AbstractExecutorService{
    
    @Override
    public void shutdown() {
    }
    
    @NonNull
    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }
    
    @Override
    public boolean isShutdown() {
        return false;
    }
    
    @Override
    public boolean isTerminated() {
        return false;
    }
    
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }
    
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
