package com.picture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.picture.model.dto.user.UserLoginRequest;
import com.picture.model.dto.user.UserQueryRequest;
import com.picture.model.dto.user.UserRegisterResult;
import com.picture.model.entity.User;
import com.picture.model.vo.LoginUserVO;
import com.picture.model.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Amentcity
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-04-21 11:11:39
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userRegisterResult 用户注册信息
     * @return 新用户id
     */
    long userRegister(UserRegisterResult userRegisterResult);

    /**
     * 用户登录
     *
     * @param userLoginRequest   用户登录信息
     * @param httpServletRequest http请求信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest);

    /**
     * 获取当前登录用户
     *
     * @param httpServletRequest http信息
     * @return 用户登录信息
     */
    User loginUser(HttpServletRequest httpServletRequest);

    /**
     * 获得脱敏后的信息
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获得脱敏后的用户信息
     *
     * @param user 用户信息
     * @return 拓明后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获得脱敏后的用户信息列表
     *
     * @param userList 用户信息列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVOList(List<User> userList);


    /**
     * 获取加密后的密码
     *
     * @param password 用户密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String password);

    /**
     * 用户注销
     *
     * @param httpServletRequest http请求信息
     * @return null，用途退出登录
     */
    boolean userLogOut(HttpServletRequest httpServletRequest);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 查询条件
     * @return 获取查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 是否为管理员
     *
     * @param user 用户对象
     * @return 是否为管理员
     */
    boolean isAdmin(User user);
}
