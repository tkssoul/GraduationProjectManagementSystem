import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useJWTStore = defineStore(
  'token',
  () => {
    const token = ref('')

    function refreshToken(newToken: string) {
      token.value = newToken
    }

    return { token, refreshToken }
  },
  {
    persist: true,
  },
)
