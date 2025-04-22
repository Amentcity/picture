package com.picture.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3940068709032538343L;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", required = true)
    private String userAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String userPassword;
}
