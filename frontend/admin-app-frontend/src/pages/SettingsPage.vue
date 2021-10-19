<template>
  <div id="container">
    <h2>Settings</h2>
    <hr id="divider" />
    <br />
    <div id="inputs">
      <label id="nameLabel" for="name">Restaurant Name&nbsp;</label>
      <input id="name" type="text" v-model="restaurantName" />

      <label id="primaryColorLabel" for="primaryColor">Primary Color Website&nbsp;</label>
      <input id="primaryColor" type="color" v-model="primaryColor" />

      <label id="secondaryColorLabel" for="secondaryColor">Secondary Color Website&nbsp;</label>
      <input id="secondaryColor" type="color" v-model="secondaryColor" />
    </div>

    <div class="row">
      <div class="block">
        <label for="imagePreview">Website Logo</label>
        <ImagePreview id="imagePreview" ref="preview" />
      </div>

      <div class="block">
        <label for="imagePreview2">Background Website</label>
        <ImagePreview id="imagePreview2" ref="preview2" />
      </div>
      <div class="block"></div>
    </div>

    <button v-on:click="uploadImage">Test</button>
  </div>
</template>

<script>
import ImagePreview from "../components/ImagePreview.vue";
import api from "../wrappers/Test.js"


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
   async uploadImage() {
      //console.log(this.$refs.preview.base64);
      await api.getTest();
    },
  },
};
</script>

<style scoped>
input {
  height: 30px;
}

body {
  box-sizing: border-box;
}

#imagePreviewWrapper {
  width: 25%;
  height: 25%;
}

#container {
  margin-top: 5%;
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
</style>