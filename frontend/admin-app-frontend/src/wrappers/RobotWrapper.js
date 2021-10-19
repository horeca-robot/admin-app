import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

export default {
    async getRobots(){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/api/Robot`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('jwt')
                }
            })
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

        let config = {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('jwt'),
            }
          }

        var response;

        try{
            response =  await axios.post(`${baseUrl}/api/Robot`, data, config)
            //response = await axios.get(`${baseUrl}/api/Robot/test`, config)
        }
        catch(error){
            response = error.response
        }

        return {
            status: response.status
        }
    },

    async deleteRobot(data) {

        var response;

        try{
            response =  await axios.delete(`${baseUrl}/api/Robot/${data}`, {
                header: {
                    Authorization: 'Bearer ' + localStorage.getItem('jwt')
                }
            })
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            data: response.data["data"]
        }
    }
} 