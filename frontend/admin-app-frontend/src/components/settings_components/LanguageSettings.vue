<template>
  <div class="language_col">
    <select  v-model="selected" @change="setLanguage()">
      <option v-for="language in Object.keys(languages)" v-bind:key="language">{{ language }}</option>
    </select>
  </div>
</template>

<script>
import languages from "../../assets/languages/languages.json";
import LanguageUtil from '../../utils/LanguageUtil';

export default {
  data(){
    return{
      text: LanguageUtil.getTextObject(),
      selected: 'English',
      languages: languages,
    }
  },
  created(){
      this.getLanguage();
  },
  methods:{
    getLanguage(){
      const language = localStorage.getItem('language');
      if(language){
        this.selected = language
      }
      else{
        localStorage.setItem("language", this.selected);
      }
    },
    setLanguage(){
      localStorage.setItem("language", this.selected);
      window.location.reload();
    }
  }
};
</script>

<style scoped>
.language_col
{
  border: 2px solid black;
  width: 200px;
  height: 100px;
  margin-top: 5px;
}
</style>