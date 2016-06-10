package net.cachapa.protiumdemo.registry;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.repository.GiphyRepository;

import java.util.concurrent.Executors;

public class InteractorRegistry {
    private static GiphyRepository repository = new GiphyRepository();
    private static SearchInteractor interactor;

    public synchronized static SearchInteractor getSearchInteractor() {
        if (interactor == null) {
            interactor = new SearchInteractor(Executors.newCachedThreadPool(), repository);
        }

        return interactor;
    }
}
