package net.cachapa.data;

import net.cachapa.data.gateway.ClientManager;
import net.cachapa.data.gateway.GiphyGateway;
import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.mock.BlockingExecutorService;
import net.cachapa.data.model.Page;
import net.cachapa.protium.ObservableModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchInteractorTests {
    private SearchInteractor mInteractor;
    
    @Before
    public void setup() {
        mInteractor = new SearchInteractor(new BlockingExecutorService(), new GiphyGateway(ClientManager.getClient()), "beer");
    }
    
    @Test
    public void search() throws Exception {
        ObservableModel<Page> observable = mInteractor.getObservable();
        mInteractor.fetch();
        
        assertEquals(25, observable.getModel().size());
        assertTrue(observable.getModel().total() > 1000);
    }
}
