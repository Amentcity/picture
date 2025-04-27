<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
</script>

<template>
  <div id="app">
    <a-config-provider :locale="zhCN">
      <BasicLayout/>
    </a-config-provider>
  </div>
</template>

<style scoped></style>
