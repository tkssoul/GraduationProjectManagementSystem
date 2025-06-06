import { defineStore } from 'pinia'
import { ref } from 'vue'

interface User {
  name: string
  role: string
  id?: number
}
export const useUserStore = defineStore(
  'user',
  () => {
    const user = ref<User>({
      name: '',
      role: '',
      id: undefined,
    })

    function setUser(newUser: User) {
      user.value = newUser
    }

    function clearUser() {
      user.value = {
        name: '',
        role: '',
        id: undefined,
      }
    }

    return { user, setUser, clearUser }
  },
  { persist: true },
)
