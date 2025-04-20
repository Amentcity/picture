<script setup lang="ts">
import GlobalHeader from '@/components/GlobalHeader.vue'
</script>

<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header"><GlobalHeader/></a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">拾光图库</a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
#basicLayout .header{
  padding-inline: 20px;
  background: white;
  color: unset;
  margin-bottom: 16px;
}
#basicLayout .content {
  padding: 20px;
  background: linear-gradient(to right, #fefefe, #ffffff);
  margin-bottom: 28px;
}

#basicLayout .footer {
  background-color: #f5f5f5;
  padding: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
