package com.thagedy.footballclub.common.pojo;

import java.util.List;

/**
 * Created by Tianqi Cui on 2016/9/9.
 * 通用分页响应数据结构
 */
public class PageResult {
    public PageResult(){}

    public PageResult(List<?> data, Long total) {
        this.data = data;
        this.total = total;
    }

    private List<?> data;

    private Long total;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
