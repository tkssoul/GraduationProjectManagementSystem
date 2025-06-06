import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useJWTStore = defineStore(
  'token',
  () => {
    const token = ref('')

    function refreshToken(newToken: string) {
      token.value = newToken
    }

    function clearToken() {
      token.value = ''
      localStorage.removeItem('token')
    }

    return { token, refreshToken, clearToken }
  },
  {
    persist: true,
  },
)
