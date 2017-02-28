package net.cachapa.data;

import net.cachapa.data.gateway.ClientManager;
import net.cachapa.data.model.Page;
import net.cachapa.data.gateway.GiphyGateway;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GiphyGatewayTests {
    private GiphyGateway mRepository;
    
    @Before
    public void setup() {
        mRepository = new GiphyGateway(ClientManager.getClient());
    }

    @Test
    public void suggestions() throws Exception {
        List<String> suggestions = mRepository.getSuggestions("ca");

        assertEquals(8, suggestions.size());
    }
    
    @Test
    public void search() throws Exception {
        Page gifPage = mRepository.search("beer", 0);
        
        assertEquals(25, gifPage.getEntries().size());
        assertTrue(gifPage.total() > 1000);
    }
}
