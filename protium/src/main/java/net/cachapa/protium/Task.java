package net.cachapa.protium;

public interface Task<T> {
    T run() throws Exception;
}
