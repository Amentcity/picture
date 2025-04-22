package com.picture.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = -5335744188842818874L;

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    private int id;
}
