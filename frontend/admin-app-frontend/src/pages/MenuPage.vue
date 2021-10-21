<template>
    <div>
        <AddProduct @add-product="addProduct" class="panel"/>
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
    .panel{
        background-color: pink;
    }
</style>