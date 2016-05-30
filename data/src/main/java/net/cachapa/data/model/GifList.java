package net.cachapa.data.model;

import android.databinding.BaseObservable;

import java.util.ArrayList;
import java.util.List;

public class GifList extends BaseObservable {
    private List<Gif> mList;
    private int mTotalCount;
    
    public GifList() {
        mList = new ArrayList<>();
        mTotalCount = 0;
    }
    
    public List<Gif> getList() {
        return mList;
    }
    
    public int getTotalCount() {
        return mTotalCount;
    }

    public void setData(List<Gif> gifs, int totalCount) {
        mList.addAll(gifs);
        mTotalCount = totalCount;
        
        notifyChange();
    }
}
