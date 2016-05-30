package net.cachapa.protiumdemo.recycler;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.cachapa.data.model.Gif;

import java.util.List;

public class GifListBinder {
    @BindingAdapter("gif_list")
    public static void bindList(RecyclerView view, List<Gif> list) {
        ((GifAdapter) view.getAdapter()).setGifs(list);
    }
    
    @BindingAdapter("gif_item")
    public static void bindImage(ImageView view, Gif gif) {
        Picasso.with(view.getContext())
                .load(gif.getPreviewUrl())
                .into(view);
    }
}