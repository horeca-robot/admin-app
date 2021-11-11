import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
    }
}

export default {
    
    async getProducts(){

        var response;

        try{
            response = await axios.get(`${baseUrl}/api/Product`, apiConfig)
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.data["success"],
            message: response.data["message"],
            products: response.data["data"]["products"]
        }
    },

    async getProductById(id){

        var response;

        try{
            response = await axios.get(`${baseUrl}/api/Product/${id}`, apiConfig)
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.data["success"],
            message: response.data["message"],
            product: response.data["data"]["product"]
        }
    },
    
    async postProduct(data) {
        var response;

        try{
            response =  await axios.post(`${baseUrl}/api/Product`, data, apiConfig)
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 201
        }
    },

    async putProduct(data) {
        console.log(data)

        var response;

        try{
            response =  await axios.put(`${baseUrl}/api/Product/${data.id}`, data, apiConfig)
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 204
        }
    },
    
    async deleteProduct(id){
        var response;

        try{
            response = await axios.delete(`${baseUrl}/api/Product/${id}`, apiConfig)
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.success === 204
        }
    }
} 