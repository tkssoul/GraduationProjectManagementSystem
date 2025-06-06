<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { Ref } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import type { TableColumnType } from 'ant-design-vue'
import { getGradeList, reviewGrade } from '@/api/grade'
import type { Grade } from '@/api/grade'

type TableDataType = {
  key: string
  name: string
  address: string
  applicationTime: string
}

const searchStatus = ref<string>('')

const searchInput = ref()

const state = reactive({
  searchText: '',
  searchedColumn: '',
})

const handleSearch = (selectedKeys: string[], confirm: () => void, dataIndex: string) => {
  confirm()
  state.searchText = selectedKeys[0]
  state.searchedColumn = dataIndex
}

const handleReset = (clearFilters: (option?: { confirm: boolean }) => void) => {
  clearFilters({ confirm: true })
  state.searchText = ''
}

const gradeState = ref<Grade[]>([
  {
    gradeId: 0,
    gradeStatus: '',
  },
])

async function refreshGradeList(searchStatus: string) {
  const res = await getGradeList(searchStatus)
  data.value = res.data
  data.value.map((item, index) => {
    item.key = String(index + 1)
  })
}

async function handleReview(record: Record<string, number>, pass: boolean) {
  console.log('点击通过')
  gradeState.value[0].gradeId = record.gradeId
  gradeState.value[0].gradeStatus = pass ? '已批准' : '已拒绝'
  await reviewGrade(gradeState.value)
  await refreshGradeList(searchStatus.value)
}

const columns: TableColumnType<TableDataType>[] = [
  {
    title: '编号',
    dataIndex: 'gradeId',
    key: 'gradeId',
    width: '5%',
  },
  {
    title: '学生名',
    dataIndex: 'name',
    key: 'name',
    width: '12%',
    customFilterDropdown: true,
    onFilter: (value, record) =>
      record.name.toString().toLowerCase().includes(value.toString().toLowerCase()),
    onFilterDropdownOpenChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          searchInput.value.focus()
        }, 100)
      }
    },
  },
  {
    title: '学号',
    dataIndex: 'studentId',
    key: 'studentId',
    width: '10%',
  },
  {
    title: '论文主题',
    dataIndex: 'topicTitle',
    key: 'topicTitle',
    width: '18%',
  },
  {
    title: '指导老师',
    dataIndex: 'supervisor',
    key: 'supervisor',
    width: '7%',
  },
  {
    title: '中期成绩',
    key: 'midtertmGrade',
    dataIndex: 'midtertmGrade',
    width: '7%',
  },
  {
    title: '论文成绩',
    key: 'thesisGrade',
    dataIndex: 'thesisGrade',
    width: '7%',
  },
  {
    title: '答辩成绩',
    key: 'defenseGrade',
    dataIndex: 'defenseGrade',
    width: '7%',
  },
  {
    title: '最终成绩',
    key: 'finalGrade',
    dataIndex: 'finalGrade',
    width: '7%',
  },
  {
    title: '成绩状态',
    key: 'gradeStatus',
    dataIndex: 'gradeStatus',
    width: '7%',
  },
  {
    title: '操作',
    key: 'action',
    width: '12%',
  },
]

interface DataItem {
  key: string
  studentId: number
  name: string
  topicTitle: string
  supervisor: string
  midtertmGrade: number
  thesisGrade: number
  defenseGrade: number
  finalGrade: number
  gradeStatus: string
}

const data: Ref<DataItem[]> = ref([
  {
    key: '1',
    gradeId: 1, // 添加编号数据
    studentId: 20230001,
    name: '张三',
    topicTitle: '人工智能在教育领域的应用研究',
    supervisor: '李老师',
    midtertmGrade: 85,
    thesisGrade: 88,
    defenseGrade: 90,
    finalGrade: 87,
    gradeStatus: '通过',
  },
  {
    key: '2',
    gradeId: 2, // 添加编号数据
    studentId: 20230002,
    name: '李四',
    topicTitle: '大数据驱动的市场营销策略分析',
    supervisor: '王老师',
    midtertmGrade: 78,
    thesisGrade: 82,
    defenseGrade: 80,
    finalGrade: 80,
    gradeStatus: '通过',
  },
  {
    key: '3',
    gradeId: 3, // 添加编号数据
    studentId: 20230003,
    name: '王五',
    topicTitle: '区块链技术在金融领域的应用',
    supervisor: '赵老师',
    midtertmGrade: 65,
    thesisGrade: 70,
    defenseGrade: 68,
    finalGrade: 67,
    gradeStatus: '通过',
  },
])

onMounted(async () => {
  refreshGradeList(searchStatus.value)
})

import { watch } from 'vue'

// 监听 searchStatus 的变化，当它改变时重新请求数据
watch(searchStatus, async (newStatus) => {
  refreshGradeList(newStatus)
})
</script>

<template>
  <a-typography-title :level="3">毕业论文审核</a-typography-title>
  <a-typography-paragraph> 使用表格快速查看、修改学生的成绩 </a-typography-paragraph>
  <a-radio-group v-model:value="searchStatus">
    <a-radio-button value="">全部</a-radio-button>
    <a-radio-button value="待审批">待审批</a-radio-button>
    <a-radio-button value="已批准">已批准</a-radio-button>
    <a-radio-button value="已拒绝">已拒绝</a-radio-button>
  </a-radio-group>
  <a-table :columns="columns" :data-source="data">
    <template
      #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
    >
      <div style="padding: 8px">
        <a-input
          ref="searchInput"
          placeholder="搜索学生姓名"
          :value="selectedKeys[0]"
          style="width: 188px; margin-bottom: 8px; display: block"
          @change="
            (e: Event) =>
              setSelectedKeys(
                (e.target as HTMLInputElement).value ? [(e.target as HTMLInputElement).value] : [],
              )
          "
          @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
        />
        <a-button
          type="primary"
          size="small"
          style="width: 90px; margin-right: 8px"
          @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          <template #icon><SearchOutlined /></template>
          搜索
        </a-button>
        <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
          重置
        </a-button>
      </div>
    </template>
    <template #customFilterIcon="{ filtered }">
      <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }" />
    </template>

    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'name'">
        <a>
          {{ record.name }}
        </a>
      </template>

      <template v-else-if="column.key === 'action'">
        <span>
          <a @click="handleReview(record, true)">通过</a>
          <a-divider type="vertical" />
          <a @click="handleReview(record, false)">驳回</a>
        </span>
      </template>
    </template>
  </a-table>
</template>

<style scoped>
/* Your component's scoped styles go here */
</style>
