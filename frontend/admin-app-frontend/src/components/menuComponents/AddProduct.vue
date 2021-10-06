<template>

    <form @submit="onSubmit">
        <h1>Menu </h1>
        <div>
        <label> Product name</label>
        <input placeholder="Product Name" v-model="name" name="name"/>   <br>
        </div>
        <div>
        <label> Image</label>
        <input type="file" id="img" name="img" accept="image/*" @change="SubmittedImage"/>  <br>
        </div>
        <div>
        <label> Price</label>
        <input type="number" placeholder="Price" name="price" v-model="price" step="any"/>   <br>
        </div>
        <div>
        <label> Discount price</label>
        <input type="number" placeholder="Discount price" name="discountPrice" v-model="discountPrice" step="any"/> <br>  
        </div>
        <div>
        <label> Description</label>
        <input type="text" placeholder="Description" name="description" v-model="description"/>   <br>
        </div>
        <div>
        <label> Alcohol</label>
        <input type="checkbox" name="alcohol" v-model="alcohol"/>   <br>
        </div>
        <div>
        <input type="submit" value="Save Product"/>
        </div>
    </form>
</template>

<script>
export default {
    name: 'AddProduct',
    data(){
        return{
            name: '',
            img: '',
            price: 0,
            discountPrice: 0, 
            description: '', 
            alcohol: false,
            products: []
        }
    },
    methods:{
        onSubmit(e){
            e.preventDefault()

            if(!this.name || !this.price || !this.discountPrice){
                alert('Not all required fields are filled in, please check again')
                return
            }

            const newProduct = {
                name: this.name,
                price: this.price,
                discountPrice: this.discountPrice,
                description: this.description,
                alcohol: this.alcohol
            }

            this.$emit('add-product', newProduct)
            this.products = [...this.products, newProduct]
            console.log(this.products.length)

            this.name = ''
            this.price = 0
            this.discountPrice = 0
            this.description = ''
            this.alcohol = false

        },
    }
}
</script>
