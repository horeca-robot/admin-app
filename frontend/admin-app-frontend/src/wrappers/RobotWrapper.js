import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    }
}

export default {

    async getRobots(){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/api/Robot`, apiConfig)
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            robots: response.data["data"]["robots"]
        }
    },

    async postRobot(data) {

        var response;

        try{
            response =  await axios.post(`${baseUrl}/api/Robot`, data, apiConfig)

            return {
                success: response.status === 201
            }
        }
        catch(error){
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    },

    async putRobot(data) {

        var response;

        try{
            response =  await axios.put(`${baseUrl}/api/Robot`, data, apiConfig)

            return {
                success: response.status === 201
            }
        }
        catch(error){
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    },

    async deleteRobot(id) {

        var response;

        try{
            response =  await axios.delete(`${baseUrl}/api/Robot/${id}`, apiConfig)
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"]
        }
    }
} 