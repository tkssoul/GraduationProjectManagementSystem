<script setup lang="ts">
import { ref, reactive } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import type { TableColumnType } from 'ant-design-vue'
type TableDataType = {
  key: string
  name: string
  address: string
  applicationTime: string
}

const tabActiveKey = ref('1')

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

const handleReset = (clearFilters: (options?: { confirm: boolean }) => void) => {
  clearFilters({ confirm: true })
  state.searchText = ''
}

const columns: TableColumnType<TableDataType>[] = [
  {
    title: '学生名',
    dataIndex: 'name',
    key: 'name',
    width: '15%',
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
    title: '邮箱地址',
    dataIndex: 'email',
    key: 'email',
    width: '20%',
  },
  {
    title: '状态',
    dataIndex: 'status',
    key: 'status',
    width: '10%',
  },
  {
    title: '申请时间',
    key: 'applicationTime',
    dataIndex: 'applicationTime',
    width: '15%',
    sorter: (a: TableDataType, b: TableDataType) =>
      new Date(a.applicationTime).getTime() - new Date(b.applicationTime).getTime(),
  },
  {
    title: '备注',
    key: 'remark',
    dataIndex: 'remark',
    width: '10%',
  },
  {
    title: '操作',
    key: 'action',
    width: '20%',
  },
]

const data = [
  {
    key: '1',
    name: '三字名',
    email: '2495830486@qq.com',
    status: '通过',
    applicationTime: '2023-10-01',
    remark: '备注原因备注内容备注原因备注内容备注原因备注内容',
  },
  {
    key: '2',
    name: '四字名',
    email: '2495830486@qq.com',
    status: '驳回',
    applicationTime: '2023-10-02',
    remark: '备注原因',
  },
  {
    key: '3',
    name: 'Sue Black',
    email: '2495830486@qq.com',
    status: '审核中',
    applicationTime: '2023-10-03',
    remark: '无',
  },
]
</script>

<template>
  <a-typography-title :level="3">毕业论文审核（仅教师可见）</a-typography-title>
  <a-typography-paragraph> 使用表格快速查看、修改学生的成绩 </a-typography-paragraph>

  <a-tabs v-model:activeKey="tabActiveKey" type="card">
    <a-tab-pane key="1" tab="论文审核">
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
                    (e.target as HTMLInputElement).value
                      ? [(e.target as HTMLInputElement).value]
                      : [],
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
          <template v-else-if="column.key === 'status'">
            <span>
              <a-tag
                :key="record.status"
                :color="
                  record.status === '通过'
                    ? 'green'
                    : record.status === '驳回'
                      ? 'volcano'
                      : 'geekblue'
                "
              >
                {{ record.status.toUpperCase() }}
              </a-tag>
            </span>
          </template>
          <template v-else-if="column.key === 'action'">
            <span>
              <a>打分</a>
            </span>
          </template>
        </template>
      </a-table>
    </a-tab-pane>

    <a-tab-pane key="2" tab="操作历史">
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
                    (e.target as HTMLInputElement).value
                      ? [(e.target as HTMLInputElement).value]
                      : [],
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
          <template v-else-if="column.key === 'status'">
            <span>
              <a-tag
                :key="record.status"
                :color="
                  record.status === '通过'
                    ? 'green'
                    : record.status === '驳回'
                      ? 'volcano'
                      : 'geekblue'
                "
              >
                {{ record.status.toUpperCase() }}
              </a-tag>
            </span>
          </template>
          <template v-else-if="column.key === 'action'">
            <span>
              <a></a>
            </span>
          </template>
        </template>
      </a-table>
    </a-tab-pane>
  </a-tabs>
</template>

<style scoped>
/* Your component's scoped styles go here */
</style>
