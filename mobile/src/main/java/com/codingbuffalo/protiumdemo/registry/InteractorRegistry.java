package com.codingbuffalo.protiumdemo.registry;

import com.codingbuffalo.data.GiphyRepository;
import com.codingbuffalo.data.SearchInteractor;

import java.util.HashMap;
import java.util.Map;

public class InteractorRegistry {
    private static GiphyRepository               mRepository    = new GiphyRepository();
    private static Map<String, SearchInteractor> mInteractorMap = new HashMap<>();
    
    public synchronized static SearchInteractor getSearchInteractor(String query) {
        if (!mInteractorMap.containsKey(query)) {
            SearchInteractor interactor = new SearchInteractor(mRepository, query);
            mInteractorMap.put(query, interactor);
        }
        
        return mInteractorMap.get(query);
    }
}
