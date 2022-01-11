<template>
  <form id="panel" @submit="onSubmit">
    <div class="panel-side">
      <div class="panel-section">
        <label>{{text.CatPage_Name}}</label>
        <input placeholder="Name..." v-model="name" type="text"/>
      </div>
      <div style="margin-bottom: 5px;">
        <label class="isVisible">{{text.CatPage_Visible}}</label><input type="checkbox" v-model="visible"/>
      </div>
      <div class="panel-section">
        <label>{{text.CatPage_Image}}</label>
        <ImagePreview ref="image" />
      </div>
    </div>
    <div class="panel-side">
      <div class="panel-section">
        <label>{{text.CatPage_Parent}}</label>
        <div id="categories">
          <div class="category" v-for="category in filteredCategories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="category.id"> 
              <input type="checkbox" v-model="category.selected"> 
              <label>{{ category.name }}</label>
          </div>
        </div>
      </div>
      <div id="buttons">
        <input class="button" id="save" type="submit" :value="text.CatPage_Save"/>
        <button class="button" id="delete" @click="deleteCategory">{{text.CatPage_Delete}}</button>
      </div>
    </div>
  </form>
</template> 

<script>
import CategoryWrapper from '../wrappers/CategoryWrapper'
import ImagePreview from '../components/ImagePreview.vue'
import notification from '../utils/NotificationUtil'
import LanguageUtil from '../utils/LanguageUtil'

export default {
    data() {
      return {
        text: LanguageUtil.getTextObject(),
        id: '',
        isEditing: false,
        name: '',
        visible: true,
        filteredCategories: [],
        allCategories: []
      }
    },
    components:{
      ImagePreview
    },
    async created() {
      this.id = this.$route.query.id

      await this.getAllCategories();

      if(this.id){
          this.isEditing = true
          this.getCategoryInfo()
      }
    },
    methods: {
      async getAllCategories(){
        const response = await CategoryWrapper.getCategories()
        this.allCategories = response.categories
        this.filteredCategories = response.categories.filter(c => !c.parentCategories.length && this.id !== c.id)
        this.filteredCategories.forEach(function (category) {
          category.selected = false
        })
      },

      async getCategoryInfo(){
        const response = await CategoryWrapper.getCategoryById(this.id);
        this.setCategoryValues(response.category)
      },

      setCategoryValues(category){
        this.visible = category.visible
        this.id = category.id
        this.name = category.name

        console.log(category.parentCategories)
        console.log(this.filteredCategories)

        this.filteredCategories.forEach(function (c) {
          if(category.parentCategories.some(i => i === c.id)){
            c.selected = true
          }
        })

        if(category.image){
          this.$refs.image.setBase64(category.image)
        }
      },

      async onSubmit(e){
        e.preventDefault()

        if(!this.name){
          notification.showErrorNotification(this.text.CatPage_Err1)
          return
        }

        if(this.filteredCategories.some(c => c.name.toLowerCase() === this.name.toLowerCase())){
          notification.showErrorNotification(this.text.CatPage_Err2)
          return
        }

        if(!this.isEditing){
          await this.addCategory()
        }
        else{
          await this.updateCategory()
        }

        this.resetValues()
        this.getAllCategories()
        this.$router.push('category')
      },

      async addCategory(){
        const payload = {
          name: this.name,
          image : this.$refs.image.base64,
          parentCategories: this.filteredCategories.filter(i => i.selected).map(i => i.id),
          visible: this.visible
        }

        const response = await CategoryWrapper.postCategory(payload)

        if(response.success){
          notification.showSuccessNotification(`${payload.name} ` + this.text.CatPage_Succes1)
        }
        else {
          notification.showErrorNotification(response.message)
        }
      },

      async updateCategory(){
        const payload = {
          id: this.id,
          name: this.name,
          image : this.$refs.image.base64,
          parentCategories: this.filteredCategories.filter(i => i.selected).map(i => i.id),
          visible: this.visible
        }

        const response = await CategoryWrapper.putCategory(payload)

        if(response.success){
          notification.showSuccessNotification(`${payload.name} ` + this.text.CatPage_Succes2)
        }
        else {
          notification.showErrorNotification(response.message)
        }
      },

      async deleteCategory(e){
        e.preventDefault()

        if(!this.isEditing){
          this.$router.push('menu')
          return
        }

        if(confirm(`${this.name} ` + this.text.CatPage_DelConfirm)){
          await CategoryWrapper.deleteCategory(this.id)
          this.$router.push('menu')
          notification.showSuccessNotification(`${this.name} ` + this.text.CatPage_DelSucces)
          this.resetValues()
        }
      },
      
      resetValues(){
        this.id = ''
        this.name = ''
        this.visible = true;
        this.filteredCategories.forEach(function (category) {
          category.selected = false
        })
        this.$refs.image.setBase64(null);
      }
    }
}
</script>

<style scoped>
#panel{ 
  top: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  min-width: 500px;
  width: 25vw;
  height: fit-content;
  background-color: var(--secondary-color);
  box-shadow: 5px 5px 5px 1px rgb(0 0 0 / 50%);
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  padding: 30px;
  color: var(--text-color);
}

.panel-side{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: fit-content;
}

.panel-section{
  display: flex;
  flex-direction: column;
}

label{
  font-size: 1.5rem;
  margin-bottom: 2px;
}

input[type=text] {  
  font-family: inherit;
  font-size: 1rem;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  width: calc(100% - 14px);
  height: 20px;
  padding: 5px;
  margin-bottom: 5%;
}

#categories{
  padding: 10px;
  height: 150px;
  width: calc(100% - 24px);
  overflow-x: hidden;
  overflow-y: auto;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
}

.category{
  color: var(--text-color);
  display: flex;
  align-items: center;
}

input[type=checkbox]{
  transform: scale(1.3)
}

  .category label{    
    font-size: 1.3em !important;
    margin-left: 5px;
  }

#buttons{  
  display: flex;
  justify-content: space-between;
}

.button{
  width: 40%;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  font-size: 1.5em;
  font-family: inherit;
  cursor: pointer;
  padding: 5px;
}

.isVisible{
  margin-right: 5px;
}

#save{
  background-color: var(--primary-color);
  color: var(--secondary-color);
}

#delete{
  background-color: var(--secondary-color);
  color: var(--primary-color);
}
</style>