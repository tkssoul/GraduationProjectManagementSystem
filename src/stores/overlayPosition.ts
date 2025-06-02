import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useOverlayStore = defineStore(
  'overlayPosition',
  () => {
    const overlayPosition = ref('left')
    function changePosition(newPosition: string) {
      overlayPosition.value = newPosition
    }

    return { overlayPosition, changePosition }
  },
  {
    persist: true,
  },
)
