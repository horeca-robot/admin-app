import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },

    async getCategories(){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/api/Category`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            categories: response.data["data"]["categories"]
        }
    },

    async getCategoryById(id){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/api/Category/${id}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            category: response.data["data"]["category"]
        }
    }
} 