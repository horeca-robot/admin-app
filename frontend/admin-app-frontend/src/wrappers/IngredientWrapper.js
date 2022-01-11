import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/ingredient`;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },

    async getIngredients(){
        var response;

        try{
            response =  await axios.get(baseUrl, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            ingredients: response.data["data"]["ingredients"]
        }
    },

    async getIngredientById(id){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/${id}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            ingredient: response.data["data"]["ingredient"]
        }
    },
    
    async postIngredient(data) {
        var response;

        try{
            response =  await axios.post(baseUrl, data, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 201
        }
    }

} 