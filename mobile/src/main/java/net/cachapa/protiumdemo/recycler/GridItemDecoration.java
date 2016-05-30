package net.cachapa.protiumdemo.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int               margin;
    private GridLayoutManager layoutManager;
    
    public GridItemDecoration(Context context, GridLayoutManager layoutManager, int marginRes) {
        this.layoutManager = layoutManager;
        margin = (int) context.getResources().getDimension(marginRes);
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int columnCount = layoutManager.getSpanCount();
        
        if (position >= columnCount) {
            outRect.top = margin;
        }
        
        if (position % columnCount != 0) {
            outRect.left = margin;
        }
    }
}