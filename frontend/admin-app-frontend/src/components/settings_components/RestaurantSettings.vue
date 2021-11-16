<template>
  <div id="grid-container">
    <div class="grid container-element">
      <div>
        <label for="restaurantname">Restaurant Name</label>
        <input id="restaurantname" v-model="restaurantName" type="text" />
      </div>

      <div>
        <label for="contactperson">Contact Person</label>
        <input id="contactperson" v-model="contactName" type="text" />
      </div>

      <div>
        <label for="openingtime">Opening Time</label>
        <input id="openingtime" v-model="openingTime" type="time" />
      </div>

      <div>
        <label for="contactemail">Contact Email</label>
        <input id="contactemail" v-model="contactEmail" type="email" />
      </div>

      <div>
        <label for="closingtime">Closing Time</label>
        <input id="closingtime" v-model="closingTime" type="time" />
      </div>

      <div>
        <label for="contactphone">Contact Phone</label>
        <input id="contactphone" v-model="contactPhone" type="tel" />
      </div>

      <div>
        <button @click="handleSave">Save Settings</button>
      </div>
    </div>
    <div id="img-preview" class="container-element">
      <label for="logo">Restaurant Logo</label>
      <ImagePreview ref="logo"/>
    </div>
  </div>
</template>

<script>
import ImagePreview from "../ImagePreview.vue";
import api from "../../wrappers/InfoWrapper.js";

export default {
  async mounted(){
      let response = await api.getRestaurantSettings()

      if(response.data.data.info) {
          let info = response.data.data.info
          
          this.restaurantName = info.name
          this.openingTime = info.openingTime
          this.closingTime = info.closingTime
          this.contactName = info.contactPersonName
          this.contactEmail = info.contactPersonEmail
          this.contactPhone = info.contactPersonPhone

          if(info.restaurantLogo)
            this.$refs.logo.setBase64(info.restaurantLogo)
      }
  },
  data(){
      return {
          restaurantName : '',
          openingTime : '',
          closingTime : '',
          contactName : '',
          contactEmail : '',
          contactPhone : ''
      }
  },
  methods : {
      async handleSave () {
          const payload = {
              name : this.restaurantName,
              restaurantLogo : this.$refs.logo.base64,
              openingTime : this.openingTime,
              closingTime : this.closingTime,
              contactPersonName : this.contactName,
              contactPersonEmail : this.contactEmail,
              contactPersonPhone : this.contactPhone
          }

          await api.putRestaurantSettings(payload)
      }
  },
  components: {
    ImagePreview,
  },
};
</script>

<style scoped>
#grid-container {
  display: flex;
}
.container-element {
  display: inline-block;
}
#img-preview {
  float: right;
  width: 25%;
}
.grid {
  width: 75%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0px 0px;
  grid-auto-flow: row;
  grid-template-areas:
    ". ."
    ". ."
    ". .";
}
input,
label {
  display: block;
}

label {
  margin-bottom: 5px;
  margin-top: 10px;
  color: var(--text-color)
}

button {
  margin-top: 50px;
  min-width: 50%;
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