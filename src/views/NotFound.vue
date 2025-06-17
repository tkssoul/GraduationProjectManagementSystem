<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useSiderStore } from '@/stores/sider'
import { Result, Button } from 'ant-design-vue'

const router = useRouter()
const siderStore = useSiderStore()

const goToHome = () => {
  siderStore.reset()
  router.push('/')
}
</script>

<template>
  <a-flex class="not-found-container" align="center" justify="center">
    <Result status="404" title="404" sub-title="你要访问的页面不存在">
      <template #extra>
        <Button type="primary" @click="goToHome"> 返回首页 </Button>
      </template>
    </Result>
  </a-flex>
</template>

<style scoped>
.not-found-container {
  width: 100vw;
  height: 100vh;
}
</style>
