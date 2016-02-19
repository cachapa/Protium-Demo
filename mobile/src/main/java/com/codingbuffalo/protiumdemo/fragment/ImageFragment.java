package com.codingbuffalo.protiumdemo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.codingbuffalo.data.Gif;
import com.codingbuffalo.protiumdemo.R;
import com.koushikdutta.ion.Ion;

public class ImageFragment extends DialogFragment {
    private static final String ARG_GIF_URL = "gif_url";
    
    public static ImageFragment create(Gif gif) {
        Bundle args = new Bundle();
        args.putString(ARG_GIF_URL, gif.getGifUrl());
        
        //noinspection deprecation
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    /**
     * @deprecated Use {@link #create(Gif)} instead
     */
    @Deprecated
    public ImageFragment() {
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_image, null, false);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        
        String gifUrl = getArguments().getString(ARG_GIF_URL);
        
        Ion.with(view.getContext())
                .load(gifUrl)
                .progressBar(progressBar)
                .intoImageView(imageView);
        
        return new AlertDialog
                .Builder(getContext(), R.style.ImageDialog)
                .setView(view)
                .create();
    }
}
