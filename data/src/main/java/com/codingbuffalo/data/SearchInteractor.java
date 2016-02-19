package com.codingbuffalo.data;

import com.codingbuffalo.data.protium.Interactor;
import com.codingbuffalo.data.protium.Scheduler;
import com.codingbuffalo.data.protium.StateHolder;
import com.codingbuffalo.data.protium.Task;

public class SearchInteractor extends Interactor {
    private GiphyRepository mRepository;
    private String          mQuery;
    
    private GifList mGifs;
    
    public SearchInteractor(GiphyRepository repository, String query) {
        this(new ThreadedScheduler(), repository, query);
    }
    
    public SearchInteractor(Scheduler scheduler, GiphyRepository repository, String query) {
        super(scheduler);
        
        mRepository = repository;
        mQuery = query;
        
        mGifs = new GifList();
    }
    
    public GifList getGifs() {
        fetchNextPage();
        return mGifs;
    }
    
    public synchronized void fetchNextPage() {
        if (getStateHolder().getState() == StateHolder.State.IDLE &&
                (mGifs.getTotalCount().get() == 0 || mGifs.getList().size() < mGifs.getTotalCount().get())) {
            
            execute(new SearchTask());
        }
    }
    
    /* Tasks */
    private class SearchTask implements Task {
        @Override
        public void execute() throws Exception {
            Page<Gif> page = mRepository.search(mQuery, mGifs.getList().size());
            
            mGifs.getList().addAll(page.getEntries());
            mGifs.setTotalCount(page.getTotalCount());
        }
    }
}
