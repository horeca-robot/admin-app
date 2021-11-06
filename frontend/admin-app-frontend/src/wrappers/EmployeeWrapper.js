import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    }
}

export default {
    async getEmployees() {
        var response;

        try{
            response =  await axios.get(`${baseUrl}/api/Employees`, apiConfig)
        }
        catch(error) {
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            employees: response.data["data"]["employees"]
        }
    }
} 