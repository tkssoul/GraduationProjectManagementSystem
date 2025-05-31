import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'projectProposalSubmission',
      component: () => import('../views/ProjectProposalSubmission.vue'),
    },
    {
      path: '/grade-management',
      name: 'gradeManagement',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/GradeManagement.vue'),
    },
  ],
})

export default router
