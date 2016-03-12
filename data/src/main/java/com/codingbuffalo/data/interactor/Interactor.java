package com.codingbuffalo.data.interactor;

import android.support.annotation.NonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class Interactor {
    private ExecutorService mService;
    
    public Interactor(@NonNull ExecutorService service) {
        mService = service;
    }
    
    @NonNull
    protected final Future execute(@NonNull Runnable task) {
        return mService.submit(task);
    }
    
    @NonNull
    protected final <T> Future<T> execute(@NonNull Callable<T> task) {
        return mService.submit(task);
    }
}
