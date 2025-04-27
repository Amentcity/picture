package com.picture.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5335744188842818874L;

    /**
     * id
     */
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}
