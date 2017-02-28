package net.cachapa.protium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProtiumExample {
    @Test
    public void simpleGet() throws Exception {
        TestInteractor interactor = new TestInteractor();

        ObservableModel<Integer> value = interactor.getValueSimple();
        interactor.join();

        assertEquals(5, value.getModel().intValue());
    }

    @Test
    public void sequentialSum() throws Exception {
        TestInteractor interactor = new TestInteractor();

        ObservableModel<Integer> value = interactor.getSequentialSum();
        interactor.join();

        assertEquals(7, value.getModel().intValue());
    }

    @Test
    public void parallelSum() throws Exception {
        TestInteractor interactor = new TestInteractor();

        ObservableModel<Integer> value = interactor.getParallelSum();
        interactor.join();

        assertEquals(7, value.getModel().intValue());
    }

    @Test
    public void failure() {
        TestInteractor interactor = new TestInteractor();

        final StringBuilder errorMsg = new StringBuilder();

        ObservableModel<Integer> value = interactor.failTask();
        interactor.join();
        
        assertTrue(value.isError());
    }

    @Test
    public void cancelTask() throws Exception {
        TestInteractor interactor = new TestInteractor();

        ObservableModel<Integer> value = interactor.getParallelSum();
        interactor.cancel();
        interactor.join();

        assertEquals(0, value.getModel().intValue());
        assertTrue(value.isIdle());
    }
}
