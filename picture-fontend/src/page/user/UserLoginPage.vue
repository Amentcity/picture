<script setup lang="ts">
import { reactive } from 'vue'
import { userLogin } from '@/api/userController.ts'
import { useUserLoginStore } from '@/stores/useUserLoginStore.ts'
import { message } from 'ant-design-vue'
import router from '@/router'

const formState = reactive<API.userLoginParams>({
  userAccount: '',
  userPassword: '',
})

const loginUserStore = useUserLoginStore()

const onFinish = async (values: API.userLoginParams) => {
  const res = await userLogin(values)
  console.log(res.data)
  if (res.data.code === 200 && res.data.data) {
    await loginUserStore.fetchLoginUser()
    message.success('登录成功')
    await router.replace({
      path: '/',
    })
  } else {
    message.error('登录失败' + res.data.message)
  }
}

const onFinishFailed = (errorInfo: string) => {
  console.log('Failed:', errorInfo)
}
</script>

<template>
  <div id="user-login">
    <h2 class="title">拾光图库</h2>
    <a-form
      :model="formState"
      name="basic"
      autocomplete="off"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item name="userAccount" :rules="[{ required: true, message: '请输入您的账号' }]">
        <a-input placeholder="请输入账号名" v-model:value="formState.userAccount" />
      </a-form-item>

      <a-form-item name="userPassword" :rules="[{ required: true, message: '请输入您的密码' }]">
        <a-input-password placeholder="请输入密码" v-model:value="formState.userPassword" />
      </a-form-item>
      <div class="tips">
        没有账号？<RouterLink to="/user/register">去注册</RouterLink>
      </div>
      <a-form-item>
        <a-button type="primary" html-type="submit" style="width: 100%">登录</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
#user-login {
  max-width: 360px;
  margin: 0 auto;
}

.title {
  text-align: center;
  margin-bottom: 16px;
}

.tips {
  margin-bottom: 16px;
  color: #bbb;
  font-size: 16px;
  text-align: right;
}
</style>
