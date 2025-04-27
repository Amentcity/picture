<script setup lang="ts">
import { computed, h, ref } from 'vue'
import { HomeOutlined, LogoutOutlined } from '@ant-design/icons-vue'
import { type MenuProps, message } from 'ant-design-vue'
import { useRouter } from 'vue-router'
import { useUserLoginStore } from '@/stores/useUserLoginStore.ts'
import { userSignOut } from '@/api/userController.ts'

const userLoginStore = useUserLoginStore()

// 当前要高亮的菜单项
const current = ref<string[]>([])
// 菜单列表
const originItems = [
  {
    key: '/',
    icon: () => h(HomeOutlined),
    label: '主页',
    title: '主页',
  },
  {
    key: '/admin/userManage',
    label: '用户管理',
    title: '用户管理',
  },
]

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    // Null check
    if (!menu) return false

    // Type check for key
    if (typeof menu.key !== 'string') return false

    // Admin path check
    if (!menu.key.startsWith('/admin')) return true

    // Admin permission check
    const loginUser = userLoginStore.loginUser
    return loginUser?.userRole === 'admin'
  })
}

// 展示在菜单的路由数组
const items = computed<MenuProps['items']>(() => filterMenus(originItems))

const router = useRouter()

// 定义接口描述对象类型
interface MenuItem {
  key: string
  // 可以添加其他属性
}

// 修复后的函数
const doMenuClick = ({ key }: MenuItem) => {
  router.push({ path: key })
}

// 监听路由变化，更新高亮菜单项
router.afterEach((to) => {
  current.value = [to.path]
})

// 用户注销
const doLogout = async () => {
  const res = await userSignOut()
  console.log(res)
  if (res.data.code === 0) {
    userLoginStore.setLoginUser({
      userName: '未登录',
    })
    message.success('退出登录成功')
    await router.push('/user/login')
  } else {
    message.error('退出登录失败，' + res.data.message)
  }
}
</script>

<template>
  <div id="globalHeader">
    <a-row :wrap="false">
      <a-col flex="200px">
        <router-link to="/">
          <div class="title-bar">
            <img class="logo" src="../assets/logo.svg" alt="logo" />
            <div class="title">拾光图库</div>
          </div>
        </router-link>
      </a-col>
      <a-col flex="auto">
        <a-menu
          v-model:selectedKeys="current"
          mode="horizontal"
          :items="items"
          @click="doMenuClick"
        />
      </a-col>
      <a-col flex="120px">
        <div v-if="userLoginStore.loginUser.id">
          <a-dropdown>
            <ASpace>
              <a-avatar :src="userLoginStore.loginUser.userAvatar" />
              {{ userLoginStore.loginUser.userName ?? '无名' }}
            </ASpace>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="doLogout">
                  <LogoutOutlined />
                  退出登录
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped>
#globalHeader .title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: black;
  font-size: 18px;
  margin-left: 16px;
}

.logo {
  height: 48px;
}
</style>
