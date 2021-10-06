<template>
    <div>
        <AddProduct @add-product="AddProduct"/>
    </div>
</template>

<script>
import AddProduct from '../components/menuComponents/AddProduct.vue'

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
        AddProduct(product){
            this.products = [...this.products, product]
            console.log(product)
        }
    }
}
</script>
