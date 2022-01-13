<template>
  <div id="grid-container">
    <div class="grid container-element">
      <div class="restaurant-details-wrapper">
        <div>
          <label for="restaurantname">{{text.ResSet_RestaurantName}}</label>
          <input id="restaurantname" v-model="restaurantName" type="text" />
        </div>

        <div>
          <label for="contactperson">{{text.ResSet_ContactPerson}}</label>
          <input id="contactperson" v-model="contactName" type="text" />
        </div>

        <div>
          <label for="contactemail">{{text.ResSet_ContactEmail}}</label>
          <input id="contactemail" v-model="contactEmail" type="email" />
        </div>

        <div>
          <label for="contactphone">{{text.ResSet_ContactPhone}}</label>
          <input id="contactphone" v-model="contactPhone" type="tel" />
          </div>
        </div>   
      <div class="days-open-wrapper">
        <label>{{text.ResSet_OpenCloseTime}}</label>
        <div class="days-open">
          <label>{{text.WeekComp_Monday}}</label>
          <input class="openingtime" v-model="openingTimeMonday" type="time" />
          <input class="closingtime" v-model="closingTimeMonday" type="time" />
        </div> 
        <div class="days-open">
          <label>{{text.WeekComp_Tuesday}}</label>
          <input class="openingtime" v-model="openingTimeTuesday" type="time" />
          <input class="closingtime" v-model="closingTimeTuesday" type="time" />
        </div>
        <div class="days-open">
          <label>{{text.WeekComp_Wednesday}}</label>
          <input class="openingtime" v-model="openingTimeWednesday" type="time" />
          <input class="closingtime" v-model="closingTimeWednesday" type="time" />
        </div>
        <div class="days-open">
          <label>{{text.WeekComp_Thursday}}</label>
          <input class="openingtime" v-model="openingTimeThursday" type="time" />
          <input class="closingtime" v-model="closingTimeThursday" type="time" />
        </div> 
        <div class="days-open">
          <label>{{text.WeekComp_Friday}}</label>
          <input class="openingtime" v-model="openingTimeFriday" type="time" />
          <input class="closingtime" v-model="closingTimeFriday" type="time" />
        </div> 
        <div class="days-open">
          <label>{{text.WeekComp_Saturday}}</label>
          <input class="openingtime" v-model="openingTimeSaturday" type="time" />
          <input class="closingtime" v-model="closingTimeSaturday" type="time" />
        </div>
        <div class="days-open">
          <label>{{text.WeekComp_Sunday}}</label>
          <input class="openingtime" v-model="openingTimeSunday" type="time" />
          <input class="closingtime" v-model="closingTimeSunday" type="time" />
        </div>
      </div>
    </div>
    <div class="image-preview-wrapper">
      <div id="img-preview" class="container-element">
        <label for="logo">{{text.ResSet_RestaurantLogo}}</label>
        <ImagePreview ref="logo"/>
      </div>
      <div>
        <button @click="handleSave">{{text.ResSet_SaveSettings}}</button>
      </div>
    </div>
  </div>
</template>

<script>
import ImagePreview from "../ImagePreview.vue";
import api from "../../wrappers/InfoWrapper.js";
import LanguageUtil from '../../utils/LanguageUtil';

export default {
  async mounted() {
      let response = await api.getRestaurantSettings()

      if(response.data.data.info) {
          let info = response.data.data.info

          this.restaurantName = info.name
          this.contactName = info.contactPersonName
          this.contactEmail = info.contactPersonEmail
          this.contactPhone = info.contactPersonPhone

          for (let i = 0; i < info.openingPeriods.length; i++) {
            if(info.openingPeriods[i].dayOfWeek == 1) {
              this.openingTimeMonday = info.openingPeriods[i].openingTime
              this.closingTimeMonday = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 2) {
              this.openingTimeTuesday = info.openingPeriods[i].openingTime
              this.closingTimeTuesday = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 3) {
              this.openingTimeWednesday = info.openingPeriods[i].openingTime
              this.closingTimeWednesday = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 4) {
              this.openingTimeThursday = info.openingPeriods[i].openingTime
              this.closingTimeThursday  = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 5) {
              this.openingTimeFriday = info.openingPeriods[i].openingTime
              this.closingTimeFriday = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 6) {
              this.openingTimeSaturday = info.openingPeriods[i].openingTime
              this.closingTimeSaturday = info.openingPeriods[i].closingTime
            }
            if(info.openingPeriods[i].dayOfWeek == 0) {
              this.openingTimeSunday = info.openingPeriods[i].openingTime
              this.closingTimeSunday = info.openingPeriods[i].closingTime
            }
          }

          if(info.restaurantLogo)
            this.$refs.logo.setBase64(info.restaurantLogo)
      }
  },
  data() {
      return {
          text: LanguageUtil.getTextObject(),
          restaurantName : '',
          contactName : '',
          contactEmail : '',
          contactPhone : '',

          openingTimeMonday : '',
          openingTimeTuesday : '',
          openingTimeWednesday : '',
          openingTimeThursday : '',
          openingTimeFriday : '',
          openingTimeSaturday : '',
          openingTimeSunday : '',

          closingTimeMonday : '',
          closingTimeTuesday : '',
          closingTimeWednesday : '',
          closingTimeThursday : '',
          closingTimeFriday : '',
          closingTimeSaturday : '',
          closingTimeSunday : '',

          restaurantLogo : ''      
      }
  },
  methods : {
      async handleSave () {
          const payload = {
              name : this.restaurantName,             
              contactPersonName : this.contactName,
              contactPersonEmail : this.contactEmail,
              contactPersonPhone : this.contactPhone,
              openingPeriods: [
                  {dayOfWeek: 1, openingTime: this.openingTimeMonday, closingTime: this.closingTimeMonday},
                  {dayOfWeek: 2, openingTime: this.openingTimeTuesday, closingTime: this.closingTimeTuesday},
                  {dayOfWeek: 3, openingTime: this.openingTimeWednesday, closingTime: this.closingTimeWednesday},
                  {dayOfWeek: 4, openingTime: this.openingTimeThursday, closingTime: this.closingTimeThursday},
                  {dayOfWeek: 5, openingTime: this.openingTimeFriday, closingTime: this.closingTimeFriday},
                  {dayOfWeek: 6, openingTime: this.openingTimeSaturday, closingTime: this.closingTimeSaturday},
                  {dayOfWeek: 0, openingTime: this.openingTimeSunday, closingTime: this.closingTimeSunday}
              ],
              restaurantLogo : this.$refs.logo.base64,
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