package com.picture.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @Schema(description = "当前页号")
    private Integer currentPage;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private Integer pageSize;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 排序规则（默认降序）
     */
    @Schema(description = "排序规则")
    private String sortOrder;

    public PageRequest() {
        currentPage = 1;
        pageSize = 10;
        sortOrder = "desc";
    }
}
