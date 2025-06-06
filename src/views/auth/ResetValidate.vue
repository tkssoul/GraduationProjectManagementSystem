<script setup lang="ts">
import { reactive, computed, onBeforeMount } from 'vue'
import { UserOutlined } from '@ant-design/icons-vue'
import { useIdStore } from '@/stores/username'
import { useRouter } from 'vue-router'

const router = useRouter()
const usernameStore = useIdStore()

interface FormState {
  username?: number
}

const formState = reactive<FormState>({
  username: undefined,
})

onBeforeMount(() => {
  formState.username = usernameStore.Id
})

const disabled = computed(() => {
  return !formState.username
})
</script>

<template>
  <div class="validate-container form-container half-width">
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">重置密码</a-typography-title>
      <a-form :model="formState" name="normal_login" class="login-form">
        <a-form-item
          label="账户"
          name="username"
          @change="usernameStore.setUsername(formState.username)"
          :rules="[{ required: true, message: '请输入要找回密码的账户！' }]"
        >
          <a-input v-model:value="formState.username">
            <template #prefix>
              <UserOutlined class="site-form-item-icon" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item>
          <a-button
            :disabled="disabled"
            type="primary"
            class="login-form-button"
            style="width: 100%"
            @click="() => router.replace('/login/reset-password')"
          >
            下一步
          </a-button>
        </a-form-item>
      </a-form>
    </a-flex>
  </div>
</template>

<style scoped>
.validate-container {
  padding: 20px;
  height: 100%;
  position: relative;
  left: -50%;
}
</style>
