<template>
    <div :class="{'head-category head-category-selected' : this.isSelected, 'head-category' : !this.isSelected}" 
    @click="selectHeadCategory" 
    @dblclick="redirectToCategory(this.id)">
        {{ name }}
    </div>
    <template v-if="isSelected">
        <div v-for="childCategory in childCategories" :key="childCategory.id" 
        :class="{'child-category child-category-selected' : childCategory.selected, 'child-category' : !childCategory.selected}" 
        @click="selectChildCategory(childCategory)" 
        @dblclick="redirectToCategory(childCategory.id)">
            {{ childCategory.name }}
        </div>
    </template>
</template>

<script>
export default {
    name: 'Category',
    emits: ['selectCategory'],
    data(){
        return {
            isSelected: false
        }
    },
    props: {
        id: String,
        name: String,
        childCategories: Array,
        otherIsSelected: Boolean
    },
    watch: {
        otherIsSelected() {
            if(this.otherIsSelected){
                this.isSelected = false;
                this.deselectAllChildCategories()
            }
        }
    },
    methods: {
        selectHeadCategory(){
            if(this.childCategories.some(i => i.selected) || this.isSelected){
                return
            }

            this.isSelected = true;
            this.$emit('selectCategory', this.id)
        },
        selectChildCategory(childCategory){
            this.deselectAllChildCategories()
            childCategory.selected = true
            this.$emit('selectCategory', childCategory.id)
        },
        deselectAllChildCategories(){
            this.childCategories.forEach(element => element.selected = false)
        },
        redirectToCategory(id){
            this.$router.push(`category?id=${id}`)
        }
    }
}
</script>

<style scoped>
    .head-category{
        color: var(--secondary-color);
        background: var(--primary-color);
        opacity: 0.75;
        display: flex;
        font-size: 1.25rem;
        padding: 12.5px 12.5px 12.5px 25px;
    }

    .head-category:hover{
        cursor: pointer;
        opacity: 1 !important;
    }

    .head-category-selected{
        opacity: 1 !important;
    }

    .child-category{
        color: var(--secondary-color);
        background: var(--primary-color);
        opacity: 0.75;
        display: flex;
        font-size: 1rem;
        padding: 10px 10px 10px 40px;
    }

    .child-category:hover{
        cursor: pointer;
        opacity: 1 !important;
    }

    .child-category-selected{
        opacity: 1 !important;
    }
</style>