package com.codingbuffalo.data.interactor;

import com.codingbuffalo.data.model.Gif;
import com.codingbuffalo.data.model.GifList;
import com.codingbuffalo.data.model.Page;
import com.codingbuffalo.data.model.StateHolder;
import com.codingbuffalo.data.repository.GiphyRepository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchInteractor extends Interactor {
    private GiphyRepository repository;
    private String          query;
    private Future          future;
    
    private StateHolder stateHolder;
    private GifList     gifs;
    
    public SearchInteractor(GiphyRepository repository, String query) {
        this(Executors.newCachedThreadPool(), repository, query);
    }
    
    public SearchInteractor(ExecutorService service, GiphyRepository repository, String query) {
        super(service);
        
        this.repository = repository;
        this.query = query;
        
        stateHolder = new StateHolder();
        gifs = new GifList();
    }
    
    public StateHolder getStateHolder() {
        return stateHolder;
    }
    
    public GifList getGifs() {
        if (gifs.getList().isEmpty()) {
            fetchNextPage();
        }
        
        return gifs;
    }
    
    public synchronized void fetchNextPage() {
        if (future == null || future.isDone()) {
            future = execute(new SearchTask());
        }
    }
    
    /* Tasks */
    private class SearchTask implements Runnable {
        @Override
        public void run() {
            try {
                stateHolder.setState(StateHolder.State.WORKING);
                
                Page<Gif> page = repository.search(query, gifs.getList().size());
                gifs.getList().addAll(page.getEntries());
                gifs.setTotalCount(page.getTotalCount());
    
                stateHolder.setState(StateHolder.State.IDLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
