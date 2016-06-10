package net.cachapa.protiumdemo.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.data.model.GifsObservable;
import net.cachapa.data.model.StateHolder;
import net.cachapa.protiumdemo.MainActivity;
import net.cachapa.protiumdemo.R;
import net.cachapa.protiumdemo.databinding.FragmentGridBinding;
import net.cachapa.protiumdemo.recycler.GifAdapter;
import net.cachapa.protiumdemo.registry.InteractorRegistry;

public class GridFragment extends Fragment {
    private static final String KEY_QUERY = "query";
    
    private String query;
    private SearchInteractor searchInteractor;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(KEY_QUERY);
        } else {
            query = "beer";
        }
        
        searchInteractor = InteractorRegistry.getSearchInteractor();
        GifsObservable gifs = searchInteractor.getGifsObservable();
        StateHolder stateHolder = searchInteractor.getStateHolder();

        FragmentGridBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grid, container, false);
        binding.setGifs(gifs);
        binding.setStateHolder(stateHolder);

        // Configure RecyclerView
        binding.recyclerView.setAdapter(new GifAdapter((MainActivity) getActivity()));
        binding.recyclerView.addOnScrollListener(new PagingScrollListener());
        
        searchInteractor.setQuery(query);

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_QUERY, query);
        super.onSaveInstanceState(outState);
    }

    public void setQuery(String query) {
        this.query = query;
        searchInteractor.setQuery(query);
    }

    private class PagingScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            if (lastItem > recyclerView.getAdapter().getItemCount() - 10) {
                searchInteractor.fetchNextPage();
            }
        }
    }
}
