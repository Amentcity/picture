package com.picture.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "登录")
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3940068709032538343L;

    /**
     * 账号
     */
    @Schema(description = "账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAccount;

    /**
     * 密码
     */
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userPassword;
}
