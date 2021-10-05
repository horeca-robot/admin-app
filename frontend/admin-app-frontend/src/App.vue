<template>
  <div>
    <router-view />
    <Background />
  </div>
</template>

<script>
import Background from './components/Background.vue'

export default {
  name: 'App',
  components: { Background }
}
</script>

<style>
#app {
  font-family: Strait !important;
}
</style>
