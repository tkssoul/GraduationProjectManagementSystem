import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useIdStore = defineStore('Id', () => {
  const Id = ref()

  function setUsername(newId?: number) {
    Id.value = newId
  }
  return { Id, setUsername }
})
