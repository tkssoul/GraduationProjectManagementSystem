import { defineStore } from 'pinia'
import { ref } from 'vue'

interface User {
  name: string
  role: string
  id: number
}
export const useUserStore = defineStore(
  'user',
  () => {
    const user = ref({
      name: '',
      role: '',
      id: 0,
    })

    function setUser(newUser: User) {
      user.value = newUser
    }

    return { user, setUser }
  },
  { persist: true },
)
