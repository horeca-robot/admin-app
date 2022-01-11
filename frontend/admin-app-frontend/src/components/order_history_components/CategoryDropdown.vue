<template>
    <div id="category">
        <div id="header">
            <i v-if="droppedDown" class="icon fas fa-caret-down" @click="droppedDown = false"/>
            <i v-else class="icon fas fa-caret-right" @click="droppedDown = true"/>
            <label>{{ category.name }}</label>
        </div>
        <div id="products" v-if="droppedDown">
            <label v-for="product in allProducts.sort((a,b) => (a.amount < b.amount) ? 1 : ((b.amount < a.amount) ? -1 : 0))" :key="product.id">
                <label>{{ product.name }}</label> {{text.CatDropDownComp_Ordered}} <label>{{ product.amount }}</label> {{text.CatDropDownComp_Times}}
            </label>
        </div>
    </div>
</template>
<script>
import ProductWrapper from '../../wrappers/ProductWrapper'
import NotificationUtil from '../../utils/NotificationUtil'
import LanguageUtil from '../../utils/LanguageUtil'

export default {
    data() {
        return {
            text: LanguageUtil.getTextObject(),
            droppedDown: false,
            allProducts: []
        }
    },
    props: {
        category: Object,
        orderedProducts: Array
    },
    async created() {
        await this.getAllProducts()
    },
    methods: {
        async getAllProducts(){
            const response = await ProductWrapper.getProducts()

            if(response.success){
                this.allProducts = response.products.filter(p => p.categories.some(c => c === this.category.id))
                this.allProducts.forEach(product => {
                    product.amount = this.orderedProducts.filter(p => p.id === product.id).length
                })
            }
            else{
                NotificationUtil.showErrorNotification(response.message)
            }
        }
    }
}
</script>
<style scoped>
    #category{
        margin-bottom: 10px;
    }

    #header{
        display: flex;
        font-size: 1.5em;
        align-items: center;
        margin-bottom: 5px;
        color: var(--text-color);
    }

    .icon{
        width: 15px;
    }

        #header label{
            margin-left: 10px;
        }

    #products{
        color: var(--text-color);
        font-size: 1.25em;
        display: flex;
        flex-direction: column;
    }

        #products label{
            margin-bottom: 2px;
        }

            #products label label{
                color: var(--primary-color);
            }
</style>