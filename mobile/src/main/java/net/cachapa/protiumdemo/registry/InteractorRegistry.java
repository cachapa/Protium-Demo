package net.cachapa.protiumdemo.registry;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.repository.GiphyRepository;

import java.util.HashMap;
import java.util.Map;

public class InteractorRegistry {
    private static GiphyRepository repository    = new GiphyRepository();
    private static Map<String, SearchInteractor> interactorMap = new HashMap<>();
    
    public synchronized static SearchInteractor getSearchInteractor(String query) {
        if (!interactorMap.containsKey(query)) {
            SearchInteractor interactor = new SearchInteractor(repository, query);
            interactorMap.put(query, interactor);
        }
        
        return interactorMap.get(query);
    }
}
