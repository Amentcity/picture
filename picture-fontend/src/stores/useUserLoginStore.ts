import { ref } from 'vue'
import { defineStore } from 'pinia'

/**
 * 存储登录用户信息的状态
 */
export const useUserLoginStore = defineStore('loginUser', () => {
  const loginUser = ref<any>({
    userName: '未登录',
  })

  function setLoginUser(newLoginUser: any) {
    loginUser.value = newLoginUser
  }

  async function fetchLoginUser() {}

  return { loginUser, setLoginUser, fetchLoginUser }
})
