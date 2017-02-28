package net.cachapa.protium;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ObservableModel<T> extends BaseObservable {
    private static final int IDLE = 0;
    private static final int BUSY = 1;
    private static final int ERROR = 2;

    private T model;
    private int state = IDLE;
    private Exception exception;

    public ObservableModel(@NonNull T model) {
        this.model = model;
    }

    public void setModel(@NonNull T model) {
        this.model = model;
        notifyChange();
    }

    @NonNull
    public T getModel() {
        return model;
    }

    /* Convenience methods for databinding */

    public boolean isIdle() {
        return state == IDLE;
    }

    public boolean isBusy() {
        return state == BUSY;
    }

    public boolean isError() {
        return state == ERROR;
    }

    @Nullable
    public Exception getException() {
        return exception;
    }

    /* Protected setters */

    void setIdle() {
        setState(IDLE, null);
    }

    void setBusy() {
        setState(BUSY, null);
    }

    void setError(@NonNull Exception e) {
        setState(ERROR, e);
    }

    private void setState(int state, @Nullable Exception e) {
        if (this.state != state) {
            this.state = state;
            this.exception = e;
            notifyChange();
        }
    }
}
