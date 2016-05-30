package com.codingbuffalo.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    @SerializedName("data")
    private List<Gif>    entries;
    private Pagination pagination;
    
    public List<Gif> getEntries() {
        return entries;
    }
    
    public int getTotalCount() {
        return pagination.total_count;
    }
    
    private class Pagination {
        private int total_count;
    }
}
