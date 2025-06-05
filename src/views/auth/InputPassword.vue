<script setup lang="ts">
import { reactive, computed, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { useIdStore } from '@/stores/username'
import { useJWTStore } from '@/stores/jwtToken'
import { useUserStore } from '@/stores/user'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { loginStudent, loginTeacher } from '@/api/user'

const router = useRouter()
const usernameStore = useIdStore()
const jwtStore = useJWTStore()
const userStore = useUserStore()

interface UserInfoType {
  id: number
  name: string
  role: string
}

interface FormState {
  id: number
  password: string
  remember: boolean
  role: string
}

const formState = reactive<FormState>({
  id: 0,
  password: '',
  remember: true,
  role: 'student',
})

onBeforeMount(() => {
  formState.id = usernameStore.Id
})

const disabled = computed(() => {
  return !(formState.id && formState.password)
})

async function handleSubmit() {
  console.log(`formState = ${JSON.stringify(formState)}`)
  if (formState.role == 'teacher') {
    try {
      const res = await loginTeacher({ teacherId: formState.id, password: formState.password })
      console.log(`教师登录 response = ${JSON.stringify(res)}`)
      if (res.data.authorization != null) {
        jwtStore.refreshToken(res.data.authorization)
        const userInfo: UserInfoType = {
          id: res.data.id,
          name: res.data.name,
          role: formState.role,
        }
        userStore.setUser(userInfo)
      }

      await router.replace({ name: 'gradeManagement' })
    } catch (error) {
      console.error('教师登录出错:', error)
    }
    return
  } else if (formState.role == 'student') {
    try {
      const res = await loginStudent({ studentId: formState.id, password: formState.password })
      console.log(`学生登录 response = ${JSON.stringify(res)}`)
      if (res.data.token != null) {
        jwtStore.refreshToken(res.data.token)
        const userInfo: UserInfoType = {
          id: res.data.id,
          name: res.data.name,
          role: formState.role,
        }
        userStore.setUser(userInfo)
      }
      await router.replace({ name: 'gradeManagement' })
    } catch (error) {
      console.error('学生登录出错:', error)
    }
    return
  }
}
</script>

<template>
  <div ref="signFormRef" class="sign-in-container form-container half-width">
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">登录</a-typography-title>
      <a-form :model="formState" name="normal_login" class="login-form" @finish="handleSubmit">
        <a-form-item
          label="账户"
          name="id"
          @change="usernameStore.setUsername(formState.id)"
          :rules="[{ required: true, message: '请输入账户！' }]"
        >
          <a-input v-model:value="formState.id">
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

        <a-form-item name="role">
          <a-flex justify="center" align="center" :vertical="true">
            <a-radio-group v-model:value="formState.role">
              <a-radio-button value="student">学生登录</a-radio-button>
              <a-radio-button value="teacher">教师登录</a-radio-button>
            </a-radio-group>
          </a-flex>
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
