package com.codingbuffalo.data;

import com.codingbuffalo.data.interactor.SearchInteractor;
import com.codingbuffalo.data.mock.BlockingExecutorService;
import com.codingbuffalo.data.model.GifList;
import com.codingbuffalo.data.repository.GiphyRepository;

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
