<script setup lang="ts">
import { reactive, computed, onBeforeMount } from 'vue'
import { useUsernameStore } from '@/stores/username'
import { useJWTStore } from '@/stores/jwtToken'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'

const usernameStore = useUsernameStore()
const jwtStore = useJWTStore()

interface FormState {
  username: string
  password: string
  remember: boolean
}
const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
})

onBeforeMount(() => {
  formState.username = usernameStore.username
})

const disabled = computed(() => {
  return !(formState.username && formState.password)
})

function handleSubmit() {
  if (formState.remember) {
    jwtStore.refreshToken('这是测试token')
  }
  console.log('成功保持登录态，现在的token是:', jwtStore.token)
}
</script>

<template>
  <div ref="signFormRef" class="sign-in-container form-container half-width">
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">登录</a-typography-title>
      <a-form :model="formState" name="normal_login" class="login-form" @finish="handleSubmit">
        <a-form-item
          label="账户"
          name="username"
          @change="usernameStore.setUsername(formState.username)"
          :rules="[{ required: true, message: '请输入账户！' }]"
        >
          <a-input v-model:value="formState.username">
            <template #prefix>
              <UserOutlined class="site-form-item-icon" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入密码！' }]"
        >
          <a-input-password v-model:value="formState.password">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-form-item name="remember" no-style>
            <a-checkbox v-model:checked="formState.remember">记住我</a-checkbox>
          </a-form-item>
        </a-form-item>

        <a-form-item>
          <a-button
            :disabled="disabled"
            type="primary"
            html-type="submit"
            class="login-form-button"
            style="width: 100%"
          >
            登录
          </a-button>
        </a-form-item>
      </a-form>
    </a-flex>
  </div>
</template>

<style scoped>
.sign-in-container {
  padding: 20px;
  position: relative;
  right: 0;
  height: 100%;
}
</style>
