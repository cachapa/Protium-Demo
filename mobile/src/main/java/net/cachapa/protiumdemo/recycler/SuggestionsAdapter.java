package net.cachapa.protiumdemo.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import net.cachapa.data.interactor.SuggestionsInteractor;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsAdapter extends BaseAdapter implements ListAdapter, Filterable, SuggestionsInteractor.OnSuggestionsAvailableListener {
    private List<String> suggestions;
    private Filter filter;

    public SuggestionsAdapter(SuggestionsInteractor interactor) {
        this.suggestions = new ArrayList<>();
        this.filter = interactor;

        interactor.setListener(this);
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int position) {
        return suggestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        ((TextView) view.findViewById(android.R.id.text1)).setText(suggestions.get(position));
        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public void onSuggestionsAvailable(List<String> suggestions) {
        this.suggestions.clear();
        this.suggestions.addAll(suggestions);

        notifyDataSetChanged();
    }
}
