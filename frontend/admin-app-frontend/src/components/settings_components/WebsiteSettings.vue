<template>
  <div id="container">
    <div style="float: left !important">
      <label for="background">{{text.WebSet_BackgroundImage}}</label>
      <ImagePreview ref="backgroundPreview" id="background" />
      <button @click="handleSave" type="button">{{text.WebSet_SaveSettings}}</button>
    </div>

    <div id="colors">
      <div>
        <label for="primary">{{text.WebSet_PrimaryColor}}&nbsp;</label>
        <input id="primary" type="color" v-model="primaryColor" />
      </div>

      <div>
        <label for="secondary">{{text.WebSet_SecondaryColor}}&nbsp;</label>
        <input id="secondary" type="color" v-model="secondaryColor" />
      </div>
    </div>
    <LanguageSettings />
  </div>
</template>

<script>
import ImagePreview from "../ImagePreview.vue";
import api from "../../wrappers/InfoWrapper.js";
import LanguageUtil from '../../utils/LanguageUtil';
import LanguageSettings from './LanguageSettings.vue';

export default {
  async mounted() {
    let response = await api.getWebsiteSettings()

    let websiteSettings  = response.data.data.info;
    
    if(websiteSettings.primaryColor != null)
      this.primaryColor = websiteSettings.primaryColor;

    if(websiteSettings.secondaryColor)
      this.secondaryColor = websiteSettings.secondaryColor;
    
    if(websiteSettings.backgroundImage != null) {
      this.$refs.backgroundPreview.setBase64(websiteSettings.backgroundImage)
    }
  },
  data() {
    return {
      text: LanguageUtil.getTextObject(),
      primaryColor: "#0157e4",
      secondaryColor: "#ffffff",
    };
  },
  methods: {
    async handleSave() {

      const payload = {
        primaryColor: this.primaryColor,
        secondaryColor: this.secondaryColor,
        backgroundImage: `${this.$refs.backgroundPreview.base64}`,
      };
      await api.putWebsiteSettings(payload);
      await this.$root.updateBackground()
      await this.$root.updateCss()
    }
  },
  components: {
    ImagePreview,
    LanguageSettings
  },
};
</script>

<style scoped>
#container {
  min-width: 100%;
  display: inline-block;
  text-align: center;
}

label {
  color: var(--text-color)
}

#container div {
  top: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  height: fit-content;
  width: fit-content;
}

#container #colors {
  margin-right: 150px;
}

#colors div {
  display: inline-block;
  padding-left: 50px;
}

button {
  margin-top: 50px;
  min-width: 75%;
  height: 35px;
  background-color: var(--primary-color);
  border: none !important;
  border-radius: 5px !important;
  color: var(--secondary-color) !important;
  font-family: Strait;
  font-weight: normal;
  font-size: 18px;
  transition: 0.5s;
}

button:hover {
  background-color: green;
  cursor: pointer;
}

button:active {
  transform: translateY(5px);
}
</style>