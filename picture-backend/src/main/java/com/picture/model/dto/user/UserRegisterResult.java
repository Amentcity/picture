package com.picture.model.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterResult implements Serializable {

    private static final long serialVersionUID = -2866172476282543825L;

    /**
     * 账号
     */
    @ApiModelProperty(value = "用户账号", required = true)
    private String userAccount;

    /**
     * 密码
     */
    @ApiModelProperty(value = "用户密码", required = true)
    private String userPassword;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码", required = true)
    private String checkPassword;
}
