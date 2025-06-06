<template>
  <a-flex justify="center" align="center" style="height: 100vh; width: 100vw; background: #f5f5f7">
    <div class="login-container">
      <a-flex gap="0" style="height: 100%">
        <!-- overlay面板 -->
        <div class="overlay-panel half-width" ref="overlayRef">
          <a-flex
            ref="overlayContentRef"
            class="overlay-content"
            :class="contentVisible == true ? 'content-visibility' : ''"
            justify="center"
            align="center"
            :vertical="true"
            style="height: 100%"
          >
            <a-typography-title>
              {{ overlayStore.overlayPosition == 'left' ? '欢迎回来' : '重置密码' }}
            </a-typography-title>
            <a-typography-paragraph
              >{{
                overlayStore.overlayPosition == 'left'
                  ? '登录高校毕业设计管理系统'
                  : '输入辅助信息快速重置你的密码'
              }}
            </a-typography-paragraph>
            <a-button
              type="primary"
              @click="
                () => {
                  overlayStore.overlayPosition == 'left' ? toReset() : toLogin()
                }
              "
              >{{ overlayStore.overlayPosition == 'left' ? '忘记密码' : '返回登录' }}</a-button
            >
          </a-flex>
        </div>

        <!-- 登录或重置密码表单 -->
        <router-view class="half-width" v-slot="{ Component }">
          <Transition>
            <component :is="Component" />
          </Transition>
        </router-view>
      </a-flex>
    </div>
  </a-flex>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useOverlayStore } from '@/stores/overlayPosition'

const overlayStore = useOverlayStore()
const router = useRouter()
const overlayRef = ref<HTMLElement | null>(null)
const contentVisible = ref(false)

const contentAnimation = () => {
  contentVisible.value = true
  setTimeout(() => {
    contentVisible.value = false
  }, 600)
}

function toReset() {
  overlayStore.changePosition('right')
  setTimeout(() => {
    router.replace('/login/reset-validate')
  }, 300)
  overlayRef.value?.classList.add('welcome-right')
  contentAnimation()
}

function toLogin() {
  overlayStore.changePosition('left')
  setTimeout(() => {
    router.replace('/login/input-password')
  }, 300)
  overlayRef.value?.classList.remove('welcome-right')
  contentAnimation()
}

onMounted(() => {
  if (overlayStore.overlayPosition === 'right') {
    overlayRef.value?.classList.add('welcome-right')
  } else {
    overlayRef.value?.classList.remove('welcome-right')
  }
})

watch(
  () => overlayStore.overlayPosition,
  (newPosition) => {
    if (newPosition === 'right') {
      overlayRef.value?.classList.add('welcome-right')
    } else {
      overlayRef.value?.classList.remove('welcome-right')
    }
  },
)
</script>

<style scoped>
.login-container {
  overflow: hidden;
  width: 600px;
  height: 40%;
  background: #fff;
  box-shadow: 10px 10px 5px 5px rgba(0, 0, 0, 0.2);
  border-radius: 20px;
}

.welcome-right {
  transform: translateX(100%);
}

.half-width {
  width: 300px;
  height: 100%;
}

.overlay-panel {
  z-index: 999;
  height: 100%;
  background: blue;
  padding: 40px;
  transition: transform 0.6s ease-in-out;
}

.overlay-panel * {
  color: #fff;
}

@keyframes fadeInOut {
  0% {
    opacity: 0;
  }
  50% {
    opacity: 0.1;
  }
  100% {
    opacity: 1;
  }
}

.content-visibility {
  animation-name: fadeInOut;
  animation-duration: 0.6s;
}

.v-enter-active {
  animation: fadeIn 0.3s ease-out;
}

.v-leave-active {
  display: none;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>
