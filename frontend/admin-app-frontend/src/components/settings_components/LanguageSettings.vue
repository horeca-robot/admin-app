<template>
  <div class="language_col">
    <!--  <div v-for="data in language" :key="data.id">
      <li> {{data}} </li>
    </div> !-->
    <ul>
      <li :key="language.id" v-for="language in languages"> {{language[0]}} </li>
    </ul>
  </div>
</template>

<script>
import jsonData from "../../assets/language.json"

export default {
  mounted()
  {
  },
  data()
  {
        return{
            languages: jsonData,
        }
  },
  methods : 
  {
  },
};
</script>

<style scoped>
.language_col
{
  border: 2px solid black;
  min-width: 200px;
  min-height: 50px;
  float: right;
}
</style>