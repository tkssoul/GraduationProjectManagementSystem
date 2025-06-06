<script setup lang="ts">
import { RouterView, useRouter } from 'vue-router'
import { ref, h, reactive } from 'vue'
import { useSiderStore } from '@/stores/sider'
import { useUserStore } from '@/stores/user'
import { useJWTStore } from '@/stores/jwtToken'
import type { MenuTheme, ItemType } from 'ant-design-vue'
const theme = ref<MenuTheme>('light')

const userStore = useUserStore()
const jwtStore = useJWTStore()
const userInfo = userStore.user

import {
  AuditOutlined,
  AppstoreOutlined,
  CarryOutOutlined,
  FileTextOutlined,
  TeamOutlined,
  RadarChartOutlined,
  SettingOutlined,
  GlobalOutlined,
} from '@ant-design/icons-vue'

const siderStore = useSiderStore()

const siderList: ItemType[] = reactive([
  {
    key: '1',
    icon: () => h(AuditOutlined),
    label: '课题申报',
    title: '课题申报',
  },
  {
    key: 'sub1',
    icon: () => h(AppstoreOutlined),
    label: '开题管理',
    title: '开题管理',
    children: [
      { key: '2', label: '学生课题（教师端）' },
      { key: '3', label: '开题报告（学生端）' },
      { key: '4', label: '题目审核（教科办）' },
    ],
  },
  {
    key: '5',
    icon: () => h(CarryOutOutlined),
    label: '中期检查管理',
    title: '中期检查管理',
  },
  {
    key: '6',
    icon: () => h(FileTextOutlined),
    label: '论文管理',
    title: '论文管理',
  },
  {
    key: '7',
    icon: () => h(TeamOutlined),
    label: '答辩管理',
    title: '答辩管理',
  },
  {
    key: '8',
    icon: () => h(RadarChartOutlined),
    label: '成绩管理',
    title: '成绩管理',
  },
])

const pathMap: Record<string, string> = reactive({
  '1': '/test',
  '2': '/student-topic',
  '3': '/opening-report',
  '4': '/topic-review',
  '5': '/mid-term-check',
  '6': '/thesis-management',
  '7': '/defense-management',
  '8': '/grade-management',
})

const router = useRouter()

function goToPath(item: ItemType) {
  if (item != null) {
    const path = pathMap[item.key as string]
    router.push(path)
  }
}

function logout() {
  localStorage.removeItem('token')
  userStore.clearUser()
  jwtStore.clearToken()
  router.replace({ name: 'inputPassword' })
}
</script>

<template>
  <a-layout style="min-height: 100vh; min-width: 100vw">
    <!-- 侧边栏 -->
    <a-layout-sider :theme="theme">
      <div class="logo">
        <a-image
          alt="杭州电子科技大学Logo"
          placeholder="true"
          :preview="false"
          src="https://www.hdu.edu.cn/_upload/tpl/02/12/530/template530/images/logo_main.svg"
        />
      </div>

      <a-menu
        v-model:openKeys="siderStore.openKeys"
        v-model:selectedKeys="siderStore.selectedKeys"
        mode="inline"
        @click="goToPath"
        :theme="theme"
        :items="siderList"
      />
    </a-layout-sider>

    <!-- 头部 -->
    <a-layout>
      <a-layout-header style="height: --header-height !important; background: #fff; padding: 0">
        <a-page-header style="width: 100%; padding: 14.5px 24px">
          <template #extra>
            <a-space :size="16" wrap>
              <SettingOutlined class="header-icons" />
              <a-popover>
                <template #content>
                  <a-flex gap="0" justify="center" align="center" :vertical="true">
                    <span class="popover-content" @click="logout">退出登录</span>
                  </a-flex>
                </template>
                <span class="user-info">
                  <a-avatar src="@/assets/logo-icon.png" alt="用户头像" />
                  <span class="username">{{ userInfo.name }}</span>
                </span>
              </a-popover>

              <GlobalOutlined class="header-icons" />
            </a-space>
          </template>
        </a-page-header>
      </a-layout-header>

      <!-- 右边内容部分 -->
      <router-view v-slot="{ Component }">
        <Transition>
          <component :is="Component" />
        </Transition>
      </router-view>
    </a-layout>
  </a-layout>
</template>

<style scoped>
.logo {
  height: 32px;
  margin: 16px;
  text-align: center;
}

.user-info {
  width: 100px;
}

.user-info .username {
  width: 4rem;
}

.header-icons {
  padding: 5px;
}

.header-icons:hover {
  color: #1777ff;
  background-color: rgba(23, 119, 255, 0.2); /* #1777ff is rgb(23, 119, 255) */
  border-radius: 5px;
}

.site-layout .site-layout-background {
  background: #fff;
}
[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}

.icon {
  width: 1em;
  height: 1em;
  margin: 0 0.15em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}

.popover-content {
  text-align: center;
  padding: 5px;
  width: 100%;
}

.popover-content:hover {
  color: #1777ff;
  background-color: rgba(23, 119, 255, 0.2); /* #1777ff is rgb(23, 119, 255) */
  border-radius: 5px;
  cursor: pointer;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}

/* 视图切换动画效果 */
.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
