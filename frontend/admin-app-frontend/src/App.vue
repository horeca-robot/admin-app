<template>
  <div>
    <Notifications />
    <NavigationBar v-show="isNotLoginPage" :currentPage="this.$route.name" :restaurantName="this.restaurantSettings.name"/>
    <router-view :class="{'other-page': isNotLoginPage,  'login-page': !isNotLoginPage}"/>
    <Background ref="background" />
  </div>
</template>

<script>
import Background from './components/Background.vue'
import NavigationBar from './components/NavigationBar.vue'
import Notifications from './components/Notifications.vue'
import ColorUtil from './utils/ColorUtil.js'
import api from './wrappers/InfoWrapper.js'
import notification from './utils/NotificationUtil'
import LanguageUtil from './utils/LanguageUtil'

export default {
  name: 'App',
  components: { Background, NavigationBar, Notifications},
  data() {
    return {
      text: LanguageUtil.getTextObject(),
      restaurantSettings: {}
    }
  },
  async created(){
    this.setStandardLanguage()
    await this.updateCss()
    await this.getRestaurantSettings()
  },
  methods: {
    setStandardLanguage(){
      if(localStorage.getItem('language') === null){
        localStorage.setItem('language', 'English')
      }
    },
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
    },
    async getRestaurantSettings(){
      const response = await api.getRestaurantSettings()
      if(response.data["success"]){
        this.restaurantSettings = response.data["data"]["info"]
      }
      else{
        notification.showErrorNotification(this.text.App_Error)
      }
    }
  },
  computed: {
    isNotLoginPage() {
      if(this.$route.name !== 'login' && this.$route.name !== 'forgot-password'){
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
    --notvisible-color: rgb(128 ,128 ,128);
  }

  ::-webkit-scrollbar {
    width: 15px;
  }

  ::-webkit-scrollbar-thumb {
    border: 5px solid var(--secondary-color);
    border-radius: 50px;
    background-color: var(--primary-color);
    background-clip: padding-box;
    cursor: pointer;
  }
</style>
