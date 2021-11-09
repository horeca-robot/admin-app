<template>
    <h1>Category</h1>
</template>

<script>
import CategoryWrapper from '../wrappers/CategoryWrapper'

export default {
    data(){
        return{
            id: '',
            isEditing: false
        }
    },
    created(){
        this.id = this.$route.query.id

        if(this.id !== undefined){
            this.isEditing = true
            this.getCategoryInfo()
        }
    },
    methods: {
        async getCategoryInfo(){
            const response = await CategoryWrapper.getCategoryById(this.id)
            console.log(response.category)
        }
    }
}
</script>