package com.codingbuffalo.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page<T> {
    @SerializedName("data")
    private List<T>    entries;
    private Pagination pagination;
    
    public List<T> getEntries() {
        return entries;
    }
    
    public int getTotalCount() {
        return pagination.total_count;
    }
    
    private class Pagination {
        private int total_count;
    }
}
