package com.codingbuffalo.protiumdemo.recycler;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingbuffalo.data.model.Gif;
import com.codingbuffalo.protiumdemo.MainActivity;
import com.codingbuffalo.protiumdemo.R;
import com.codingbuffalo.protiumdemo.databinding.ItemGifBinding;

import java.util.LinkedList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ViewHolder> {
    private MainActivity activity;
    private List<Gif>    gifs;
    
    public GifAdapter(MainActivity activity) {
        this.activity = activity;
        gifs = new LinkedList<>();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gif, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gif gif = gifs.get(position);
        holder.binder.setGif(gif);
        holder.binder.executePendingBindings(); // Required to avoid the previous image showing for one frame on fast scrolls
    }
    
    @Override
    public int getItemCount() {
        return gifs.size();
    }
    
    public void setGifs(List<Gif> gifs) {
        this.gifs.clear();
        this.gifs.addAll(gifs);
        notifyDataSetChanged();
    }
    
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemGifBinding binder;
        
        public ViewHolder(View itemView) {
            super(itemView);
            binder = DataBindingUtil.bind(itemView);
            
            itemView.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View v) {
            Gif gif = gifs.get(getAdapterPosition());
            activity.openGif(gif);
        }
    }
}
