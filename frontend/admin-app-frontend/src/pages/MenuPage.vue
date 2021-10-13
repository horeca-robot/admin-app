<template>
    <div>
        <AddProduct @add-product="AddProduct"/>
        <button @click="GetProducts">Get</button>
    </div>
</template>

<script>
import AddProduct from '../components/menuComponents/AddProduct.vue'
import axios from 'axios'

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
        async AddProduct(product){
            // this.products = [...this.products, product]
            console.log(product)
            // TODO Change connection string here 
            const response = await axios.post("http://localhost:5000/products", product)
                    .then(response =>{this.products = response.data})
                    .catch(error => console.log(error.response))

            console.log(response)
        },

        async GetProducts(){
            // TODO Change connection string here 
            await axios.get("http://localhost:5000/products")
                .then(response => {this.products = response.data, console.log(this.products.length)})
                .catch(error => console.log(error))

        }
    },

    created(){
        this.products = this.GetProducts()
        console.log("Hello")
        console.log(this.products.length)
        for(var i =0; i < this.products.length; i++){
            console.log(this.products.product.name)
        }
    }
}
</script>
