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
        },
      ],
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
  ],
})

export default router
