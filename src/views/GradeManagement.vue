<script setup lang="ts">
import { defineAsyncComponent, onBeforeMount } from 'vue'
import { useUserStore } from '@/stores/user' // 引入 useUserStore
const userStore = useUserStore()

let componentByRole: object
onBeforeMount(() => {
  if (userStore.user.role === 'student') {
    componentByRole = defineAsyncComponent(() => import('@/components/StudentGradeView.vue'))
  } else if (userStore.user.role === 'teacher') {
    componentByRole = defineAsyncComponent(() => import('@/components/TeacherGradeView.vue'))
  }
})
</script>

<template>
  <div class="info-container">
    <componentByRole />
  </div>
</template>

<style scoped>
.info-container {
  width: 100%;
  height: calc(100vh - 64px);
  padding: 20px;
  overflow: auto;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
