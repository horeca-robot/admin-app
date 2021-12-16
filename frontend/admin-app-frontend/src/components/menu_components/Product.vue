<template>
    <div class="product">
        <img :src="this.image ? this.image : 'https://p.kindpng.com/picc/s/79-798754_hoteles-y-centros-vacacionales-dish-placeholder-hd-png.png'"/>
        <div class="product-info">
            <b class="text">{{ name }}</b>
            <label class="text">{{ description }}</label>
            <label class="edit" @click="redirectToProduct">{{text.ProdComp_Edit}}</label>
        </div>
    </div>
</template>

<script>
import LanguageUtil from '../../utils/LanguageUtil';

export default {
    data(){
        return{
            text: LanguageUtil.getTextObject(),
        }
    },
    name: 'Product',
    props: {
        id: String,
        name: String,
        description: String,
        image: String
    },
    methods:{
        redirectToProduct(){
            this.$router.push(`product?id=${this.id}`)
        }
    }
}
</script>

<style scoped>
    .product{
        width: 40%;
        min-height: fit-content;
        display: flex;
        background-color: var(--secondary-color);
        border: 2px solid var(--primary-color);
        border-radius: 10px;
        padding: 10px;
        margin: 5px calc(5% - 12px) 2.5% calc(5% - 12px);
    }

    img{
        height: 121.5px;
        width: 121.5px;
        object-fit: cover;
        border: 1px solid var(--primary-color);
    }

    .product-info{
        width: 100%;
        margin-left: 5%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        text-align: left;
        color: var(--text-color);
        font-size: 1.25rem;
    }

    .text{
        margin-bottom: 10px;
    }

    .edit{
        color: var(--primary-color);
        text-align: right;
        cursor: pointer;
    }
</style>