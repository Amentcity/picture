package com.picture.model.dto.file;

import lombok.Data;

@Data
public class UploadPictureResult {

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 文件体积
     */
    private Long size;

    /**
     * 图片宽度
     */
    private int width;

    /**
     * 图片高度
     */
    private int height;

    /**
     * 图片宽高比
     */
    private Double scale;

    /**
     * 图片格式
     */
    private String format;

}
