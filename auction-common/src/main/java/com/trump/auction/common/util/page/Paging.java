package com.trump.auction.common.util.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息
 */
public class Paging<T> implements Serializable {

    private static final long serialVersionUID = -2839262213460862747L;

    @Getter
    @Setter
    private int pageNum;

    @Getter
    @Setter
    private int pageSize;

    @Getter
    @Setter
    private int size;

    @Getter
    @Setter
    private String orderBy;

    @Getter
    @Setter
    private int startRow;

    @Getter
    @Setter
    private int endRow;

    @Getter
    @Setter
    private long total;

    @Getter
    @Setter
    private int pages;

    @Getter
    @Setter
    private List<T> list;

    @Getter
    @Setter
    private int firstPage;

    @Getter
    @Setter
    private int prePage;

    @Getter
    @Setter
    private int nextPage;

    @Getter
    @Setter
    private int lastPage;

    @Getter
    private boolean isFirstPage;

    @Getter
    private boolean isLastPage;

    @Getter
    @Setter
    private boolean hasPreviousPage;

    @Getter
    @Setter
    private boolean hasNextPage;

    @Getter
    @Setter
    private int navigatePages;

    @Getter
    @Setter
    private int[] navigatepageNums;

    public Paging orderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Paging() {
    }

    public Paging(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Paging(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Paging(int pageNum, int pageSize, int total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Paging(int pageNum, int pageSize, int total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }

    public boolean isIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Paging{");
        sb.append("pageNum=").append(this.pageNum);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", size=").append(this.size);
        sb.append(", startRow=").append(this.startRow);
        sb.append(", endRow=").append(this.endRow);
        sb.append(", total=").append(this.total);
        sb.append(", pages=").append(this.pages);
        sb.append(", list=").append(this.list);
        sb.append(", firstPage=").append(this.firstPage);
        sb.append(", prePage=").append(this.prePage);
        sb.append(", nextPage=").append(this.nextPage);
        sb.append(", lastPage=").append(this.lastPage);
        sb.append(", isFirstPage=").append(this.isFirstPage);
        sb.append(", isLastPage=").append(this.isLastPage);
        sb.append(", hasPreviousPage=").append(this.hasPreviousPage);
        sb.append(", hasNextPage=").append(this.hasNextPage);
        sb.append(", navigatePages=").append(this.navigatePages);
        sb.append(", navigatepageNums=");
        if(this.navigatepageNums == null) {
            sb.append("null");
        } else {
            sb.append('[');

            for(int i = 0; i < this.navigatepageNums.length; ++i) {
                sb.append(i == 0?"":", ").append(this.navigatepageNums[i]);
            }

            sb.append(']');
        }

        sb.append('}');
        return sb.toString();
    }

}
