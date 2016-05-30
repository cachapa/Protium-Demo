package net.cachapa.protiumdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.cachapa.data.model.Gif;
import net.cachapa.protiumdemo.fragment.GridFragment;
import net.cachapa.protiumdemo.fragment.ImageFragment;

public class MainActivity extends AppCompatActivity {
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
