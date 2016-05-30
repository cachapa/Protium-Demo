package com.codingbuffalo.data.protiumtest;

import android.databinding.ObservableInt;

import com.codingbuffalo.data.protium.Interactor;
import com.codingbuffalo.data.protium.SimpleTask;
import com.codingbuffalo.data.protium.ValueTask;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestInteractor extends Interactor {
    private TestRepository fastRepository;
    private TestRepository slowRepository;

    private ObservableInt value;
    private Future currentTask;

    private OnErrorListener errorListener;

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

    /**
     * Composed getter which fetches from two repository in parallel
     */
    public ObservableInt getParallelSum() {
        currentTask = execute(new ParallelSumTask());
        return value;
    }

    /**
     * Cancels current active task
     */
    public void cancel() {
        currentTask.cancel(false);
    }

    /**
     * Executes a task doomed to fail
     */
    public void failTask() {
        currentTask = execute(new FailTask());
    }

    /**
     * Waits until tasks finish.
     * Useful for testing, but not to be used in production code
     */
    public void join() {
        try {
            currentTask.get();
        } catch (Exception e) {
            // Ignore
        }
    }

    public void setErrorListener(OnErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public interface OnErrorListener {
        void onError(Exception e);
    }

    private class SimpleGetTask extends SimpleTask {
        @Override
        public void onExecute() throws Exception {
            int value = slowRepository.getValue();
            TestInteractor.this.value.set(value);
        }
    }

    private class SequentialSumTask extends SimpleTask {
        @Override
        public void onExecute() throws Exception {
            int value1 = fastRepository.getValue();
            int value2 = slowRepository.getValue();

            value.set(value1 + value2);
        }
    }

    private class ParallelSumTask extends ValueTask<Integer> {
        @Override
        public Integer onExecute() throws Exception {
            ValueTask<Integer> task1 = new ValueTask<Integer>() {
                @Override
                public Integer onExecute() throws Exception {
                    return fastRepository.getValue();
                }
            };

            ValueTask<Integer> task2 = new ValueTask<Integer>() {
                @Override
                public Integer onExecute() throws Exception {
                    return slowRepository.getValue();
                }
            };

            Future<Integer> future1 = execute(task1);
            Future<Integer> future2 = execute(task2);

            int value1 = future1.get();
            int value2 = future2.get();

            return value1 + value2;
        }

        @Override
        public void onComplete(Integer data) {
            value.set(data);
        }
    }

    private class FailTask extends SimpleTask {
        @Override
        public void onExecute() throws Exception {
            Thread.sleep(200);
            throw new Exception("Test exception");
        }

        @Override
        public void onError(Exception e) {
            errorListener.onError(e);
        }
    }
}
