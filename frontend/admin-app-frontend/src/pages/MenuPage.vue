<template>
    <div>
        <h1>Menu</h1>
        <CategoryForm />
    </div>
</template>

<script>
import CategoryForm from '../components/CategoryForm'

export default {
    name: "Employees",
    props: 
    {
        title: String,
    },
    components:
    {
        CategoryForm
    }
}
</script>