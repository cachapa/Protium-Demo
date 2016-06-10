package net.cachapa.data;

import net.cachapa.data.model.Page;
import net.cachapa.data.repository.GiphyRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GiphyRepositoryTests {
    private GiphyRepository mRepository;
    
    @Before
    public void setup() {
        mRepository = new GiphyRepository();
    }

    @Test
    public void suggestions() throws Exception {
        List<String> suggestions = mRepository.getSuggestions("ca");

        assertEquals(suggestions.size(), 25);
    }
    
    @Test
    public void search() throws Exception {
        Page gifPage = mRepository.search("beer", 0);
        
        assertEquals(gifPage.getEntries().size(), 25);
        assertTrue(gifPage.getTotalCount() > 1000);
    }
}
