<template>
    <div class="page">
        <form class="backpanel" @submit="onSubmit">
            <div class="container">
                <div class="leftBlock">
                    <div class="blocks">
                        <label class="text"> *Title:</label>
                        <input class="inputs" placeholder="Product Name..." v-model="name" name="name"/>
                    </div>
                    <div class="blocks">
                    <label class="text"> Description:</label>
                    <textarea class="textareas" placeholder="Product Description..." v-model="description"/>
                    </div>
                    <div class="blocks">
                        <label class="text"> *Price:</label>
                        <input class="inputs" type="number" placeholder="Price" name="price" v-model="price" step="any"/>
                    </div>
                    <div class="blocks">
                        <label class="text"> Discount price:</label>
                        <input class="inputs" type="number" placeholder="Discount price" name="discountPrice" v-model="discountPrice" step="any"/>
                    </div>
                    <div class="block-contains-alcohol">
                        <label class="text"> Contains Alcohol:</label>
                        <input class="checkbox" type="checkbox" name="alcohol" v-model="alcohol"/>
                    </div>
                    <div class="blocks imageComponent">
                        <label class="text"> Image:</label>
                        <ImagePreview ref="image"/>
                    </div>
                </div>
                <div class="rightBlock"> 
                    <div class="blocks">
                        <label class="text"> Ingredients:</label>
                        <label class="extraLabel"/>
                    </div>
                    <div class="blocks-row">
                        <input class="inputs inputsExtra" type="text" name="ingredients" placeholder="Ingredient..."/>
                        <button class="button">Add</button>
                    </div>
                    <div class="blocks">
                        <label class="text"> Tags:</label>
                        <div class="categorieHolder">
                            <div class="categories" v-for="tag in displayTags" :key="tag.id">
                                <input type="checkbox" v-model="tag.selected"> <label>{{tag.name}}</label>
                            </div>
                        </div>
                    </div>
                    <div class="blocks-row">
                        <input class="inputs inputsExtra" @input="searchTags" placeholder="Tag..." v-model="tag" />
                        <button class="button" @click="createTag">Add</button>
                    </div>
                    <div class="blocks">
                        <label class="text"> Select Categories:</label>
                        <div class="categorieHolder"> 
                            <div class="categories" v-for="category in categories" :key="category.id"> 
                                <input type="checkbox" v-model="category.selected"> <label>{{category.name}}</label>
                            </div>
                        </div>
                    </div>
                    <div class="blocks-row-buttons">
                        <input class="button formbuttonSave" type="submit" value="Save"/>
                        <button class="button formbuttonDelete" @click="deleteProduct">Delete</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
import ProductWrapper from '../wrappers/ProductWrapper'
import CategoryWrapper from '../wrappers/CategoryWrapper'
import notification from '../utils/NotificationUtil'
import ImagePreview from '../components/ImagePreview.vue'
import TagWrapper from '../wrappers/TagWrapper'

export default {
    data(){
        return{
            id: '',
            isEditing: false,
            name: '',
            description: '', 
            price: 0,
            discountPrice: 0, 
            alcohol: false,
            tag: '',
            categories: [],
            displayTags: [],
            tags: []
        }
    },
    components: {
        ImagePreview
    },
    async created(){
        await this.getCategories()
        await this.getTags();
        this.displayTags = this.tags;

        this.id = this.$route.query.id

        if(this.id){
            this.isEditing = true
            this.getProductInfo()
        }
    },
    methods: {
        async getCategories(){
            const response = await CategoryWrapper.getCategories()
            this.categories = response.categories
            this.categories.forEach(function (category) {
                category.selected = false
            })
        },

        async getTags(){
            const response = await TagWrapper.getTags()
            this.tags = response.tags
            this.tags.forEach(function (tag) {
                tag.selected = false
            })
        },

        async getProductInfo(){
            const response = await ProductWrapper.getProductById(this.id)
            this.setProductValues(response.product)
        },

        setProductValues(product){
            this.id = product.id
            this.name = product.name
            this.description = product.description
            this.price = product.price
            this.discountPrice = product.discountPrice
            this.alcohol = product.containsAlcohol

            this.categories.forEach(function (category) {
                if(product.categories.some(i => i === category.id)){
                    category.selected = true
                }
            })

            this.tags.forEach(function (tag) {
                if(product.tags.some(i => i.id === tag.id)){
                    tag.selected = true
                }
            })

            this.displayTags = this.tags;

            if(product.image){
                this.$refs.image.setBase64(product.image)
            }
        },

        onSubmit(e){
            e.preventDefault()

            if(!this.name || !this.price){
                notification.showErrorNotification('Not all required fields are filled in.')
                return
            }

            if(!this.isEditing){
                this.addProduct()
            }
            else{
                this.updateProduct()
            }

            this.resetValues()
            this.$router.push('product')

        },

        async addProduct(){
            const payload = {
                name: this.name,
                price: this.price,
                discountPrice: this.discountPrice,
                description: this.description,
                containsAlcohol: this.alcohol,
                image : this.$refs.image.base64,
                categories: this.categories.filter(i => i.selected).map(i => i.id),
                tags: this.tags.filter(i => i.selected).map(i => i.id)
            }
            
            const response = await ProductWrapper.postProduct(payload)

            if(response.success){
                notification.showSuccessNotification(`Succesfully added ${payload.name} to the menu.`)
            }
            else{
                notification.showErrorNotification(response.message)
            }
        },

        async updateProduct(){
            const payload = {
                id: this.id,
                name: this.name,
                description: this.description,
                price: this.price,
                discountPrice: this.discountPrice,
                containsAlcohol: this.alcohol,
                image: this.$refs.image.base64,
                categories: this.categories.filter(i => i.selected).map(i => i.id),
                tags: this.tags.filter(i => i.selected).map(i => i.id)
            }
            
            const response = await ProductWrapper.putProduct(payload)

            if(response.success){
                notification.showSuccessNotification(`Succesfully updated ${payload.name}.`)
            }
            else{
                notification.showErrorNotification(response.message)
            }
        },

        async createTag(e){
            e.preventDefault()

            if (this.tags.some(t => t.name.toLowerCase() === this.tag.toLowerCase())) {
                alert('A tag with this name already exists')
                return
            }

            var result = await TagWrapper.postTag({
                name: this.tag
            });

            if (!result.success) {
                alert(result.message);
                return;
            }

            this.tag = '';

            var selectedTags = this.tags.filter(t => t.selected)
                .map(t => t.id);

            await this.getTags();

            this.tags.forEach(t => {
                if (selectedTags.some(s => s === t.id)) {
                    t.selected = true;
                }
            })

            this.displayTags = this.tags;
        },

        async searchTags(){
            this.displayTags = this.tags.filter(t => t.name.toLowerCase().includes(this.tag.toLowerCase()));
        },

        async deleteProduct(e){
            e.preventDefault()

            if(!this.isEditing){
                this.$router.push('menu')
                return
            }

            if(confirm(`Are you sure you wan't to delete ${this.name} from the menu?`)){
                await ProductWrapper.deleteProduct(this.id)
                this.$router.push('menu')
                notification.showErrorNotification(`Succesfully deleted ${this.name}.`)
                this.resetValues()
            }
        },

        resetValues(){
            this.id = ''
            this.isEditing = false
            this.name = ''
            this.description = ''
            this.price = 0
            this.discountPrice = 0
            this.alcohol = false
            this.categories.forEach(function (category) {
                category.selected = false
            })
            this.tags.forEach(function (tag) {
                tag.selected = false
            })
            this.displayTags = this.tags;
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

    .backpanel{
        background-color: var(--secondary-color);
        border-color: var(--primary-color);
        border-width: 30px;
        width: 35vw;
        height: fit-content;
        border-radius: 10px;
        padding-bottom: 40px;
        padding-left: 30px;
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

    .rightBlock{
        float:right;
        width: 45%;
        min-height: 10px;
    }

    .text{
        font-size: 1.5rem;
        margin-bottom: 2px;
        color: var(--text-color);
    }


    .blocks{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: column;
    }

    .blocks-row{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: row;
    }

    .block-contains-alcohol{
        margin-top:20px;
        margin-left: 15px;
        display: flex;
        flex-direction: row;
        align-items: center;
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
        color: var(--secondary-color);
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
        background-color: transparent;
        color: var(--primary-color);
        font-size: 1rem;
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

    .textareas{
        font-family: inherit;
        font-size: 1rem;
        resize: none;
        border-radius: 5px;
        border: 2px solid var(--primary-color);
        width: 220px;
        height: 80px;
    }

    .extraLabel{
        width: 220px;
        display: block;
        min-height: 80px;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
    }

    .textareas:focus{
        outline:none;
    }

    .checkboxes{
        background-color: blue;
    }

    .inputs{
        font-family: inherit;
        font-size: 1rem;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        width: 220px;
        height: 30px;
    }

    .imageComponent{
        height: 300px;
    }

    .imageCom{
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        height: 300px;
    }

    .inputsExtra{
        width:160px;
    }

    .inputs:focus{
        outline: none;
    } 
    
</style>