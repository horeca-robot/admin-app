<template>
    <form id="panel" @submit.prevent="onSubmit">
        <div class="panel-side">
            <div class="product-property">
                <label class="property-title">Name: (Required)</label>
                <input v-model="name" type="text" placeholder="Name..."/>
            </div>
            <div class="product-property">
                <label class="property-title">Description:</label>
                <textarea v-model="description" placeholder="Description..."/>
            </div>
            <div class="product-property">
                <label class="property-title">Price: (Required)</label>
                <input v-model="price" type="number" placeholder="Price..." step="any"/>
            </div>
            <div class="product-property">
                <label class="property-title">Discount Price:</label>
                <input v-model="discountPrice" type="number" placeholder="Discount..." step="any"/>
            </div>
            <div class="product-property">
                <div>
                    <label class="property-title">Contains Alcohol:</label> 
                    <input v-model="alcohol" type="checkbox"/>
                </div>
            </div>
            <div class="product-property">
                <div>
                    <label class="property-title">Is A By-Product:</label> 
                    <input v-model="canBeServedAsByProduct" type="checkbox" @click="byProducts.forEach(p => p.selected = false)"/>
                </div>
            </div>
            <div class="product-property">
                <label class="property-title">Image:</label> 
                <ImagePreview ref="image"/>
            </div>
            <div id="buttons">
                <input class="button save" type="submit" value="Save" />
                <button class="button delete" @click="deleteProduct()">Delete</button>
            </div>
        </div>
        <div class="panel-side">
            <div class="product-property">
                <label class="property-title">Ingredients:</label> 
                <div class="property-list">
                    <div class="list-entry" v-for="ingredient in displayIngredients.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="ingredient.id">
                        <input type="checkbox" v-model="ingredient.selected" /><label>{{ingredient.name}}</label>
                        <select v-if="ingredient.selected" v-model="ingredient.required">
                            <option :value=true>Required</option>
                            <option :value=false>Not Required</option>
                        </select>
                    </div>
                </div>
                <div class="list-search">
                    <input type="text" @input="searchIngredients" placeholder="Search Ingredient..." v-model="ingredient" />
                    <button class="button" @click="createIngredient">Add</button>
                </div>
            </div>
            <div class="product-property">
                <label class="property-title">Tags:</label> 
                <div class="property-list">
                    <div class="list-entry" v-for="tag in displayTags.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="tag.id">
                        <input type="checkbox" v-model="tag.selected" /><label>{{tag.name}}</label>
                    </div>
                </div>
                <div class="list-search">
                    <input type="text" @input="searchTags" placeholder="Search Tag..." v-model="tag" />
                    <button class="button" @click="createTag">Add</button>
                </div>
            </div>
            <div class="product-property">
                <label class="property-title">Categories:</label> 
                <div class="property-list">
                    <div class="list-entry" v-for="category in categories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="category.id">
                        <input type="checkbox" v-model="category.selected" /><label>{{category.name}}</label>
                    </div>
                </div>
            </div>
            <div class="product-property">
                <label class="property-title">By-Products:</label> 
                <div class="property-list">
                    <div class="list-entry" v-for="product in byProducts.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="product.id">
                        <input v-if="!this.canBeServedAsByProduct" type="checkbox" v-model="product.selected" /><label>{{product.name}}</label>
                    </div>
                </div>
            </div>
        </div>
    </form>
</template>

<script>
import ProductWrapper from '../wrappers/ProductWrapper'
import CategoryWrapper from '../wrappers/CategoryWrapper'
import notification from '../utils/NotificationUtil'
import ImagePreview from '../components/ImagePreview.vue'
import TagWrapper from '../wrappers/TagWrapper'
import IngredientWrapper from '../wrappers/IngredientWrapper'

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
            canBeServedAsByProduct: false,
            tag: '',
            ingredient: '',
            categories: [],
            byProducts: [],
            displayTags: [],
            tags: [],
            displayIngredients: [],
            ingredients: []
        }
    },
    components: {
        ImagePreview
    },
    async created(){
        await this.getCategories()
        await this.getByProducts()
        await this.getTags();
        await this.getIngredients();
        this.displayTags = this.tags;
        this.displayIngredients = this.ingredients;

        this.id = this.$route.query.id

        if(this.id){
            this.isEditing = true
            this.getProductInfo()
        }
    },
    methods: {
        async getCategories(){
            const response = await CategoryWrapper.getCategories()
            this.categories = response.categories.filter(c => !c.childCategories.length)
            this.categories.forEach(function (category) {
                category.selected = false
            })
        },

        async getByProducts(){
            const response = await ProductWrapper.getProducts()
            this.byProducts = response.products.filter(p => p.canBeServedAsByProduct)
            this.byProducts.forEach(function (product) {
                product.selected = false
            })
        },

        async getTags(){
            const response = await TagWrapper.getTags()
            this.tags = response.tags
            this.tags.forEach(function (tag) {
                tag.selected = false
            })
        },

        async getIngredients(){
            const response = await IngredientWrapper.getIngredients()
            this.ingredients = response.ingredients
            this.ingredients.forEach(function (ingredient) {
                ingredient.selected = false
                ingredient.required = false
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
            this.canBeServedAsByProduct = product.canBeServedAsByProduct

            this.categories.forEach(function (category) {
                if(product.categories.some(i => i === category.id)){
                    category.selected = true
                }
            })

            this.byProducts.forEach(function(byProduct, index, object) {
                if(product.id === byProduct.id){
                    console.log('ait')
                    object.splice(index, 1)
                }
                if(product.byProducts.some(i => i === byProduct.id)){
                    byProduct.selected = true
                }
            })

            this.tags.forEach(function (tag) {
                if(product.tags.some(i => i.id === tag.id)){
                    tag.selected = true
                }
            })

            this.displayTags = this.tags;
            console.log(this.ingredients)
            console.log(product.ingredients)
            this.ingredients.forEach(function (ingredient) {
                if(product.ingredients.some(i => i.id === ingredient.id)){
                    ingredient.selected = true
                    ingredient.required = product.ingredients.find(i => i.id === ingredient.id).required
                }
            })

            this.displayIngredients = this.ingredients;

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
            const ingredients = new Map();
            this.ingredients.filter(i => i.selected).map(i => i.id)
                .forEach(i => ingredients.set(i, i.required));

            const payload = {
                name: this.name,
                price: this.price,
                discountPrice: this.discountPrice,
                description: this.description,
                containsAlcohol: this.alcohol,
                canBeServedAsByProduct: this.canBeServedAsByProduct,
                image : this.$refs.image.base64,
                categories: this.categories.filter(i => i.selected).map(i => i.id),
                byProducts: this.byProducts.filter(i => i.selected).map(i => i.id),
                tags: this.tags.filter(i => i.selected).map(i => i.id),
                ingredients: Object.fromEntries(ingredients)
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
            const ingredients = new Map();
            this.ingredients.filter(i => i.selected)
                .forEach(i => ingredients.set(i.id, i.required));

            const payload = {
                id: this.id,
                name: this.name,
                description: this.description,
                price: this.price,
                discountPrice: this.discountPrice,
                containsAlcohol: this.alcohol,
                canBeServedAsByProduct: this.canBeServedAsByProduct,
                image: this.$refs.image.base64,
                categories: this.categories.filter(i => i.selected).map(i => i.id),
                byProducts: this.byProducts.filter(i => i.selected).map(i => i.id),
                tags: this.tags.filter(i => i.selected).map(i => i.id),
                ingredients: Object.fromEntries(ingredients)
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
                notification.showErrorNotification('A tag with this name already exists')
                return
            }

            var result = await TagWrapper.postTag({
                name: this.tag
            });

            if (!result.success) {
                notification.showErrorNotification(result.message)
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

        async createIngredient(e){
            e.preventDefault()

            if (this.ingredients.some(t => t.name.toLowerCase() === this.ingredient.toLowerCase())) {
                notification.showErrorNotification('A ingredient with this name already exists')
                return
            }

            var result = await IngredientWrapper.postIngredient({
                name: this.ingredient
            });

            if (!result.success) {
                notification.showErrorNotification(result.message)
                return;
            }

            this.ingredient = '';

            var selectedIngredients = this.ingredients.filter(i => i.selected)
                .map(i => i.id);

            await this.getIngredients();

            this.ingredients.forEach(i => {
                if (selectedIngredients.some(j => j === i.id)) {
                    i.selected = true;
                }
            })

            this.displayIngredients = this.ingredients;
        },

        async searchIngredients(){
            this.displayIngredients = this.ingredients.filter(i => i.name.toLowerCase().includes(this.ingredient.toLowerCase()));
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
                notification.showSuccessNotification(`Succesfully deleted ${this.name}.`)
                this.resetValues()
            }
        },

        async resetValues(){
            await this.getByProducts()
            
            this.id = ''
            this.isEditing = false
            this.name = ''
            this.description = ''
            this.price = 0
            this.discountPrice = 0
            this.alcohol = false
            this.canBeServedAsByProduct = false
            this.categories.forEach(function (category) {
                category.selected = false
            })
            this.byProducts.forEach(function (product) {
                product.selected = false
            })
            this.tags.forEach(function (tag) {
                tag.selected = false
            })
            this.displayTags = this.tags;
            this.ingredients.forEach(function (ingredient) {
                ingredient.selected = false
                ingredient.required = false
            })
            this.displayIngredients = this.ingredients;
            this.$refs.image.setBase64(null);
        }
    }
}
</script>

<style scoped>
    #panel{
        top:0;
        bottom: 0;
        right: 0;
        margin: auto;
        padding: 20px;
        width: 30vw;
        height: fit-content;
        background-color: var(--secondary-color);
        border-radius: 10px;
        display: flex;
        justify-content: space-between;
    }

    .panel-side{
        width: 45%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
    }

    .product-property{
        display: flex;
        flex-direction: column;
        margin-bottom: 10px;
    }

        .product-property div{
            display: flex;
            align-items: center;
        }

    .property-title{
        font-size: 1.5rem;
        margin-bottom: 2px;
        color: var(--text-color);
    }

    input[type=text]{
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 5px;
        font-family: inherit;
        font-size: 1.2em;
    }

    textarea{    
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 5px;
        font-family: inherit;
        font-size: 1.2em;
        resize: none;
        height: 75px;
    }

    input[type=number]{
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        padding: 5px;
        font-family: inherit;
        font-size: 1.2em;
    }

    input[type=checkbox]{
        transform: scale(1.3);
        margin-left: 10px;
    }

    #buttons{
        width: 100%;
        display: flex;
        justify-content: space-between;
        margin: 10px 0px;
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

    .save{
        background-color: var(--primary-color);
        color: var(--secondary-color);
    }

    .delete{
        background-color: var(--secondary-color);
        color: var(--primary-color);
    }
    
    .property-list{
        padding: 10px;
        height: 100px;
        overflow: auto;
        border: 2px solid var(--primary-color);
        border-radius: 5px;
        display: flex;
        flex-direction: column;
    }

    .list-entry{
        height: auto;
        width: 100%;
        color: var(--text-color);
    }

        .list-entry label{
            margin-left: 5px;
        }

        .list-entry select{
            border: 1px solid var(--primary-color);
            border-radius: 5px;
            padding: 2px;
            font-family: inherit;
            background-color: var(--primary-color);
            color: var(--secondary-color);
            outline: none;
            margin-left: 5px;
            margin-bottom: 3px;
        }

        .list-entry input[type=checkbox]{
            margin-left: 0 !important;
        }

    .list-search{
        display: flex;
        align-items: stretch !important;
        margin-top: 5px;
        justify-content: space-between;
    }

        .list-search input{
            width: 50%;
            font-size: 1em;
        }

        .list-search button{
            background-color: var(--primary-color);
            color: var(--secondary-color);
            padding: 0px 5px;
            width: 30%;
            border: 2px solid var(--primary-color);
        }
</style>