package net.cachapa.protium;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class Interactor {
    private ExecutorService executor;

    public Interactor(ExecutorService executor) {
        this.executor = executor;
    }

    @NonNull
    protected final <T> Future execute(Task<T> task, ObservableModel<T> observableModel) {
        return executor.submit(new ValueTask<>(task, observableModel));
    }

    private class ValueTask<T> implements Runnable {
        private Task<T> task;
        private ObservableModel<T> observableModel;

        ValueTask(Task<T> task, ObservableModel<T> observableModel) {
            this.task = task;
            this.observableModel = observableModel;
        }

        @Override
        public final void run() {
            try {
                observableModel.setBusy();
                T model = task.run();

                if (!Thread.currentThread().isInterrupted()) {
                    observableModel.setModel(model);
                    observableModel.setIdle();
                }
            } catch (Exception e) {
                e.printStackTrace();
                observableModel.setError(e);
            }
        }
    }
}
