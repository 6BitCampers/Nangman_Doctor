package com._6bitcampers.nangman_doctor.servingPackage.jangwoo.QnA.dto;

import java.io.Serializable;
import java.util.List;

public class pagingInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int pageIndex;
    private final int pageSize;
    private final int totalCount;
    private final List<T> data;

    public pagingInfo(int pageIndex, int pageSize, int totalCount, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotalPage() {
        return (int)Math.ceil((double) totalCount/pageSize);
    }
}
