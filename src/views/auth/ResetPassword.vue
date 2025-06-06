<script setup lang="ts">
import { ref, reactive, computed, onBeforeMount } from 'vue'
import { useIdStore } from '@/stores/username'
import { useOverlayStore } from '@/stores/overlayPosition'
import { LockOutlined } from '@ant-design/icons-vue'
import { useRouter } from 'vue-router'
import { resetPassword } from '@/api/user'
import type { ResetPasswordParams } from '@/api/user'
import type { Rule } from 'ant-design-vue/es/form'
import type { FormInstance } from 'ant-design-vue'
import { message } from 'ant-design-vue'
const [messageApi, contextHolder] = message.useMessage()

const router = useRouter()

const usernameStore = useIdStore()
const overlayStore = useOverlayStore()

interface FormState {
  id?: number
  password1: string
  password2: string
}
const formState = reactive<FormState>({
  id: undefined,
  password1: '',
  password2: '',
})

const formRef = ref<FormInstance>()

const validatePass = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请输入密码！')
  } else {
    if (formState.password2 !== '') {
      formRef.value?.validateFields('password2')
    }
    return Promise.resolve()
  }
}

const validatePass2 = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请再次输入密码！')
  } else if (value !== formState.password1) {
    return Promise.reject('两次输入的密码不一致！')
  } else {
    return Promise.resolve()
  }
}

const rules: Record<string, Rule[]> = {
  password1: [
    {
      required: true,
      validator: validatePass,
      trigger: 'change',
    },
  ],
  password2: [
    {
      required: true,
      validator: validatePass2,
      trigger: 'change',
    },
  ],
}

onBeforeMount(() => {
  if (usernameStore.Id) {
    formState.id = usernameStore.Id
  } else {
    usernameStore.setUsername(undefined)
    router.replace({ name: 'resetValidate' })
  }
})

const disabled = computed(() => {
  return !(formState.id && formState.password1 && formState.password2)
})

async function handleSubmit() {
  const data: ResetPasswordParams = {
    id: formState.id,
    newPassword: formState.password1,
    newPasswordAgain: formState.password2,
  }
  try {
    await resetPassword(data)
    messageApi.info('密码重置成功，即将跳转登录页...')
    setTimeout(() => {
      overlayStore.resetPosition()
      router.replace({ name: 'inputPassword' })
    }, 2000)
  } catch (error) {
    console.error('密码重置失败:', error)
  }
}
</script>

<template>
  <div ref="signFormRef" class="reset-container form-container half-width">
    <context-holder />
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">登录</a-typography-title>
      <a-form ref="formRef" :model="formState" :rules="rules" @finish="handleSubmit">
        <a-form-item has-feedback label="输入新密码" name="password1">
          <a-input-password v-model:value="formState.password1">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item has-feedback label="请确认密码" name="password2">
          <a-input-password v-model:value="formState.password2">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button :disabled="disabled" type="primary" html-type="submit" style="width: 100%">
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
