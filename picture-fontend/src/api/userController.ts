// @ts-ignore
/* eslint-disable */
import request from '@/request'

/** 创建用户 POST /user/add */
export async function addUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.addUserParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLong>('/user/add', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 删除用户 POST /user/delete */
export async function deleteUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteUserParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseBoolean>('/user/delete', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 据 id 获取用户（仅管理员） GET /user/get */
export async function getUserById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserByIdParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseUser>('/user/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 获取当前登录用户 GET /user/get/login */
export async function getLoginUser(options?: { [key: string]: any }) {
  return request<API.BaseResponseLoginUserVO>('/user/get/login', {
    method: 'GET',
    ...(options || {}),
  })
}

/** 根据 id 获取包装类 GET /user/get/vo */
export async function getUserVoById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserVOByIdParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseUserVO>('/user/get/vo', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 分页获取用户封装列表（仅管理员） POST /user/list/page/vo */
export async function listUserVoByPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listUserVOByPageParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponsePageUserVO>('/user/list/page/vo', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 用户登录 POST /user/login */
export async function userLogin(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.userLoginParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLoginUserVO>('/user/login', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 用户注册 POST /user/register */
export async function userRegister(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.userRegisterParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseLong>('/user/register', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 更新用户 POST /user/update */
export async function updateUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateUserParams,
  options?: { [key: string]: any }
) {
  return request<API.BaseResponseBoolean>('/user/update', {
    method: 'POST',
    params: {
      ...params,
    },
    ...(options || {}),
  })
}

/** 用户注销 POST /user/userSignOut */
export async function userSignOut(options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/user/userSignOut', {
    method: 'POST',
    ...(options || {}),
  })
}
