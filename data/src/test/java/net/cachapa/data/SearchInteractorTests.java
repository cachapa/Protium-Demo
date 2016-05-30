package net.cachapa.data;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.mock.BlockingExecutorService;
import net.cachapa.data.model.GifList;
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
        GifList gifList = mInteractor.getGifs();
        
        assertEquals(gifList.getList().size(), 25);
        assertTrue(gifList.getTotalCount() > 1000);
    }
}
