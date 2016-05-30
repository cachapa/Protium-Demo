package net.cachapa.protium;

import java.util.concurrent.Callable;

public abstract class Task<T> implements Callable<T> {
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
