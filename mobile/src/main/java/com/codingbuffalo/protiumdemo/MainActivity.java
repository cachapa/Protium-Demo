package com.codingbuffalo.protiumdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codingbuffalo.data.model.Gif;
import com.codingbuffalo.data.interactor.SearchInteractor;
import com.codingbuffalo.protiumdemo.fragment.GridFragment;
import com.codingbuffalo.protiumdemo.fragment.ImageFragment;

public class MainActivity extends AppCompatActivity {
    private SearchInteractor mSearchInteractor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new GridFragment())
                    .commit();
        }
    }
    
    public void openGif(Gif gif) {
        ImageFragment fragment = ImageFragment.create(gif);
        fragment.show(getSupportFragmentManager(), null);
    }
}
