<script setup lang="ts">
import { ref } from 'vue'
const loading = ref(true)
setTimeout(() => {
  loading.value = false
}, 500)
</script>

<template>
  <div>
    <a-typography-title :level="3"> 成绩管理学生端（仅学生可见） </a-typography-title>
    <a-card title="毕业论文成绩" style="width: 100%" :loading="loading">
      <p>80分</p>
    </a-card>
  </div>
</template>

<style scoped>
/* Component-specific styles */
</style>
