package net.cachapa.data;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.mock.BlockingExecutorService;
import net.cachapa.data.model.GifsObservable;
import net.cachapa.data.repository.GiphyRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchInteractorTests {
    private SearchInteractor mInteractor;
    
    @Before
    public void setup() {
        mInteractor = new SearchInteractor(new BlockingExecutorService(), new GiphyRepository(), "beer");
    }
    
    @Test
    public void search() throws Exception {
        GifsObservable gifsObservable = mInteractor.getGifsObservable();
        
        assertEquals(gifsObservable.getList().size(), 25);
        assertTrue(gifsObservable.getTotalCount() > 1000);
    }
}
