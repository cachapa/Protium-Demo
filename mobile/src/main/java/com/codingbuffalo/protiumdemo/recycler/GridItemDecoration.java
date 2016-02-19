package com.codingbuffalo.protiumdemo.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mMargin;
    private final GridLayoutManager mLayoutManager;
    
    public GridItemDecoration(Context context, GridLayoutManager layoutManager, int marginRes) {
        mLayoutManager = layoutManager;
        mMargin = (int) context.getResources().getDimension(marginRes);
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int columnCount = mLayoutManager.getSpanCount();
        
        if (position >= columnCount) {
            outRect.top = mMargin;
        }
    
        if (position % columnCount != 0) {
            outRect.left = mMargin;
        }
    }
}