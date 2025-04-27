package com.picture.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6885382787866971004L;
    /**
     * id
     */
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userName;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userAvatar;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    @Schema(description = "用户角色")
    private String userRole;

}
