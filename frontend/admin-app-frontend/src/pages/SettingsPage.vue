<template>
  <div id="container">
    <h2>Settings</h2>
    <hr id="divider" />
    <br />

    <div id="container-body">
      <div id="inputs">
        <label id="nameLabel" for="name">Restaurant Name&nbsp;</label>
        <input id="name" type="text" v-model="restaurantName" />

        <label id="primaryColorLabel" for="primaryColor"
          >Primary Color Website&nbsp;</label
        >
        <input id="primaryColor" type="color" v-model="primaryColor" />

        <label id="secondaryColorLabel" for="secondaryColor"
          >Secondary Color Website&nbsp;</label
        >
        <input id="secondaryColor" type="color" v-model="secondaryColor" />
      </div>

      <div class="row">
        <div class="block">
          <label for="imagePreview">Website Logo</label>
          <ImagePreview class="m-t" id="imagePreview" ref="websiteLogo" />
        </div>

        <div class="block" style="margin-left: 8% !important">
          <label for="imagePreview2">Background Website</label>
          <ImagePreview
            class="m-t"
            id="imagePreview2"
            ref="websiteBackground"
          />
        </div>

        <div class="block">&nbsp;</div>
      </div>
    </div>

    <hr id="divider" />

    <div id="container-footer">
      <button type="submit" id="save-btn" v-on:click="saveSettings">
        Save
      </button>
    </div>
  </div>
</template>

<script>
import ImagePreview from "../components/ImagePreview.vue";
import api from "../wrappers/RestaurantWrapper"

export default {
  data() {
    return {
      restaurantName: "",
      primaryColor: "#1200ff",
      secondaryColor: "#ff0021",
    };
  },

  components: {
    ImagePreview,
  },

  methods: {
    async saveSettings() {
      
      var logo = this.$refs.websiteLogo.base64;

      var background = this.$refs.websiteBackground.base64;

      const payload = {
        name: this.restaurantName,
        primarycolor: this.primaryColor,
        secondarycolor: this.secondaryColor,
        logo: logo,
        background: background,
      }

      var response = await api.saveSettings(payload);

      console.log(response)
    },
  },
};
</script>

<style scoped>
input {
  height: 30px;
  font-size: 16px;
}

#container-footer {
  margin-left: 2.5%;
  margin-right: 2.5%;
  padding-bottom: 2.5%;
}

button {
  display: flex;
  height: 40px;
  width: 100%;
  justify-content: center;
  align-items: center;
  background-color: #2d6ace;
  border: none;
  border-radius: 10px;
  color: white;
  font-size: 18px;
  transition-duration: 0.3s;
}

button:hover {
  background-color: #0157e4;
  box-shadow: 0 12px 16px 0 rgba(0, 0, 0, 0.24), 0 5px 5px 0 rgba(0, 0, 0, 0.19);
}

button:active {
  transform: translateY(4px);
}

#container-body {
  overflow: hidden scroll;
  height: 36rem;
}

#imagePreviewWrapper {
  width: 25%;
  height: 25%;
}

#container {
  margin-top: 2.5%;
  margin-left: 4%;
  margin-right: 2.5%;
  width: 80%;
  background-color: #ffffff;
}

#container h2 {
  margin-left: 2.5%;
}

#divider {
  margin-left: 2.5%;
  margin-right: 2.5%;
}

#inputs {
  margin-left: 3%;
}

#name {
  margin-right: 6%;
}

#secondaryColorLabel {
  margin-left: 10%;
}

#primaryColorLabel {
  margin-left: 5%;
}

label {
  font-weight: bold;
  font-size: 20px;
}

.row {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-left: 3%;
  margin-top: 5%;
}
.block {
  width: 45%;
}

.m-t {
  margin-top: 10px !important;
}
</style>