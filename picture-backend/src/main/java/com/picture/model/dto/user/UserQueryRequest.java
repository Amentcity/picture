package com.picture.model.dto.user;

import com.picture.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 5175248911521311920L;

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userName;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String userAccount;

    /**
     * 简介
     */
    @Schema(description = "简介")
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    @Schema(description = "用户角色")
    private String userRole;

}

