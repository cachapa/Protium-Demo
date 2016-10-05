package net.cachapa.data.interactor;

import android.widget.Filter;

import net.cachapa.data.gateway.GiphyGateway;

import java.io.IOException;
import java.util.List;

public class SuggestionsInteractor extends Filter {
    private GiphyGateway gateway;
    private OnSuggestionsAvailableListener listener;

    public SuggestionsInteractor(GiphyGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        if (constraint == null) {
            return null;
        }

        List<String> suggestions = null;
        try {
            suggestions = gateway.getSuggestions(constraint.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        FilterResults results = new FilterResults();
        results.values = suggestions;
        results.count = suggestions.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        if (results != null) {
            listener.onSuggestionsAvailable((List<String>) results.values);
        }
    }

    public void setListener(OnSuggestionsAvailableListener listener) {
        this.listener = listener;
    }

    public interface OnSuggestionsAvailableListener {
        void onSuggestionsAvailable(List<String> suggestions);
    }
}
