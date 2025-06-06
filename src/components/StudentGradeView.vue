<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import { getStudentGrade } from '@/api/grade'
import { useJWTStore } from '@/stores/jwtToken'
import { useUserStore } from '@/stores/user'
const loading = ref(true)

const jwtStore = useJWTStore()
const userStore = useUserStore()

type GradeType = {
  studentId: number
  topicTitle: string
  midtermGrade: number
  thesisGrade: number
  defenseGrade: number
  finalGrade: number
  gradeStatus: string
}

const grade = reactive<GradeType>({
  studentId: 0,
  topicTitle: '',
  midtermGrade: 0,
  thesisGrade: 0,
  defenseGrade: 0,
  finalGrade: 0,
  gradeStatus: '',
})

function getStatusColorClass(status: string) {
  if (status === '通过') {
    return 'green'
  } else if (status === '未通过') {
    return 'red'
  } else {
    return 'orange'
  }
}

onMounted(async () => {
  const res = await getStudentGrade(String(userStore.user.id), jwtStore.token)
  grade.studentId = res.data !== null ? (res.data.studentId ? res.data.studentId : 0) : 0
  grade.topicTitle =
    res.data !== null
      ? res.data.topicTitle
        ? res.data.topicTitle
        : '暂无论文题目'
      : '暂无论文题目'
  grade.midtermGrade = res.data !== null ? (res.data.midtermGrade ? res.data.midtermGrade : 0) : 0
  grade.thesisGrade = res.data !== null ? (res.data.thesisGrade ? res.data.thesisGrade : 0) : 0
  grade.defenseGrade = res.data !== null ? (res.data.defenseGrade ? res.data.defenseGrade : 0) : 0
  grade.finalGrade = res.data !== null ? (res.data.finalGrade ? res.data.finalGrade : 0) : 0
  grade.gradeStatus =
    res.data !== null ? (res.data.gradeStatus ? res.data.gradeStatus : '审核中') : '处理中'
})

setTimeout(() => {
  loading.value = false
}, 500)
</script>

<template>
  <div>
    <a-typography-title :level="3">
      成绩管理学生端（仅学生可见）{{ grade.gradeStatus }}
    </a-typography-title>
    <a-card
      :title="grade.topicTitle ? grade.topicTitle : '暂无论文题目'"
      style="width: 100%"
      :loading="loading"
    >
      <template #extra>
        <a-space>
          <span :class="['status-dot', getStatusColorClass(grade.gradeStatus)]" />
          <span>{{ grade.gradeStatus }}</span>
        </a-space>
      </template>
      <a-row>
        <a-col :span="12">
          <a-statistic
            title="中期检查成绩"
            :value="grade.midtermGrade"
            style="margin-right: 50px"
          />
        </a-col>
        <a-col :span="12">
          <a-statistic title="论文成绩" :value="grade.thesisGrade" />
        </a-col>
        <a-col :span="12">
          <a-statistic title="答辩成绩" :value="grade.defenseGrade" />
        </a-col>
        <a-col :span="12">
          <a-statistic title="最终成绩" :precision="grade.finalGrade" :value="grade.finalGrade" />
        </a-col>
      </a-row>
    </a-card>
  </div>
</template>

<style scoped>
.status-dot {
  display: inline-block;
  width: 10px;
  height: 10px;
  border-radius: 50%;
}

.status-dot.green {
  background-color: green;
}

.status-dot.red {
  background-color: red;
}

.status-dot.orange {
  background-color: orange;
}
</style>
