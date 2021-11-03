<template>
  <div>
    <NavigationBar v-show="isNotLoginPage" :currentPage="this.$route.name"/>
    <router-view :class="{'other-page': isNotLoginPage,  'login-page': !isNotLoginPage}"/>
    <Background ref="background" />
  </div>
</template>

<script>
import Background from './components/Background.vue'
import NavigationBar from './components/NavigationBar.vue'
import ColorUtil from './utils/ColorUtil.js'
import api from './wrappers/InfoWrapper.js'

export default {
  name: 'App',
  components: { Background, NavigationBar},
  async created(){
    ColorUtil.getTextColor()
    await this.updateCss()
  },
  methods: {
    async updateBackground(){
      await this.$refs.background.update()
    },
    async updateCss(){
      let response = await api.getWebsiteSettings()

        let websiteSettings = response.data.data.info

        if (websiteSettings.primaryColor != null) {
            document.documentElement.style.setProperty('--primary-color', websiteSettings.primaryColor)
        }

        if (websiteSettings.secondaryColor != null) {
            document.documentElement.style.setProperty('--secondary-color', websiteSettings.secondaryColor)
        }

        ColorUtil.getTextColor()
    }
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
