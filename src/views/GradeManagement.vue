<script setup lang="ts">
import StudentGradeView from '@/components/StudentGradeView.vue'
import TeacherGradeView from '@/components/TeacherGradeView.vue'
import TROfficeGradeView from '@/components/TROfficeGradeView.vue'
</script>

<template>
  <div class="info-container">
    <StudentGradeView />
    <TeacherGradeView />
    <TROfficeGradeView />
  </div>
</template>

<style scoped>
.info-container {
  width: 100%;
  height: calc(100vh - 64px);
  padding: 20px;
  overflow: auto;
}
</style>
