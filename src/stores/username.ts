import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUsernameStore = defineStore('username', () => {
  const username = ref<string>('')

  function setUsername(newUsername: string) {
    username.value = newUsername
  }
  return { username, setUsername }
})
