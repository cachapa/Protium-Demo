package net.cachapa.protium;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestInteractor extends Interactor {
    private TestGateway fastGateway;
    private TestGateway slowGateway;

    private ObservableModel<Integer> value;
    private Future currentTask;

    public TestInteractor() {
        super(Executors.newCachedThreadPool());

        fastGateway = new TestGateway(2);
        slowGateway = new TestGateway(5);

        value = new ObservableModel<>(0);
    }

    /**
     * Simple getter which executes in the background and updates the observable when finished
     */
    public ObservableModel<Integer> getValueSimple() {
        currentTask = execute(new SimpleGetTask(), value);
        return value;
    }

    /**
     * Composed getter which fetches from two repositories sequentially
     */
    public ObservableModel<Integer> getSequentialSum() {
        currentTask = execute(new SequentialSumTask(), value);
        return value;
    }

    /**
     * Composed getter which fetches from two repository in parallel
     */
    public ObservableModel<Integer> getParallelSum() {
        currentTask = execute(new ParallelSumTask(), value);
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
    public ObservableModel<Integer> failTask() {
        currentTask = execute(new FailTask(), value);
        return value;
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

    public interface OnErrorListener {
        void onError(Exception e);
    }

    private class SimpleGetTask implements Task<Integer> {
        @Override
        public Integer run() throws InterruptedException {
            return slowGateway.getValue();
        }
    }

    private class SequentialSumTask implements Task<Integer> {
        @Override
        public Integer run() throws Exception {
            int value1 = fastGateway.getValue();
            int value2 = slowGateway.getValue();
            return value1 + value2;
        }
    }

    private class ParallelSumTask implements Task<Integer> {
        @Override
        public Integer run() throws Exception {
            Task<Integer> task1 = new Task<Integer>() {
                @Override
                public Integer run() throws Exception {
                    return fastGateway.getValue();
                }
            };

            Task<Integer> task2 = new Task<Integer>() {
                @Override
                public Integer run() throws Exception {
                    return slowGateway.getValue();
                }
            };

            ObservableModel<Integer> v1 = new ObservableModel<>(0);
            ObservableModel<Integer> v2 = new ObservableModel<>(0);
            Future future1 = execute(task1, v1);
            Future future2 = execute(task2, v2);

            // Block until both tasks finish
            future1.get();
            future2.get();

            return v1.getModel() + v2.getModel();
        }
    }

    private class FailTask implements Task<Integer> {
        @Override
        public Integer run() throws Exception {
            Thread.sleep(200);
            throw new Exception("Test exception");
        }
    }
}
