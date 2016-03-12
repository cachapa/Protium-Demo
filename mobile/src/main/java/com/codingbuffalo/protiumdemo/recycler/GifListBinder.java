package com.codingbuffalo.protiumdemo.recycler;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.codingbuffalo.data.model.Gif;
import com.squareup.picasso.Picasso;

public class GifListBinder {
    @BindingAdapter("gif_list")
    public static void bindList(RecyclerView view, ObservableList<Gif> list) {
        ((GifAdapter) view.getAdapter()).setGifs(list);
    }
    
    @BindingAdapter("gif_item")
    public static void bindImage(ImageView view, Gif gif) {
        Picasso.with(view.getContext())
                .load(gif.getPreviewUrl())
                .into(view);
    }
}