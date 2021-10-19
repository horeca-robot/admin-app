<template>
  <div>
    <NavigationBar v-show="isNotLoginPage" :currentPage="this.$route.name"/>
    <router-view :class="{'other-page': isNotLoginPage,  'login-page': !isNotLoginPage}"/>
    <Background />
  </div>
</template>

<script>
import Background from './components/Background.vue'
import NavigationBar from './components/NavigationBar.vue'
import ColorUtil from './utils/ColorUtil.js'

export default {
  name: 'App',
  components: { Background, NavigationBar},
  created(){
    ColorUtil.getTextColor()
  },
  computed: {
    isNotLoginPage() {
      if(this.$route.name !== 'login'){
        return true;
      }
      return false;
    }
  }
}
</script>

<style>
#app {
  font-family: Strait !important;
}

.login-page{
  position: absolute;
}

.other-page{
  position: absolute;
  left: 12.5vw;
}

:root{
  --primary-color: rgb(1, 87, 228);
  --secondary-color: rgb(255, 255, 255);
  --text-color: rgb(0, 0, 0);
}
</style>
