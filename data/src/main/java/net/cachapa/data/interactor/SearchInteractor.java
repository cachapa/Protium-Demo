package net.cachapa.data.interactor;

import net.cachapa.data.model.GifsObservable;
import net.cachapa.data.model.Page;
import net.cachapa.data.model.StateHolder;
import net.cachapa.data.repository.GiphyRepository;
import net.cachapa.protium.Interactor;
import net.cachapa.protium.ValueTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SearchInteractor extends Interactor {
    private GiphyRepository repository;
    private String          query;
    private Future          future;
    
    private StateHolder stateHolder;
    private GifsObservable gifsObservable;
    
    public SearchInteractor(ExecutorService service, GiphyRepository repository) {
        super(service);
        
        this.repository = repository;
        
        stateHolder = new StateHolder();
        gifsObservable = new GifsObservable();
    }
    
    public StateHolder getStateHolder() {
        return stateHolder;
    }
    
    public GifsObservable getGifsObservable() {
        return gifsObservable;
    }

    public void setQuery(String query) {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        
        this.query = query;
        gifsObservable.clear();
        fetchNextPage();
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
            
            return repository.search(query, gifsObservable.getList().size());
        }

        @Override
        public void onComplete(Page data) {
            gifsObservable.setData(data.getEntries(), data.getTotalCount());
            stateHolder.setState(StateHolder.State.IDLE);
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace();
            stateHolder.setFailure(e);
        }
    }
}
