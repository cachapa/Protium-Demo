package com.codingbuffalo.data.protium;

public interface Scheduler {
    void execute(final Task task, StateHolder stateHolder);
}
