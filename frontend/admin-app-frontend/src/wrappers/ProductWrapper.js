import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/product`;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },
    
    async getProducts(){

        var response;

        try{
            response = await axios.get(baseUrl, this.getHeaders())
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
            response = await axios.get(`${baseUrl}/${id}`, this.getHeaders())
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
            response =  await axios.post(baseUrl, data, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 201
        }
    },

    async putProduct(data) {

        var response;

        try{
            response =  await axios.put(`${baseUrl}/${data.id}`, data, this.getHeaders())
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
            response = await axios.delete(`${baseUrl}/${id}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.success === 204
        }
    }
} 