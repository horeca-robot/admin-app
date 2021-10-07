<template>
  <div id="container">
    <div>
        <label for="name">Restaurant Name&nbsp;</label>
        <input id="name" ref="name" type="text" v-model="restaurantName">
        {{ restaurantName }}
    </div>
    <br>
    <br>
    <div id="imagePreviewWrapper">
        <label for="name">Restaurant Logo</label>
      <img style="width: 100% !important; height: 100% !important" :src="`${previewImageData}`"/>
    </div>

    <input ref="fileInput" accept="image/png, image/gif, image/jpeg" type="file" @input="previewImage" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      restaurantName: "",
      previewImageData: null,
    };
  },

  methods: {
    previewImage() {
      let input = this.$refs.fileInput;

      let file = input.files;

      if (file && file[0]) {
        let reader = new FileReader();

        reader.onload = (e) => {
          this.previewImageData = e.target.result;
        };

        reader.readAsDataURL(file[0]);

        this.$emit("input", file[0]);
      }
    },
  },
};
</script>

<style scoped>
#imagePreviewWrapper {
  width: 50%;
  height: 50%;
}

#container{
    margin-top: 2.5%;
    margin-left: 2.5%;
}
</style>