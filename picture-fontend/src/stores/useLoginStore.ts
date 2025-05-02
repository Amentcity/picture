import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getLoginUser } from '@/api/userController.ts'

/**
 * 存储登录用户信息的状态
 */
export const useLoginStore = defineStore('loginUser', () => {
  const loginUser = ref<API.LoginUserVO>({
    userName: '未登录',
  })

  /**
   * 设置登录用户
   * @param newLoginUser
   */
  function setLoginUser(newLoginUser: API.LoginUserVO) {
    loginUser.value = newLoginUser
  }

  /**
   * 运城获取登录用户信息
   */
  async function fetchLoginUser() {
    const res = await getLoginUser()
    if (res.data.code === 200 && res.data.data) {
      loginUser.value = res.data.data
    }
  }

  return { loginUser, setLoginUser, fetchLoginUser }
})
