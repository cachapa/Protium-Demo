package com.codingbuffalo.data.protium;

public abstract class Interactor {
    private Scheduler   mScheduler;
    private StateHolder mStateHolder;
    
    public Interactor(Scheduler scheduler) {
        mScheduler = scheduler;
        mStateHolder = new StateHolder();
    }
    
    public final StateHolder getStateHolder() {
        return mStateHolder;
    }
    
    protected final void execute(final Task task) {
        mScheduler.execute(task, mStateHolder);
    }
}
