import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;


export default {
    async postForgetPasswordLink(email){
        var response;

        try{
            response = await axios.post(`${baseUrl}/api/password`, email)
        }
        catch(error){
            response = response.error
        }

        return{
            success: response.status === 204
        }
    },

    async resetpassword(model){
        var response;

        try{
            response = await axios.put(`${baseUrl}/api/password`, model)
        }
        catch(error){
            response = response.error
        }

        return{
            success: response.status === 204
        }
    }
} 