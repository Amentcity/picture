package com.picture.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @ApiModelProperty(value = "当前页号")
    private Integer currentPage;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sortField;

    /**
     * 排序规则（默认降序）
     */
    @ApiModelProperty(value = "排序规则")
    private String sortOrder;

    public PageRequest() {
        currentPage = 1;
        pageSize = 10;
        sortOrder = "desc";
    }
}
