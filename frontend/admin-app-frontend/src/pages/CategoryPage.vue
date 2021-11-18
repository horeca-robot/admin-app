<template>
    <div class="page">
      <form class="panel" @submit="onSubmit">
        <div class="panel-side">
          <div class="blocks">
              <label class="text"> Name:</label>
              <input class="inputs" placeholder="Category Name" v-model="catName" name="catName"/>
            </div>
            <div class="blocks">
              <label class="text"> Image:</label>
              <div class="imageCom">
                <ImagePreview ref="image" />
              </div>
            </div>
        </div>
        <div class="panel-side">
          <div class="blocks">
            <label class="text"> Select Parent-Categories:</label>
            <div class="categorieHolder"> 
                <div class="categories" v-for="category in categories" :key="category.id"> 
                    <input type="checkbox" v-model="category.selected"> <label>{{category.name}}</label>
                </div>
            </div>
          </div>
            <div class="blocks-row-buttons">
              <input class="button formbuttonSave" type="submit" value="Save"/>
              <button class="button formbuttonDelete" @click="deleteCategory">Delete</button>
            </div>
        </div>
      </form>  
    </div>
</template> 

<script>
import CategoryWrapper from '../wrappers/CategoryWrapper'
import ImagePreview from '../components/ImagePreview.vue'

export default {
    data()
    {
      return{
          id: '',
          isEditing: false,
          catName: '',
          categories: [],
      }
    },
    created(){
      this.id = this.$route.query.id

      this.getAllCategories();

      if(this.id){
          this.isEditing = true
          this.getCategoryInfo()
      }
    },
    components:{
      ImagePreview
    },
    methods: {
      async getAllCategories(){
        const response = await CategoryWrapper.getCategories()
        this.categories = response.categories
        this.categories.forEach(function (category) {
                category.selected = false
            })
      },

      async getCategoryInfo(){
        const response = await CategoryWrapper.getCategoryById(this.id);
        this.setCategoryValues(response.category)
      },

      setCategoryValues(category){
        this.id = category.id
        this.catName = category.name

        this.categories.forEach(function (c) {

            if(category.parentCategories.some(i => i === c.id)){
                c.selected = true
            }
        })

        this.categories = this.categories.filter(function(value){ 
         return value.id !== category.id;
         });

        if(category.image){
            this.$refs.image.setBase64(category.image)
        }
        },

      onSubmit(e){
        e.preventDefault()

        if(!this.catName){
          alert('Not all required fields are filled in, please check again')
          return
        }

        if(!this.isEditing){
          this.addCategory()
        }
        else{
          this.updateCategory()
        }

        this.resetValues()
        this.$router.push('category')

      },

      async addCategory(){
        const payload =
            {
              name: this.catName,
              image : this.$refs.image.base64,
              parentCategories: this.categories.filter(i => i.selected).map(i => i.id)
            }

        const response = await CategoryWrapper.postCategory(payload)

        if(response.success){
          alert(`Successfully added ${payload.name} to the menu.`)
        }
        else
        {
          alert(response.message)
        }
      },

      async updateCategory(){
        const payload =
            {
              id: this.id,
              name: this.catName,
              image : this.$refs.image.base64,
              parentCategories: this.categories.filter(i => i.selected).map(i => i.id)
            }

        const response = await CategoryWrapper.putCategory(payload)

        if(response.success){
          alert(`Successfully updated ${payload.name}.`)
        }
        else
        {
          alert(response.message)
        }
      },

      async deleteCategory(e){
            e.preventDefault()

            if(!this.isEditing){
                this.$router.push('menu')
                return
            }

            if(confirm(`Are you sure you wan't to delete ${this.catName} from the menu?`)){
                await CategoryWrapper.deleteCategory(this.id)
                this.$router.push('menu')
                alert(`Succesfully deleted ${this.catName}.`)
                this.resetValues()
            }
        },
      
      resetValues(){
        this.catName = ''
        this.categories.forEach(function (category) {
                category.selected = false
            })
      }
    }
}
</script>

<style scoped>

.page{
  top:0;
  bottom: 0;
  right: 0;
  margin: auto;
  height: fit-content;
  width: fit-content;
}

.panel{
        min-width: 500px;
        width: 31vw;
        min-height: fit-content;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        justify-content: space-between;
}

.panel-side{
display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 30px;
}

.backpanel{
  background-color: var(--secondary-color);
  width: 35vw;
  height: fit-content;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
  display: flex;
}

.container{
  display: inline-block;
  width: 100%;
}

.leftBlock{
  float:left;
  width: 45%;
  min-height: 10px;
}

.text{
  font-size: 1.5rem;
  margin-bottom: 2px;
}

.blocks{
  display: flex;
  flex-direction: column;
}

.blocks-row-buttons{
  margin-top:20px;
  margin-left: -5%;
  display: flex;
  flex-direction: row;
}

.button{
  width: 50px;
  background-color: var(--primary-color);
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  color: white;
  margin-left: 1%;
  font-size: 1em;
  font-family: inherit;
}

.button:hover{
  cursor: pointer;
}

.formbuttonSave{
  margin-left: 10%;
  height: 3vh;
  width: 5vw;
  font-size: 1rem;
}

input[type=checkbox] {
  transform: scale(1.3);
}

.formbuttonDelete{
  margin-left: 10%;
  height: 3vh;
  width: 5vw;
  background-color: white;
  color: var(--primary-color);
  font-size: 1rem;
}

.inputs{
  font-family: inherit;
  font-size: 1rem;
  border: 2px solid var(--primary-color);
  border-radius: 5px;
  width: 220px;
  height: 30px;
}

.imageCom{
  border: 2px solid var(--primary-color);
  border-radius: 5px;
}

.inputs:focus{
  outline: none;
}

.categorieHolder{
        padding: 10px;
        height: 150px;
        width: 200px;
        overflow: auto;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
    }

.categories{
    float: left;
    height: auto;
    width: 100%;
    color: var(--text-color);
}
</style>