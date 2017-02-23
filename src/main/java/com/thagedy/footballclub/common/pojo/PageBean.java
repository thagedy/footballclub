package com.thagedy.footballclub.common.pojo;

/**
 * Created by Muzi Qiu on 2016/9/8.
 */
public class PageBean {
    public PageBean() {
    }

    public PageBean(Object keyword, int page, int count, String order, String order_type) {
        this.keyword = keyword;
        this.page = page;
        this.count = count;
        this.order = order;
        this.order_type = order_type;
    }

    private Object keyword;
    private int page=1;
    private int count;
    private String order;
    private String order_type;

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
