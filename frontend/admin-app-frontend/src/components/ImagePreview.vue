<template>
  <div>
    <img id="preview" :src="base64" />
    <label for="file-upload" id="upload" class="custom-file-upload">Upload Image</label>
    <input
      @input="previewImage"
      ref="fileInput"
      type="file"
      accept="image/png, image/gif, image/jpeg"
      id="file-upload"
    />
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

    async setBase64(b64) {
      this.base64 = b64
    }
  },
};
</script>

<style scoped>
div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  width: fit-content;
}

#preview {
  width: 150px;
  height: 150px;
  object-fit: cover;
}

input[type=file]{
  display: none;
}

#upload{
  margin-top: 10px;
  width: calc(100% - 20px);
  display: flex;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  font-size: 1em;
  font-family: inherit;
  padding: 5px;
  background-color: var(--primary-color);
  color: var(--secondary-color);
  transition: 0.2s ease transform;
  justify-content: center;
}

  #upload:hover{
    transform: scale(1.05);
    cursor: pointer;
    box-shadow: 2px 2px 5px 0px rgb(0 0 0 / 50%);
  }
</style>
