<template>
   <p v-if="errors.length">
    <b>Please correct the following error(s):</b>
    <ul>
      <li v-for="error in errors">{{ error }}</li>
    </ul>
  </p>
</template>

<script>
export default {
    name: "FormError",
    props: 
    {
        errors: [],
        title: String,
    },
}
</script>