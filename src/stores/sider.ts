import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useSiderStore = defineStore(
  'selectedKey',
  () => {
    const selectedKeys = ref(['8'])
    const openKeys = ref(['sub1'])
    function changeKey(newKey: string) {
      selectedKeys.value[0] = newKey
    }
    function changeOpenKeys(newOpenKeys: string[]) {
      openKeys.value = newOpenKeys
    }

    function reset() {
      selectedKeys.value = ['8']
    }

    return { selectedKeys, openKeys, changeKey, changeOpenKeys, reset }
  },
  {
    persist: true,
  },
)
