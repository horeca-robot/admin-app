<template>
    <div class="page">
        <div class="panel">
            <div class="categories-box">
                <h1 class="box-title">Categories</h1>
                <div class="categories" v-if="categories.length !== 0">
                    <Category v-for="category in categories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="category.id" 
                    :id="category.id" 
                    :name="category.name" 
                    :childCategories="category.childCategories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" 
                    :otherIsSelected="isOtherCategorySelected(category.id)" 
                    @selectCategory="selectCategory"/>
                </div>
                <h1 class="box-message" v-else>No existing categories.</h1>
            </div>
            <div class="products-box">
                <h1 class="box-title">Products</h1>
                <div class="products" v-if="selectedProducts.length !== 0">
                    <Product v-for="product in selectedProducts.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="product.id" 
                    :id="product.id" 
                    :name="product.name" 
                    :description="product.description" 
                    :image="product.image"/>
                </div>
                <h1 class="box-message" v-else-if="!selectedCategory">Select a category.</h1>
                <h1 class="box-message" v-else>Currently selected category doesn't contain any products.</h1>
            </div>
        </div>
        <div class="buttons-box">
            <button class="add-button" @click="redirectToCategory">Add Category</button>
            <button class="add-button" @click="redirectToProduct">Add Product</button>
        </div>
    </div>
</template>

<script>
import Category from '../components/menu_components/Category.vue'
import Product from '../components/menu_components/Product.vue'
import CategoryWrapper from '../wrappers/CategoryWrapper'
import ProductWrapper from '../wrappers/ProductWrapper'
import notification from '../utils/NotificationUtil'

export default {
    data() {
        return {
            categories: [],
            products: [],
            selectedCategory: '',
            selectedProducts: []
        }
    },
    components: {
        Category,
        Product
    },
    created(){
        this.getCategories()
        this.getProducts()
    },
    methods: {
        async getCategories(){
            const response = await CategoryWrapper.getCategories()

            if(response.success){
                this.categories = response.categories.filter(i => !i.parentCategories.length)
                this.categories.forEach(category => {
                    category.childCategories.forEach(child => {
                        child.selected = false;
                    });
                });
            }
            else{
                notification.showErrorNotification(response.message)
            }
        },
        async getProducts(){
            const response = await ProductWrapper.getProducts()

            if(response.success){
                this.products = response.products
            }
            else{
                notification.showErrorNotification(response.message)
            }
        },
        selectCategory(id){
            this.selectedCategory = id
            this.selectedProducts = this.products.filter(i => i.categories.some(x => x === id))
        },
        isOtherCategorySelected(categoryId){
            const childCategories = this.categories.find( ({ id }) => id === categoryId ).childCategories

            if(!this.selectedCategory){
                return false
            }
            else if(this.selectedCategory === categoryId){
                return false
            }
            else if(childCategories.length !== 0 && childCategories.some(i => i.id === this.selectedCategory)){
                return false
            }

            return true
        },
        redirectToCategory(){
            this.$router.push('category')
        },
        redirectToProduct(){
            if(!this.categories.length){
                notification.showErrorNotification("You'll need to add a category first.")
                return
            }

            this.$router.push('product')
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
        text-align: right;
    }

    .panel{
        min-width: 500px;
        width: 70vw;
        min-height: fit-content;
        height: 70vh;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
    }

    .categories-box{
        width: 20%;
        display: flex;
        flex-direction: column;
        align-items: center;
        border-right: 1px solid var(--text-color);
        text-align: center;
    }

    .products-box{
        width: 80%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .box-title{
        margin: 0;
        font-weight: 500;
        padding: 20px;
        color: var(--text-color);
    }

    .box-message{
        margin: 0;
        font-weight: 500;
        padding: 20px;
        color: var(--primary-color);
    }

    .categories{
        width: 100%;
    }

    .products{
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        align-content: flex-start;
        align-items: center;
        overflow-x: hidden;
        overflow-y: overlay;
    }
    
        .products::-webkit-scrollbar {
            width: 15px;
        }

        .products::-webkit-scrollbar-thumb {
            border: 5px solid var(--secondary-color);
            border-radius: 50px;
            background-color: var(--primary-color);
            background-clip: padding-box;
            cursor: pointer;
        }

    .buttons-box{
        width: 100%;
        margin-top: 2.5vh;
        display: flex;
        justify-content: space-between;
    }

    .add-button{
        padding: 15px 20px 15px 20px;
        color: var(--text-color);
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        font-size: 1.5rem;
        font-family: inherit;
        outline: none;
        border: none;
    }

    .add-button:hover{
        color: var(--primary-color);
            cursor: pointer;
    }
</style>