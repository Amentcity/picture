package com.picture.model.dto.picture;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PictureUploadRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4146669147001723537L;
    /**
     * 图片 id（用于修改）
     */
    private Long id;

}
