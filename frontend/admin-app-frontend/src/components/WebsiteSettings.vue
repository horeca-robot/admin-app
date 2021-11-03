<template>
  <div id="container">
    <div style="float: left !important">
      <label for="background">Background Image</label>
      <ImagePreview ref="backgroundPreview" id="background" />
      <button @click="handleSave" type="button">Save Settings</button>
    </div>

    <div id="colors">
      <div>
        <label for="primary">PrimaryColor&nbsp;</label>
        <input id="primary" type="color" v-model="primaryColor" />
      </div>

      <div>
        <label for="secondary">SecondaryColor&nbsp;</label>
        <input id="secondary" type="color" v-model="secondaryColor" />
      </div>
    </div>
  </div>
</template>

<script>
import ImagePreview from "./ImagePreview.vue";
import api from "../wrappers/InfoWrapper.js";

export default {
  async mounted() {
    let response = await api.getWebsiteSettings()

    let websiteSettings  = response.data.data.info;
    
    if(websiteSettings.primaryColor != null)
      this.primaryColor = websiteSettings.primaryColor;

    if(websiteSettings.secondaryColor)
      this.secondaryColor = websiteSettings.secondaryColor;
    
    if(websiteSettings.backgroundImage != null)
    {
      let base64 = await api.getBase64Image(websiteSettings.backgroundImage)

      this.$refs.backgroundPreview.setBase64(base64)
    }
  },
  data() {
    return {
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
      await api.saveWebsiteSettings(payload);
      await this.$root.updateBackground()
      await this.$root.updateCss()
    },

    async loadInfo() {

    }
  },
  components: {
    ImagePreview,
  },
};
</script>

<style scoped>
#container {
  min-width: 100%;
  display: inline-block;
  text-align: center;
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
  min-width: 100%;
  height: 35px;
  background-color: #0157e4;
  border: none !important;
  border-radius: 5px !important;
  color: white !important;
  font-family: Strait;
  font-weight: normal;
  font-size: 18px;
  transition: 0.5s;
}

button:hover {
  background-color: green;
}

button:active {
  transform: translateY(5px);
}
</style>