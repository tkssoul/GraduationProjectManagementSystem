import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useJWTStore = defineStore(
  'jwt',
  () => {
    const token = ref('')

    function refreshToken(newToken: string) {
      token.value = newToken
    }

    return { token, refreshToken }
  },
  {
    persist: {
      storage: localStorage, // token存储到localStorage
    },
  },
)
