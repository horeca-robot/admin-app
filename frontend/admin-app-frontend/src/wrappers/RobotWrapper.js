import axios from "axios";

// Base url can be found in the .env file in the root folder
const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/robot`;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },

    async getRobots(){
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
            robots: response.data["data"]["robots"]
        }
    },

    async postRobot(data) {

        var response;

        try{
            response =  await axios.post(baseUrl, data, this.getHeaders())

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
            response =  await axios.put(`${baseUrl}/${data.id}`, data, this.getHeaders())

            return {
                success: response.status === 204
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
            response =  await axios.delete(`${baseUrl}/${id}`, this.getHeaders())

            return {
                success: response.status === 204
            }
        }
        catch(error){
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    }
} 