package com.codingbuffalo.data.protium;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StateHolder {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({State.IDLE, State.WORKING, State.FAILURE})
    public @interface State {
        int IDLE    = 0;
        int WORKING = 1;
        int FAILURE = -1;
    }

    private ObservableInt mState;
    private ObservableField<Exception> mException;

    public StateHolder() {
        mState = new ObservableInt(State.IDLE);
        mException = new ObservableField<>();
    }

    public void setState(@State int state) {
        mState.set(state);
    }

    public void setFailure(Exception e) {
        mException.set(e);
        setState(State.FAILURE);
    }

    public ObservableInt getStateObservable() {
        return mState;
    }

    @StateHolder.State
    public int getState() {
        //noinspection WrongConstant
        return mState.get();
    }

    public ObservableField<Exception> getException() {
        return mException;
    }
}
