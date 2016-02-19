package com.codingbuffalo.data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GiphyRepositoryTests {
    private GiphyRepository mRepository;
    
    @Before
    public void setup() {
        mRepository = new GiphyRepository();
    }
    
    @Test
    public void search() throws Exception {
        Page<Gif> gifPage = mRepository.search("beer", 0);
        assertEquals(gifPage.getEntries().size(), 25);
        assertTrue(gifPage.getTotalCount() > 1000);
    }
}