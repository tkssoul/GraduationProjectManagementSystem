<script setup lang="ts">
import { reactive, computed, onBeforeMount, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useIdStore } from '@/stores/username'
import { useJWTStore } from '@/stores/jwtToken'
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { loginStudent, loginTeacher } from '@/api/user'
import type { Rule, FormInstance } from 'ant-design-vue/es/form'

const router = useRouter()
const usernameStore = useIdStore()
const jwtStore = useJWTStore()
const requestState = ref<string>('idle')

const formRef = ref<FormInstance>()

interface FormState {
  id?: number
  password: string
  role: string
}

const formState = reactive<FormState>({
  id: undefined,
  password: '',
  role: 'student',
})

onBeforeMount(() => {
  formState.id = usernameStore.Id
})

const disabled = computed(() => {
  return !(formState.id && formState.password)
})

const rules: Record<string, Rule[]> = {
  id: [{ validator: validateID, trigger: 'blur' }],
  password: [{ validator: validatePassword, trigger: 'blur' }],
}

async function validateID(_rule: Rule, value: string) {
  if (value === '') {
    return Promise.reject(new Error('请输入账号！'))
  } else if (value !== '' && requestState.value === 'fail') {
    return Promise.reject(new Error('账号不存在！'))
  } else {
    return Promise.resolve()
  }
}

async function validatePassword(_rule: Rule, value: string) {
  if (value === '') {
    return Promise.reject(new Error('请输入密码！'))
  } else if (value !== '' && requestState.value === 'fail') {
    return Promise.reject(new Error('密码错误！'))
  } else {
    return Promise.resolve()
  }
}

async function handleSubmit() {
  try {
    const res =
      formState.role === 'teacher'
        ? await loginTeacher({ teacherId: formState.id, password: formState.password })
        : await loginStudent({ studentId: formState.id, password: formState.password })
    if (res.data != null) {
      const token = res.data.token ? res.data.token : res.data.authorization
      jwtStore.refreshToken(token)
      // const userInfo: UserInfoType = {
      //   id: res.data.id,
      //   name: res.data.name,
      //   role: formState.role,
      // }
      // userStore.setUser(userInfo)
      usernameStore.clearUsername()
      router.replace({ name: 'gradeManagement' })
    } else {
      requestState.value = 'fail'
      const toValidate: string = res.msg === '账号不存在' ? 'id' : 'password'
      formRef.value?.validate([toValidate])
    }
  } catch (error) {
    console.error('登录出错:', error)
  }
  return
}
</script>

<template>
  <div ref="signFormRef" class="sign-in-container form-container half-width">
    <a-flex justify="center" align="center" :vertical="true" style="height: 100%">
      <a-typography-title :level="2">登录</a-typography-title>
      <a-form
        ref="formRef"
        :model="formState"
        :rules="rules"
        name="normal_login"
        class="login-form"
        @finish="handleSubmit"
      >
        <a-form-item
          label="账户"
          name="id"
          @change="() => usernameStore.setUsername(formState.id)"
          @blur="() => (requestState = 'idle')"
        >
          <a-input v-model:value="formState.id">
            <template #prefix>
              <UserOutlined class="site-form-item-icon" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item label="密码" name="password">
          <a-input-password
            v-model:value="formState.password"
            @blur="() => (requestState = 'idle')"
          >
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
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
          <a-button :disabled="disabled" type="primary" html-type="submit" style="width: 100%">
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
  height: 100%;
  position: relative;
  left: 0;
}
</style>
