import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/Product`;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    }
}

export default {
    
    async getProducts(){

        var response;

        try{
            response = await axios.get(baseUrl, apiConfig)
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.data["data"]
        }
    },
    
    async postProduct(data) {
        var response;

        try{
            response =  await axios.post(baseUrl, data, apiConfig)
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 201
        }
    },
    
    async deleteProduct(id){
        var response;

        try{
            response = await axios.delete(`${baseUrl}/${id}`, apiConfig)
        }
        catch(error){
            response = error.response
        }
        
        return{
            success: response.success === 200
        }
    }
} 