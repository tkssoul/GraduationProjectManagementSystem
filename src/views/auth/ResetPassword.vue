<script setup lang="ts">
import { reactive, computed, onBeforeMount } from 'vue'
import { useUsernameStore } from '@/stores/username'
import { LockOutlined } from '@ant-design/icons-vue'

const usernameStore = useUsernameStore()

interface FormState {
  username: string
  password1: string
  password2: string
}
const formState = reactive<FormState>({
  username: '',
  password1: '',
  password2: '',
})

onBeforeMount(() => {
  if (usernameStore.username) {
    formState.username = usernameStore.username
  } else {
    // 如果没有用户名，重定向到输入用户名的页面
    usernameStore.setUsername('')
    window.location.href = '/login/reset-validate'
  }
})

const disabled = computed(() => {
  return !(formState.username && formState.password1 && formState.password2)
})
</script>

<template>
  <div ref="signFormRef" class="reset-container form-container half-width">
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">登录</a-typography-title>
      <a-form :model="formState" name="normal_login" class="login-form">
        <a-form-item
          label="输入新密码"
          name="password1"
          :rules="[{ required: true, message: '请输入账户！' }]"
        >
          <a-input-password v-model:value="formState.password1">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          label="请确认密码"
          name="password2"
          :rules="[{ required: true, message: '请输入密码！' }]"
        >
          <a-input-password v-model:value="formState.password2">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button
            :disabled="disabled"
            type="primary"
            html-type="submit"
            class="login-form-button"
            style="width: 100%"
          >
            重置密码
          </a-button>
        </a-form-item>
      </a-form>
    </a-flex>
  </div>
</template>

<style scoped>
.reset-container {
  padding: 20px;
  transform: translateX(-100%);
  height: 100%;
}
</style>
