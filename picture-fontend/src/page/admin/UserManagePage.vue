<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { message } from 'ant-design-vue'
import { deleteUser, listUserVoByPage } from '@/api/userController.ts'
import dayjs from 'dayjs'

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
  },
  {
    title: '账号',
    dataIndex: 'userAccount',
  },
  {
    title: '用户名',
    dataIndex: 'userName',
  },
  {
    title: '头像',
    dataIndex: 'userAvatar',
  },
  {
    title: '简介',
    dataIndex: 'userProfile',
  },
  {
    title: '用户角色',
    dataIndex: 'userRole',
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
  },
  {
    title: '操作',
    key: 'action',
  },
]

type params = API.listUserVOByPageParams

const searchParams = reactive<params>({
  currentPage: 1,
  pageSize: 10,
})

const dataSource = ref<API.UserVO[]>([])

const total = ref(0)

const loading = ref<boolean>(false)

const queryData = async () => {
  loading.value = true
  const res = await listUserVoByPage({
    ...searchParams,
  })
  if (res.data.data) {
    loading.value = false
    dataSource.value = res.data.data.records ?? []
    total.value = Number(res.data.data.total ?? 0)
  } else {
    loading.value = false
    message.error('获取数据失败' + res.data.message)
  }
}

// 分页参数
const pagination = computed(() => {
  return {
    current: searchParams.currentPage ?? 1,
    pageSize: searchParams.pageSize ?? 10,
    total: total.value,
    showSizeChanger: true,
    showTotal: (total: number) => `共 ${total} 条`,
  }
})

// 表格变化处理
const handleTableChange = (page: params) => {
  searchParams.currentPage = page.currentPage
  searchParams.pageSize = page.pageSize
  queryData()
}

// 删除操作
const doDelete = async (id: number) => {
  const res = await deleteUser({ id })
  if (res.data.code === 200) {
    message.success('删除成功')
    await queryData()
  } else {
    message.error(res.data.message)
  }
}

// 获取数据
const doSearch = () => {
  // 重置页码
  searchParams.currentPage = 1
  queryData()
}

onMounted(() => {
  queryData()
})
</script>

<template>
  <div id="user-manage">
    <a-form layout="inline" :model="searchParams" @finish="doSearch">
      <a-form-item label="账号">
        <a-input allowClear v-model:value="searchParams.userAccount" placeholder="输入账号" />
      </a-form-item>
      <a-form-item label="用户名">
        <a-input allowClear v-model:value="searchParams.userName" placeholder="输入用户名" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">搜索</a-button>
      </a-form-item>
    </a-form>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'userAvatar'">
          <a-image :src="record.userAvatar" :width="120" />
        </template>
        <template v-else-if="column.dataIndex === 'userRole'">
          <div v-if="record.userRole === 'admin'">
            <a-tag color="green">管理员</a-tag>
          </div>
          <div v-else>
            <a-tag color="blue">普通用户</a-tag>
          </div>
        </template>
        <template v-else-if="column.dataIndex === 'createTime'">
          {{ dayjs(record.createTime).format('YYYY-MM-DD HH:mm:ss') }}
        </template>
        <template v-else-if="column.key === 'action'">
          <a-space>
            <a-button type="primary">编辑</a-button>
            <a-button danger @click="doDelete(record.id)">删除</a-button>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
</template>

<style scoped></style>
