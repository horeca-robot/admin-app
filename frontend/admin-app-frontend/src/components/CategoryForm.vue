<template>
    <div>
        <h1> Add Category Form</h1>

        <label for="name">Name</label><input id="name" v-model="name" type="text" name="name">

        <p><label for="name">Image</label><input id="img" v-model="img" type="image" name="image"></p>

        <p>
            <label for="category">Kies een categorie</label>
            <select name="Category Selector">
                <option>C1</option>
                <option>C2</option>
                <option>C3</option>
            </select>
        </p>

        <p> <input type="submit" value="Submit"> </p>

    </div>
</template>

<script>
export default {
    name: "Employees",
    props: 
    {
        title: String,
    },
    components:
    {
        
    },
}
</script>