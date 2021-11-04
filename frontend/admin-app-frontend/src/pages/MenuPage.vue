<template>
    <div class="page">
        <div class="panel">
            <div class="categoriesSideMenu">
                
            </div>
            <AddProduct @add-product="addProduct" />
        </div>
        <button @click="getProducts">Get</button>
    </div>
</template>

<script>
import AddProduct from '../components/menuComponents/AddProduct.vue'
import ProductWrapper from '../wrappers/ProductWrapper.js'

export default {
    name: 'MenuPage',
    components: {
        AddProduct,
    },
    data() {
        return{
            products: [],
        }
    },
    methods: {
        async addProduct(product){
            const response = await ProductWrapper.postProduct(product)
            console.log(response)
        },

        async getProducts(){
            const response = await ProductWrapper.getProducts()
            console.log(response)
        }
    },
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
        width: 60vw;
        height: 60vh;
        padding: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
</style>