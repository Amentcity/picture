<script setup lang="ts">
import { reactive } from 'vue'
import { userRegister } from '@/api/userController.ts'
import { message } from 'ant-design-vue'
import router from '@/router'

const formState = reactive<API.userRegisterParams>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

// 密码验证规则
const validatePassword = async (_rule: string, value: string) => {
  if (!value) return Promise.reject('请输入您的密码')
  if (value.length < 6) return Promise.reject('密码长度不能少于6位')
  return Promise.resolve()
}

// 确认密码验证规则（修正版）
const validateCheckPassword = async (_rule: string, value: string) => {
  if (!value) return Promise.reject('请再次输入密码')
  // 确保获取的是最新的 password 值
  if (value !== formState.userPassword) {
    return Promise.reject('两次输入的密码不一致')
  }
  return Promise.resolve()
}

const rules = {
  userAccount: [{ required: true, message: '请输入您的账号' }],
  userPassword: [
    { required: true, validator: validatePassword, trigger: ['change', 'blur'] }
  ],
  checkPassword: [
    { required: true, message: '请再次输入密码' },
    {
      validator: validateCheckPassword,
      trigger: ['change', 'blur']
    }
  ]
}

const onFinish = async (values: API.userRegisterParams) => {
  // 提交前再次确认密码一致
  if (values.userPassword !== values.checkPassword) {
    message.error('两次输入的密码不一致')
    return
  }

  const res = await userRegister(values)
  if (res.data.code === 200 && res.data.data) {
    message.success('注册成功')
    await router.replace({
      path: '/user/login',
    })
  } else {
    message.error('注册失败' + res.data.message)
  }
}

const onFinishFailed = (errorInfo: never) => {
  console.log('Failed:', errorInfo)
}
</script>

<template>
  <div id="user-register">
    <h2 class="title">拾光图库</h2>
    <a-form
      :model="formState"
      name="register"
      autocomplete="off"
      @finish="onFinish"
      @finishFailed="onFinishFailed"
    >
      <a-form-item
        name="userAccount"
        :rules="rules.userAccount"
      >
        <a-input placeholder="请输入账号名" v-model:value="formState.userAccount" />
      </a-form-item>

      <a-form-item
        name="userPassword"
        :rules="rules.userPassword"
      >
        <a-input-password
          placeholder="请输入密码"
          v-model:value="formState.userPassword"
        />
      </a-form-item>

      <a-form-item
        name="checkPassword"
        :rules="rules.checkPassword"
      >
        <a-input-password
          placeholder="请确认密码"
          v-model:value="formState.checkPassword"
        />
      </a-form-item>

      <div class="tips">
        已有账号？
        <RouterLink to="/user/login">去登录</RouterLink>
      </div>

      <a-form-item>
        <a-button
          type="primary"
          html-type="submit"
          style="width: 100%"
        >
          注册
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped>
#user-register {
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
