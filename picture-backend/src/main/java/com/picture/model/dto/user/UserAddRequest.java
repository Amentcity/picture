package com.picture.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "添加用户")
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = -8566487318829488167L;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userName;

    /**
     * 账号
     */
    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAccount;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String userAvatar;

    /**
     * 用户简介
     */
    @Schema(description = "用户简介", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    @Schema(description = "用户角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userRole;

}
