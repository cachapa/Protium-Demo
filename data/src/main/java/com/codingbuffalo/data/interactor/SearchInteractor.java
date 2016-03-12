package com.codingbuffalo.data.interactor;

import com.codingbuffalo.data.model.Gif;
import com.codingbuffalo.data.model.GifList;
import com.codingbuffalo.data.model.Page;
import com.codingbuffalo.data.repository.GiphyRepository;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchInteractor extends Interactor {
    private GiphyRepository mRepository;
    private String          mQuery;
    private Future          mFuture;
    
    private GifList mGifs;
    
    public SearchInteractor(GiphyRepository repository, String query) {
        this(Executors.newCachedThreadPool(), repository, query);
    }
    
    public SearchInteractor(ExecutorService service, GiphyRepository repository, String query) {
        super(service);
        
        mRepository = repository;
        mQuery = query;
        
        mGifs = new GifList();
    }
    
    public GifList getGifs() {
        fetchNextPage();
        return mGifs;
    }
    
    public synchronized void fetchNextPage() {
        if (mFuture == null || mFuture.isDone()) {
            mFuture = execute(new SearchTask());
        }
    }
    
    /* Tasks */
    private class SearchTask implements Runnable {
        @Override
        public void run() {
            try {
                Page<Gif> page = mRepository.search(mQuery, mGifs.getList().size());
                mGifs.getList().addAll(page.getEntries());
                mGifs.setTotalCount(page.getTotalCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
