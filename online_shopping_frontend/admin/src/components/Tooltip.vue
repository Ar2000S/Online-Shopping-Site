<script setup>
import { ref } from 'vue'
defineProps({ text: String })
const show = ref(false)
</script>

<template>
  <span
    class="tooltip-wrapper"
    @mouseenter="show = true"
    @mouseleave="show = false"
  >
    <slot />   <!-- the button goes here -->
    <Transition name="fade">
      <span v-if="show" class="tooltip-bubble">{{ text }}</span>
    </Transition>
  </span>
</template>

<style scoped>
.tooltip-wrapper { position: relative; display: inline-block; }
.tooltip-bubble {
  position: absolute;
  bottom: 125%; left: 50%; transform: translateX(-50%);
  background: #ccf7b8;
  padding: 6px 10px;
  border-radius: 6px; font-size: 12px;
  white-space: normal;    /*  does wordwrap */
  max-width: 300px;       /* wordwrap above this width */
  text-align: center;
  width: max-content;
  z-index: 1000;
  color: black;
  opacity: 0.7;
}


.fade-enter-active, .fade-leave-active { transition: opacity 0.33s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>
