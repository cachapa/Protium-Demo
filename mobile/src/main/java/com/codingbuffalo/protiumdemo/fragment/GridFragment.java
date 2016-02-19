package com.codingbuffalo.protiumdemo.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingbuffalo.data.GifList;
import com.codingbuffalo.data.SearchInteractor;
import com.codingbuffalo.data.protium.StateHolder;
import com.codingbuffalo.protiumdemo.MainActivity;
import com.codingbuffalo.protiumdemo.R;
import com.codingbuffalo.protiumdemo.databinding.FragmentGridBinding;
import com.codingbuffalo.protiumdemo.recycler.GifAdapter;
import com.codingbuffalo.protiumdemo.registry.InteractorRegistry;

public class GridFragment extends Fragment {
    private SearchInteractor mSearchInteractor;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSearchInteractor = InteractorRegistry.getSearchInteractor("beer");
        GifList     gifs        = mSearchInteractor.getGifs();
        StateHolder stateHolder = mSearchInteractor.getStateHolder();
        
        FragmentGridBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grid, container, false);
        binding.setGifs(gifs);
        binding.setStateHolder(stateHolder);
        
        // Configure RecyclerView
        binding.recyclerView.setAdapter(new GifAdapter((MainActivity) getActivity()));
        binding.recyclerView.addOnScrollListener(new PagingScrollListener());
        
        return binding.getRoot();
    }
    
    private class PagingScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            
            if (lastItem > recyclerView.getAdapter().getItemCount() - 10) {
                mSearchInteractor.fetchNextPage();
            }
        }
    }
}
