package net.cachapa.protium;

public abstract class ValueTask<T> extends Task<T> {
    @Override
    public final T call() {
        try {
            T data = onExecute();

            if (!Thread.currentThread().isInterrupted()) {
                onComplete(data);
                return data;
            }
        } catch (Exception e) {
            onError(e);
        }
        
        return null;
    }

    public abstract T onExecute() throws Exception;
    
    public abstract void onComplete(T data);
}
