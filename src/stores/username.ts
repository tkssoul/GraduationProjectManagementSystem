import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useIdStore = defineStore('Id', () => {
  const Id = ref<number>()

  function setUsername(newId?: number) {
    Id.value = newId
  }

  function clearUsername() {
    Id.value = undefined
  }

  return { Id, setUsername, clearUsername }
})
