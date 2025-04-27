package com.picture.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.picture.annotation.AuthCheck;
import com.picture.common.BaseResponse;
import com.picture.common.DeleteRequest;
import com.picture.common.ResultUtils;
import com.picture.constant.UserConstant;
import com.picture.exception.BusinessException;
import com.picture.exception.ErrorCode;
import com.picture.exception.ThrowUtils;
import com.picture.model.dto.user.*;
import com.picture.model.entity.User;
import com.picture.model.vo.LoginUserVO;
import com.picture.model.vo.UserVO;
import com.picture.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
//@Tag(name = "用户模块")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public BaseResponse<Long> userRegister(UserRegisterResult userRegisterResult) {
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userRegisterResult), ErrorCode.PARAMS_ERROR);
        long result = userService.userRegister(userRegisterResult);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public BaseResponse<LoginUserVO> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userLoginRequest), ErrorCode.PARAMS_ERROR);
        LoginUserVO loginUserVO = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     *
     * @param request 请求信息
     * @return 当前登录用户
     */
    @GetMapping("/get/login")
    @Operation(summary = "获取当前登录用户")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.loginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户注销
     */
    @PostMapping("/userSignOut")
    @Operation(summary = "用户注销")
    public BaseResponse<Boolean> UserSignOut(HttpServletRequest request) {
        ThrowUtils.throwIf(ObjectUtil.isEmpty(request), ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogOut(request);
        return ResultUtils.success(result);

    }

    /**
     * 创建用户
     */
    @PostMapping("/add")
    @Operation(summary = "创建用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        final String DEFAULT_PASSWORD = "12345678";
        String encryptPassword = userService.getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 根据 id 获取用户（仅管理员）
     */
    @GetMapping("/get")
    @Operation(summary = "据 id 获取用户（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @Parameter(name = "id", description = "id", required = true)
    public BaseResponse<User> getUserById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     */
    @GetMapping("/get/vo")
    @Operation(summary = "根据 id 获取包装类")
    @Parameter(description = "id", name = "id", required = true)
    public BaseResponse<UserVO> getUserVOById(long id) {
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @Operation(summary = "删除用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    @Operation(summary = "更新用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 分页获取用户封装列表（仅管理员）
     *
     * @param userQueryRequest 查询请求参数
     */
    @PostMapping("/list/page/vo")
    @Operation(summary = "分页获取用户封装列表（仅管理员）")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long current = userQueryRequest.getCurrentPage();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

}

