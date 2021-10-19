<template>
  <div id="container">
    <h2>Settings</h2>
    <hr id="divider">
    <br>
    <div id="inputs">
      <label id="nameLabel" for="name">Restaurant Name&nbsp;</label>
      <input id="name" type="text" v-model="restaurantName">

      <label id="primaryColorLabel" for="primaryColor">Primary Color Website&nbsp;</label>
      <input id="primaryColor" type="color" v-model="primaryColor">

      <label id="secondaryColorLabel" for="secondaryColor">Secondary Color Website&nbsp;</label>
      <input id="secondaryColor" type="color" v-model="secondaryColor">
    </div>
    <br>
    <ImagePreview ref="preview"/>
    <button v-on:click="uploadImage">Test</button>
  </div>
</template>

<script>
import ImagePreview from '../components/ImagePreview.vue'

export default {
  data() {
    return {
      restaurantName: "",
      primaryColor: "#1200ff",
      secondaryColor: "#ff0021"
    };
  },

  components:{
    ImagePreview
  },

  methods: {
    uploadImage(){
      console.log(this.$refs.preview.base64)
    }
  },
};
</script>

<style scoped>

body{
  box-sizing: border-box;
}

#imagePreviewWrapper {
  width: 25%;
  height: 25%;
}

#container{
    margin-top: 5%;
    margin-left: 4%;
    margin-right: 2.5%;
    width: 80%;
    background-color: #FFFFFF;
}

#container h2{
  margin-left: 2.5%;
}

#divider{
  margin-left: 2.5%;
  margin-right: 2.5%;
}

#inputs{
  margin-left: 3%;
}

#name{
  margin-right: 6%;
}

#secondaryColorLabel{
  margin-left: 10%;
}

#primaryColorLabel{
  margin-left: 5%;
}

label{
  font-weight: bold;
}
</style>