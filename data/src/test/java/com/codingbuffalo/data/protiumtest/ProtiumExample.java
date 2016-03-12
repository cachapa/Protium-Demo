package com.codingbuffalo.data.protiumtest;

import android.databinding.ObservableInt;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProtiumExample {
    @Test
    public void simpleGet() throws Exception {
        TestInteractor interactor = new TestInteractor();
        
        ObservableInt value = interactor.getValueSimple();
        interactor.join();
        
        assertEquals(5, value.get());
    }
    
    @Test
    public void sequentialSum() throws Exception {
        TestInteractor interactor = new TestInteractor();
        
        ObservableInt value = interactor.getSequentialSum();
        interactor.join();
        
        assertEquals(7, value.get());
    }
    
    @Test
    public void parallelSum() throws Exception {
        TestInteractor interactor = new TestInteractor();
        
        ObservableInt value = interactor.getParallelSum();
        interactor.join();
        
        assertEquals(7, value.get());
    }
}