<template>
  <div id="grid-container">
    <div class="grid container-element">
      <div class="restaurant-details-wrapper">
        <div>
          <label for="restaurantname">Restaurant Name</label>
          <input id="restaurantname" v-model="restaurantName" type="text" />
        </div> 
        <div>
          <label for="contactperson">Contact Person</label>
          <input id="contactperson" v-model="contactName" type="text" />
        </div>
        <div>
          <label for="contactemail">Contact Email</label>
          <input id="contactemail" v-model="contactEmail" type="email" />
        </div>
        <div>
          <label for="contactphone">Contact Phone</label>
          <input id="contactphone" v-model="contactPhone" type="tel" />
        </div>
      </div>   
      <div class="days-open-wrapper">
        <label>Opening and Closing Time</label>
        <div class="days-open">
          <label>Monday</label>
          <input class="openingtime" v-model="openingTimeMonday" type="time" />
          <input class="closingtime" v-model="closingTimeMonday" type="time" />
        </div> 
        <div class="days-open">
          <label>Tuesday</label>
          <input class="openingtime" v-model="openingTimeTuesday" type="time" />
          <input class="closingtime" v-model="closingTimeTuesday" type="time" />
        </div>
        <div class="days-open">
          <label>Wednesday</label>
          <input class="openingtime" v-model="openingTimeWednesday" type="time" />
          <input class="closingtime" v-model="closingTimeWednesday" type="time" />
        </div>
        <div class="days-open">
          <label>Thursday</label>
          <input class="openingtime" v-model="openingTimeThursday" type="time" />
          <input class="closingtime" v-model="closingTimeThursday" type="time" />
        </div> 
        <div class="days-open">
          <label>Friday</label>
          <input class="openingtime" v-model="openingTimeFriday" type="time" />
          <input class="closingtime" v-model="closingTimeFriday" type="time" />
        </div> 
        <div class="days-open">
          <label>Saturday</label>
          <input class="openingtime" v-model="openingTimeSaturday" type="time" />
          <input class="closingtime" v-model="closingTimeSaturday" type="time" />
        </div>
        <div class="days-open">
          <label>Sunday</label>
          <input class="openingtime" v-model="openingTimeSunday" type="time" />
          <input class="closingtime" v-model="closingTimeSunday" type="time" />
        </div>
      </div>
    </div>
    <div class="image-preview-wrapper">
      <div id="img-preview" class="container-element">
        <label for="logo">Restaurant Logo</label>
        <ImagePreview ref="logo"/>
      </div>
      <div>
        <button @click="handleSave">Save Settings</button>
      </div>
    </div>
  </div>
</template>

<script>
import ImagePreview from "../ImagePreview.vue";
import api from "../../wrappers/InfoWrapper.js";

export default {
  async mounted() {
      let response = await api.getRestaurantSettings()

      if(response.data.data.info) {
          let info = response.data.data.info
          
          this.restaurantName = info.name
          this.contactName = info.contactPersonName
          this.contactEmail = info.contactPersonEmail
          this.contactPhone = info.contactPersonPhone

          this.openingTimeMonday = info.openingTimeMonday
          this.closingTimeMonday = info.closingTimeMonday
          this.openingTimeTuesday = info.openingTimeTuesday
          this.closingTimeTuesday = info.closingTimeTuesday
          this.openingTimeWednesday = info.openingTimeWednesday
          this.closingTimeWednesday = info.closingTimeWednesday
          this.openingTimeThursday = info.openingTimeThursday
          this.closingTimeThursday = info.closingTimeThursday
          this.openingTimeFriday = info.openingTimeFriday
          this.closingTimeFriday = info.closingTimeFriday
          this.openingTimeSaturday = info.openingTimeSaturday
          this.closingTimeSaturday = info.closingTimeSaturday
          this.openingTimeSunday = info.openingTimeSunday
          this.closingTimeSunday = info.closingTimeSunday

          if(info.restaurantLogo)
            this.$refs.logo.setBase64(info.restaurantLogo)
      }
  },
  data() {
      return {
          restaurantName : '',
          contactName : '',
          contactEmail : '',
          contactPhone : '',
          openingTime : '',
          closingTime : '',
          restaurantDays: [
                  {name: 'monday', openingTime: this.openingTimeMonday, closingTime: this.closingTimeMonday},
                  {name: 'tuesday', openingTime: this.openingTimeTuesday, closingTime: this.closingTimeTuesday},
                  {name: 'wedsnesday', openingTime: this.openingTimeWednesday, closingTime: this.closingTimeWednesday},
                  {name: 'thursday', openingTime: this.openingTimeThursday, closingTime: this.closingTimeThursday},
                  {name: 'friday', openingTime: this.openingTimeFriday, closingTime: this.closingTimeFriday},
                  {name: 'saturday', openingTime: this.openingTimeSaturday, closingTime: this.closingTimeSaturday},
                  {name: 'sunday', openingTime: this.openingTimeSunday, closingTime: this.closingTimeSunday}
                ]
      }
  },
  methods : {
      async handleSave () {
          const payload = {
              name : this.restaurantName,
              restaurantLogo : this.$refs.logo.base64,
              contactPersonName : this.contactName,
              contactPersonEmail : this.contactEmail,
              contactPersonPhone : this.contactPhone,
              restaurantDays: this.restaurantDays
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
}
.grid {
  width: 75%;
  display: grid;
  grid-template-columns: 1fr 1fr;
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
.days-open-wrapper {
 display: grid;
}
.days-open input, .days-open label {
  margin-right: 16px;
}
.days-open input {
  float: right;
  margin-top: 0;
}
.days-open label {
  float: left;
  margin-top: 3px;
}
.days-open {
  width: 70%;
}
.restaurant-details-wrapper label, .restaurant-details-wrapper input {
  margin-bottom: 16px;
}
</style>