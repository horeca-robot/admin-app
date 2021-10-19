import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

export default {

    async getTest() {

        var response;

        try {
            response =  await axios.get(`${baseUrl}/api/restaurant/test`)
        }
        catch(error){
            response = error.response
        }

        return response
    }
} 