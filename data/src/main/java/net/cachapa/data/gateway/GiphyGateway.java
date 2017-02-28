package net.cachapa.data.gateway;

import net.cachapa.data.model.Page;
import net.cachapa.data.model.Suggestions;
import net.cachapa.protium.RestGateway;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;

public class GiphyGateway extends RestGateway {
    private static final String USER_AGENT = "android:net.cachapa.protiumdemo";
    private static final String SUGGESTIONS_FORMAT = "http://giphy.com/ajax/tags/search/?q=%s";
    private static final String SEARCH_FORMAT = "http://api.giphy.com/v1/gifs/search?api_key=%s&q=%s&offset=%d";
    private static final String API_KEY = "dc6zaTOxFJmzC";

    public GiphyGateway(OkHttpClient client) {
        super(client, USER_AGENT);
    }

    public List<String> getSuggestions(String query) throws IOException {
        String url = String.format(Locale.US, SUGGESTIONS_FORMAT, query);
        Suggestions suggestions = fetch(Suggestions.class, url);
        return suggestions.getSuggestions();
    }

    public Page search(String query, int skip) throws IOException {
        String url = String.format(Locale.US, SEARCH_FORMAT, API_KEY, query, skip);
        return fetch(Page.class, url);
    }
}
