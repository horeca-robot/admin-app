import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/category`;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
}

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
            response =  await axios.get(baseUrl, this.getHeaders())
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
            response =  await axios.get(`${baseUrl}/${id}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            category: response.data["data"]["category"]
        }
    },
    async postCategory(data) 
    {
        var response;

        try{
            response =  await axios.post(baseUrl, data, apiConfig)
        }
        catch(error)
        {
            response = error.response
            console.log(error)
        }



        return{
            success: response.status === 201
        }
    },
    async putCategory(data) 
    {
        var response;

        try{
            response =  await axios.put(`${baseUrl}/${data.id}`, data, apiConfig)
        }
        catch(error){
            response = error.response
            console.log(error)
        }

        return{
            success: response.status === 204
        }
    },
    async deleteCategory(id) 
    {
        var response;

        try{
            response =  await axios.delete(`${baseUrl}/${id}`, apiConfig)
        }
        catch(error){
            response = error.response
            console.log(error)
        }



        return{
            success: response.status === 201
        }
    }
} 