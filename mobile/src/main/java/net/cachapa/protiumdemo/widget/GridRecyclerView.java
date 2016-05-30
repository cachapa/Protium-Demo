package net.cachapa.protiumdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import net.cachapa.protiumdemo.R;
import net.cachapa.protiumdemo.recycler.GridItemDecoration;

public class GridRecyclerView extends RecyclerView {
    private int itemSize;
    
    public GridRecyclerView(Context context) {
        super(context);
        init();
    }
    
    public GridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public GridRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    
    private void init() {
        itemSize = (int) getContext().getResources().getDimension(R.dimen.grid_item_size);
        
        setLayoutManager(new GridLayoutManager(getContext(), 1));
        addItemDecoration(new GridItemDecoration(getContext(), getLayoutManager(), R.dimen.grid_margin));
    }
    
    @Override
    public GridLayoutManager getLayoutManager() {
        return (GridLayoutManager) super.getLayoutManager();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    
        int columnCount = (int) Math.ceil(w / itemSize);
        getLayoutManager().setSpanCount(columnCount);
        invalidateItemDecorations();
    }
}
