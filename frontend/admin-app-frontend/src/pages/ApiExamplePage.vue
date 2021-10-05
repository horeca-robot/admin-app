<template>
    <div>
        <h1>Api test</h1>
        <button @click="getNum">Get random number</button> : <span>random number = {{ number}}</span>
        <br>
        <br>    
        <button @click="postForm">Post Form</button>
        <br>
        <h1> {{ formData }} </h1>
    </div>
</template>

<script>
// This is how you import the api wrapper 
import api from "@/wrappers/Example Api Wrapper.js"

export default {
    data() {
        return {
            formData : "Not edited",
            number: 0
        }
    },
    methods: {
       async getNum(){

            var response = await api.get("random/int");
           
            this.number = response.data;
        },

        async postForm(){
            const formData = new FormData();

            formData.append("data", formData);
            
            var response = await api.postJson("edit/form", formData)
            
            this.formData = response.data["data"];
        }
    }
}
</script>