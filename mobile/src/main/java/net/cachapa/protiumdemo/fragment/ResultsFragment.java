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

import net.cachapa.data.gateway.ClientManager;
import net.cachapa.data.gateway.GiphyGateway;
import net.cachapa.data.interactor.SearchInteractor;
import net.cachapa.protiumdemo.MainActivity;
import net.cachapa.protiumdemo.R;
import net.cachapa.protiumdemo.databinding.FragmentGridBinding;
import net.cachapa.protiumdemo.recycler.GifAdapter;

import java.util.concurrent.Executors;

public class ResultsFragment extends Fragment {
    private static final String KEY_QUERY = "query";

    private GiphyGateway gateway = new GiphyGateway(ClientManager.getClient());
    private SearchInteractor interactor;

    public static ResultsFragment create(String query) {
        Bundle args = new Bundle();
        args.putString(KEY_QUERY, query);

        //noinspection deprecation
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @deprecated Use {@link #create(String)} instead
     */
    @Deprecated
    public ResultsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String query = getArguments().getString(KEY_QUERY);
        interactor = new SearchInteractor(Executors.newCachedThreadPool(), gateway, query);
        interactor.fetch();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGridBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grid, container, false);

        // Configure RecyclerView
        binding.recyclerView.setAdapter(new GifAdapter((MainActivity) getActivity()));
        binding.recyclerView.addOnScrollListener(new PagingScrollListener());

        // Bind data
        binding.setObservable(interactor.getObservable());

        return binding.getRoot();
    }

    private class PagingScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            if (lastItem > recyclerView.getAdapter().getItemCount() - 10) {
                interactor.fetch();
            }
        }
    }
}
