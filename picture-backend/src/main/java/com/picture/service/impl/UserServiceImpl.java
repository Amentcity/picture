package com.picture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.picture.common.ResultUtils;
import com.picture.constant.UserConstant;
import com.picture.exception.ErrorCode;
import com.picture.exception.ThrowUtils;
import com.picture.model.dto.user.UserLoginRequest;
import com.picture.model.dto.user.UserQueryRequest;
import com.picture.model.dto.user.UserRegisterResult;
import com.picture.model.entity.User;
import com.picture.model.enums.UserRoleEnum;
import com.picture.model.vo.LoginUserVO;
import com.picture.model.vo.UserVO;
import com.picture.service.UserService;
import com.picture.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Amentcity
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2025-04-21 11:11:39
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public long userRegister(UserRegisterResult userRegisterResult) {
        // 1.校验数据
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userRegisterResult), ErrorCode.PARAMS_ERROR, "参数为空");
        // 2.检查是否有相同的账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userRegisterResult.getUserAccount());
        Long count = baseMapper.selectCount(queryWrapper);
        ThrowUtils.throwIf(count > 0, ErrorCode.PARAMS_ERROR, "账号重复");
        // 3.密码加密
        String encryptPassword = getEncryptPassword(userRegisterResult.getCheckPassword());
        User user = new User();
        BeanUtils.copyProperties(userRegisterResult, user);
        user.setUserPassword(encryptPassword);
        user.setUserRole(UserRoleEnum.USER.getValue());
        // 4.插入到数据库中
        boolean saveResult = this.save(user);
        if (!saveResult) {
            ResultUtils.error(ErrorCode.SYSTEM_ERROR, "注册失败");
        }
        return user.getId();
    }

    @Override
    public LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        // 1.校验数据
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userLoginRequest), ErrorCode.PARAMS_ERROR, "参数为空");
        // 2. 查村数据库中的用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userLoginRequest.getUserAccount());
        queryWrapper.eq(User::getUserPassword, getEncryptPassword(userLoginRequest.getUserPassword()));
        User user = baseMapper.selectOne(queryWrapper);
        // 3. 不存在，抛异常
        log.info("user login failed, userAccount cannot match UserPassword");
        ThrowUtils.throwIf(ObjectUtil.isEmpty(user), ErrorCode.PARAMS_ERROR, "账号或密码错误");
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        // 4. 保存用户的登录态
        httpServletRequest.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, loginUserVO);
        return loginUserVO;
    }

    @Override
    public User loginUser(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        ThrowUtils.throwIf(ObjectUtil.isEmpty(currentUser) && currentUser.getId() == null, ErrorCode.NOT_LOGIN_ERROR);
        // 从数据库中查询（追求性能的话可以注释，直接返回上述结果）
        Long userId = currentUser.getId();
        currentUser = getById(userId);
        ThrowUtils.throwIf(ObjectUtil.isEmpty(currentUser), ErrorCode.NOT_LOGIN_ERROR);
        return currentUser;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtil.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO UserVO = new UserVO();
        BeanUtil.copyProperties(user, UserVO);
        return UserVO;
    }

    /**
     * 获取脱敏后的用户列表
     *
     * @param userList 用户信息列表
     * @return 脱敏后的用户列表
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public String getEncryptPassword(String password) {
        // 加盐，起混淆作用
        final String salt = "gallery";
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }

    @Override
    public boolean userLogOut(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userObj), ErrorCode.OPERATION_ERROR, "未登录");
        // 移除登录态
        httpServletRequest.removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(ObjectUtil.isEmpty(userQueryRequest), ErrorCode.OPERATION_ERROR, "请求参数为空");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNull(userQueryRequest.getId()),"id", userQueryRequest.getId());
        queryWrapper.eq(StrUtil.isBlank(userQueryRequest.getUserRole()),"user_role", userQueryRequest.getUserRole());
        queryWrapper.like(StrUtil.isBlank(userQueryRequest.getUserAccount()),"user_account", userQueryRequest.getUserAccount());
        queryWrapper.like(StrUtil.isBlank(userQueryRequest.getUserName()),"user_name", userQueryRequest.getUserName());
        queryWrapper.like(StrUtil.isBlank(userQueryRequest.getUserProfile()),"user_profile", userQueryRequest.getUserProfile());
        queryWrapper.orderBy(StrUtil.isNotEmpty(userQueryRequest.getSortField()),userQueryRequest.getSortOrder().equals("asc"),userQueryRequest.getSortField());
        return queryWrapper;
    }
}




