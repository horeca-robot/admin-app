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
  components: { Background, NavigationBar },
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
  },
  methods: {
    // getTextColor(){
    //   const style = getComputedStyle(document.body)
    //   const secondary = style.getPropertyValue('--secondary-color')

    //   var rgb = secondary.match(/\d+/g); //Get rgb values from --secondary-color variable

    //   if((rgb[0]*0.299 + rgb[1]*0.587 + rgb[2]*0.114) > 186){
    //     document.documentElement.style.setProperty('--text-color', 'rgba(0, 0, 0, 1)');
    //   }
    //   else{
    //     document.documentElement.style.setProperty('--text-color', 'rgba(255, 255, 255, 1)');
    //   }
    // }
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
  --primary-color: rgb(255, 0, 0);
  --secondary-color: rgb(0, 0, 0);
  --text-color: rgb(0, 0, 0, 1);
}
</style>
