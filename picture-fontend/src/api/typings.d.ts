declare namespace API {
  type addUserParams = {
    /** 用户昵称 */
    userName?: string
    /** 账号 */
    userAccount: string
    /** 用户头像 */
    userAvatar?: string
    /** 用户简介 */
    userProfile: string
    /** 用户角色 */
    userRole: string
  }

  type BaseResponseBoolean = {
    code?: number
    data?: boolean
    message?: string
  }

  type BaseResponseLoginUserVO = {
    code?: number
    data?: LoginUserVO
    message?: string
  }

  type BaseResponseLong = {
    code?: number
    data?: number
    message?: string
  }

  type BaseResponsePageUserVO = {
    code?: number
    data?: PageUserVO
    message?: string
  }

  type BaseResponseUser = {
    code?: number
    data?: User
    message?: string
  }

  type BaseResponseUserVO = {
    code?: number
    data?: UserVO
    message?: string
  }

  type deleteUserParams = {
    /** id */
    id: number
  }

  type getUserByIdParams = {
    /** id */
    id: number
  }

  type getUserVOByIdParams = {
    /** id */
    id: number
  }

  type listUserVOByPageParams = {
    /** id */
    id?: number
    /** 用户昵称 */
    userName?: string
    /** 账号 */
    userAccount?: string
    /** 简介 */
    userProfile?: string
    /** 用户角色 */
    userRole?: string
    /** 当前页号 */
    currentPage?: number
    /** 页面大小 */
    pageSize?: number
    /** 排序字段 */
    sortField?: string
    /** 排序规则 */
    sortOrder?: string
  }

  type LoginUserVO = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    vipExpireTime?: string
    vipCode?: string
    vipNumber?: number
    shareCode?: string
    inviteUser?: number
    editTime?: string
    createTime?: string
    updateTime?: string
  }

  type OrderItem = {
    column?: string
    asc?: boolean
  }

  type PageUserVO = {
    records?: UserVO[]
    total?: number
    size?: number
    current?: number
    orders?: OrderItem[]
    optimizeCountSql?: PageUserVO
    searchCount?: PageUserVO
    optimizeJoinOfCountSql?: boolean
    maxLimit?: number
    countId?: string
    pages?: number
  }

  type updateUserParams = {
    /** id */
    id: number
    /** 用户昵称 */
    userName?: string
    /** 用户头像 */
    userAvatar?: string
    /** 简介 */
    userProfile?: string
    /** 用户角色 */
    userRole?: string
  }

  type User = {
    id?: number
    userAccount?: string
    userPassword?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    vipExpireTime?: string
    vipCode?: string
    vipNumber?: number
    shareCode?: string
    inviteUser?: number
    editTime?: string
    createTime?: string
    updateTime?: string
    isDelete?: number
  }

  type userLoginParams = {
    /** 账号 */
    userAccount: string
    /** 密码 */
    userPassword: string
  }

  type userRegisterParams = {
    /** 账号 */
    userAccount: string
    /** 用户密码 */
    userPassword: string
    /** 确认密码 */
    checkPassword: string
  }

  type UserVO = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    createTime?: string
  }
}
