import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
      children: [
        {
          path: 'grade-management',
          name: 'gradeManagement',
          component: () => import('../views/GradeManagement.vue'),
          meta: {
            requiresAuth: true,
          },
        },
      ],
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/auth/LoginView.vue'),
      children: [
        {
          path: 'input-password',
          name: 'inputPassword',
          component: () => import('../views/auth/InputPassword.vue'),
        },
        {
          path: 'reset-validate',
          name: 'resetValidate',
          component: () => import('../views/auth/ResetValidate.vue'),
        },
        {
          path: 'reset-password',
          name: 'resetPassword',
          component: () => import('../views/auth/ResetPassword.vue'),
        },
      ],
    },
    {
      path: '/404',
      name: 'notFound',
      component: () => import('../views/NotFound.vue'),
    },
    {
      path: '/:catchAll(.*)',
      redirect: '/404',
    },
  ],
})

router.beforeEach((to, from, next) => {
  // 检查目标路由是否需要登录权限
  if (to.meta.requiresAuth) {
    // 判断用户是否已登录
    // 或使用localStorage: const isAuthenticated = localStorage.getItem('token');
    const isAuthenticated = localStorage.getItem('token')

    if (isAuthenticated) {
      // 已登录，允许访问
      next()
    } else {
      // 未登录，跳转到登录页，并带上当前路由路径（以便登录后跳转回来）
      next({
        name: 'inputPassword',
        query: { redirect: to.fullPath },
      })
    }
  } else {
    // 无需登录权限，直接访问
    next()
  }
})

export default router
