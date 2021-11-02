<template>
  <div id="container">
    <input
      @input="previewImage"
      ref="fileInput"
      type="file"
      accept="image/png, image/gif, image/jpeg"
    />
    <br />
    <img id="preview" :src="base64" />
  </div>
</template>

<script>
export default {
  name: "ImagePreview",
  data() {
    return {
      base64: null,
    };
  },

  methods: {
    lazyLoad() {
      let input = this.$refs.fileInput;

      let file = input.files;

      if (file && file[0]) {
        let reader = new FileReader();

        reader.onload = (e) => {
          console.log(e);
          this.base64 = e.target.result;
        };

        reader.onerror = (e) => {
          console.log(e);
        };

        reader.readAsBinaryString(file[0]);

        this.$emit("input", file[0]);
      }
    },
    previewImage() {
      let input = this.$refs.fileInput;

      let file = input.files;

      if (file && file[0]) {
        let reader = new FileReader();

        reader.onload = (e) => {
          this.base64 = e.target.result;
        };

        reader.readAsDataURL(file[0]);

        this.$emit("input", file[0]);
      }
    },

    setUrl(base64) {
      this.base64 = base64;
    }
  },
};
</script>

<style scoped>
#preview {
  width: 300px;
  height: 300px;
}
</style>
