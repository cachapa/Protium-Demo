package net.cachapa.data.model;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private List<Gif> data = new ArrayList<>();
    private Pagination pagination = new Pagination();

    public List<Gif> getEntries() {
        return data;
    }

    public int size() {
        return data.size();
    }

    public int total() {
        return pagination.total_count;
    }
    
    public void stitch(Page page) {
        data.addAll(page.getEntries());
        pagination.total_count = page.total();
    }

    private class Pagination {
        private int total_count;
    }
}
