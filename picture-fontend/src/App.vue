<script setup lang="ts">
import BasicLayout from '@/layouts/BasicLayout.vue'
import { useUserLoginStore } from '@/stores/useUserLoginStore.ts'
import { healthUsingGet } from '@/api/mainController.ts'
const userLoginStore = useUserLoginStore()
userLoginStore.fetchLoginUser();
healthUsingGet().then((res) => {
  console.log(res)
})
</script>

<template>
  <div id="app">
    <BasicLayout/>
  </div>
</template>

<style scoped></style>
