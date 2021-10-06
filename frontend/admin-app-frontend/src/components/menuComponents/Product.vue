<template>
    <header>
        <h1> Create new product </h1>
        <a> {{ name }}</a>
        <a> {{ image }}</a>
        <a> {{ price }}</a>
        <a> {{ discountPrice }}</a>
        <a> {{ description }}</a>
        <a> {{ alcohol }}</a>
    </header>
</template>

<script>
export default{
    name: 'Product',
    porps: {
        name: String, 
        image: String,
        price: Float64Array,
        discountPrice: Float64Array,
        description: String, 
        alcohol: Boolean,
        byProducts: []
    },
}
</script>