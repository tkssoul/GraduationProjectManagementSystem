import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useOverlayStore = defineStore(
  'overlayPosition',
  () => {
    const overlayPosition = ref('left')
    function changePosition(newPosition: string) {
      overlayPosition.value = newPosition
    }

    function resetPosition() {
      overlayPosition.value = 'left'
    }

    return { overlayPosition, changePosition, resetPosition }
  },
  {
    persist: true,
  },
)
