<template>
    <div class="page">
        <div class="panel">
            <div class="categories-box">
                <h1 class="box-title">{{text.MenuPage_Categories}}</h1>
                <div class="categories" v-if="categories.length">
                    <Category v-for="category in categories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="category.id" 
                    :id="category.id" 
                    :name="category.name" 
                    :childCategories="category.childCategories.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" 
                    :otherIsSelected="isOtherCategorySelected(category.id)" 
                    :isVisible="category.visible"
                    @selectCategory="selectCategory"/>
                </div>
                <h1 class="box-message" v-else>{{text.MenuPage_CatExist}}</h1>
                <div id="bottom-categories">
                    <button :class="selectedCategory === 'byProducts' ? 'categories by-products category-selected' : 'categories by-products'" @click="selectByProductCategory">ByProducts</button> <!-- vertaal !-->
                    <button :class="selectedCategory === 'archive' ? 'categories last-item category-selected' : 'categories last-item'" @click="selectArchivedCategory">{{text.MenuPage_Archive}}</button>
                </div>
            </div>
            <div class="products-box">
                <h1 class="box-title">{{text.MenuPage_Products}}</h1>
                <div class="products" v-if="selectedProducts.length">
                    <Product v-for="product in selectedProducts.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))" :key="product.id" 
                    :id="product.id" 
                    :name="product.name" 
                    :description="product.description" 
                    :image="product.image"/>
                </div>
                <h1 class="box-message" v-else-if="!selectedCategory">{{text.MenuPage_SelCat}}</h1>
                <h1 class="box-message" v-else-if="categories.find(c => c.id === selectedCategory) ? categories.find(c => c.id === selectedCategory).childCategories.length : false">{{text.MenuPage_SelChild}}</h1>
                <h1 class="box-message" v-else-if="!selectedProducts.length">{{text.MenuPage_CatEmpty}}</h1>
            </div>
        </div>
        <div class="buttons-box">
            <button class="add-button" @click="redirectToCategory">{{text.MenuPage_AddCat}}</button>
            <button class="add-button" @click="redirectToProduct">{{text.MenuPage_AddProd}}</button>
        </div>
    </div>
</template>

<script>
import Category from '../components/menu_components/Category.vue'
import Product from '../components/menu_components/Product.vue'
import CategoryWrapper from '../wrappers/CategoryWrapper'
import ProductWrapper from '../wrappers/ProductWrapper'
import notification from '../utils/NotificationUtil'
import LanguageUtil from '../utils/LanguageUtil'

export default {
    data() {
        return {
            text: LanguageUtil.getTextObject(),
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
        selectByProductCategory(){
            this.selectedCategory = 'byProducts'
            this.selectedProducts = this.products.filter(i => i.canBeServedAsByProduct)  
        },
        selectArchivedCategory(){
            this.selectedCategory = 'archive'
            this.selectedProducts = this.products.filter(i => !i.categories.length && !i.canBeServedAsByProduct)  
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

    #bottom-categories{
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
    }

    .by-products{
        height: 45px;
        color: var(--secondary-color);
        background: var(--primary-color);
        border: 0;
        margin-bottom: 0;
        display: flex;
        font-size: 1.25rem;
        padding: 12.5px 12.5px 12.5px 25px;
        opacity: 0.75;
        align-items: center;
        font-family: inherit;
    }  

        .by-products:hover{
            opacity: 1;
            cursor: pointer;
            background: var(--primary-color);
        }

    .last-item{
        height: 45px;
        color: var(--secondary-color);
        background: var(--notvisible-color);
        border: 0;
        margin-bottom: 0;
        display: flex;
        font-size: 1.25rem;
        padding: 12.5px 12.5px 12.5px 25px;
        opacity: 0.75;
        border-radius: 0 0 0 9px;
        align-items: center;
        font-family: inherit;
    }

        .last-item:hover{
            opacity: 1;
            cursor: pointer;
            background: var(--primary-color);
        }

    .category-selected{            
        opacity: 1 !important;
        background: var(--primary-color) !important;
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