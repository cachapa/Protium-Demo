package com.codingbuffalo.data.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

public class GifList {
    private ObservableList<Gif> mList;
    private ObservableInt       mTotalCount;
    
    public GifList() {
        mList = new ObservableArrayList<>();
        mTotalCount = new ObservableInt(0);
    }
    
    public ObservableList<Gif> getList() {
        return mList;
    }
    
    public void setTotalCount(int totalCount) {
        mTotalCount.set(totalCount);
    }
    
    public ObservableInt getTotalCount() {
        return mTotalCount;
    }
}
