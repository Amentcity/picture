package com.picture.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户注册")
public class UserRegisterResult implements Serializable {

    private static final long serialVersionUID = -2866172476282543825L;

    /**
     * 账号
     */
    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAccount;

    /**
     * 密码
     */
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userPassword;

    /**
     * 确认密码
     */
    @Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String checkPassword;
}
