package net.cachapa.data.repository;

import net.cachapa.data.model.Page;

import net.cachapa.protium.RestRepository;

import java.io.IOException;
import java.util.Locale;

public class GiphyRepository extends RestRepository {
    private static final String URL_FORMAT = "http://api.giphy.com/v1/gifs/search?api_key=%s&q=%s&offset=%d";
    private static final String API_KEY = "dc6zaTOxFJmzC";
    
    public Page search(String query, int skip) throws IOException {
        String url = String.format(Locale.US, URL_FORMAT, API_KEY, query, skip);
        return fetch(Page.class, url);
    }
}
