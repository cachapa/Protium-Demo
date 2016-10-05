package net.cachapa.protiumdemo.registry;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.gateway.GiphyGateway;

import java.util.concurrent.Executors;

public class InteractorRegistry {
    private static GiphyGateway gateway = new GiphyGateway();
    private static SearchInteractor interactor;

    public synchronized static SearchInteractor getSearchInteractor() {
        if (interactor == null) {
            interactor = new SearchInteractor(Executors.newCachedThreadPool(), gateway);
        }

        return interactor;
    }
}
