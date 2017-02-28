package net.cachapa.protiumdemo;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import net.cachapa.data.gateway.ClientManager;
import net.cachapa.data.interactor.SuggestionsInteractor;
import net.cachapa.data.model.Gif;
import net.cachapa.data.gateway.GiphyGateway;
import net.cachapa.protiumdemo.fragment.ResultsFragment;
import net.cachapa.protiumdemo.fragment.ImageFragment;
import net.cachapa.protiumdemo.recycler.SuggestionsAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String DEFAULT_QUERY = "beer";
    
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            showResults(DEFAULT_QUERY);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);

        searchMenuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                return true;
            }
        });

        SuggestionsInteractor interactor = new SuggestionsInteractor(new GiphyGateway(ClientManager.getClient()));

        AutoCompleteTextView autocomplete = (AutoCompleteTextView) MenuItemCompat.getActionView(searchMenuItem).findViewById(R.id.autocomplete);
        autocomplete.setThreshold(1);
        autocomplete.setOnItemClickListener(MainActivity.this);
        autocomplete.setAdapter(new SuggestionsAdapter(interactor));

        return true;
    }

    public void openGif(Gif gif) {
        ImageFragment fragment = ImageFragment.create(gif);
        fragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String query = (String) parent.getAdapter().getItem(position);
        searchMenuItem.collapseActionView();
        showResults(query);
    }
    
    private void showResults(String query) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ResultsFragment.create(query))
                .commit();
    }
}
