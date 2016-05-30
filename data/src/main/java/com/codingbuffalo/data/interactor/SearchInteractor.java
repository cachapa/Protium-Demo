package com.codingbuffalo.data.interactor;

import com.codingbuffalo.data.model.GifList;
import com.codingbuffalo.data.model.Page;
import com.codingbuffalo.data.model.StateHolder;
import com.codingbuffalo.data.protium.Interactor;
import com.codingbuffalo.data.protium.ValueTask;
import com.codingbuffalo.data.repository.GiphyRepository;

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
    private class SearchTask extends ValueTask<Page> {
        @Override
        public Page onExecute() throws Exception {
            stateHolder.setState(StateHolder.State.WORKING);
            
            return repository.search(query, gifs.getList().size());
        }

        @Override
        public void onComplete(Page data) {
            gifs.setData(data.getEntries(), data.getTotalCount());
            stateHolder.setState(StateHolder.State.IDLE);
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
            stateHolder.setFailure(e);
        }
    }
}
