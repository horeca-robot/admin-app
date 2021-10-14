import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

export default {

    async signIn(data) {

        var response;

        try{
            response =  await axios.post(`${baseUrl}/api/SignIn/authenticate`, data)
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            token: response.data["data"]["jwt"]
        }
    }
} 