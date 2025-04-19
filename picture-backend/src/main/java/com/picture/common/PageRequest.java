package com.picture.common;

import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private Integer currentPage;

    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序规则（默认降序）
     */
    private String sortOrder;

    public PageRequest() {
        currentPage = 1;
        pageSize = 10;
        sortOrder = "desc";
    }
}
