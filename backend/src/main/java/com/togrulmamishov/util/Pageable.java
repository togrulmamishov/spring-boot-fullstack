package com.togrulmamishov.util;

public class Pageable {

    private int page = 1;
    private int size = 500;

    public Pageable() {
    }

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page > 0)
            this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size > 0)
            this.size = size;
    }

    public int getOffset() {
        return (this.page - 1) * (this.size);
    }
}
