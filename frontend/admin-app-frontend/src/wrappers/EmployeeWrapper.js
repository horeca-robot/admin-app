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
    },
    async postEmployees(data) {
        var response;

        try {
            response =  await axios.post(`${baseUrl}/api/Employees`, data, apiConfig)

            console.log(response.status)

            return {
                success: response.status === 201
            }
        }
        catch(error) {
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    },
    async putEmployees(data) {
        var response;

        try {           
            response =  await axios.put(`${baseUrl}/api/Employees/${data.id}`, data, apiConfig)
            
            return {
                success: response.status === 201
            }
        }
        catch(error) {
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    },
    async deleteEmployees(id) {
        var response;

        try {
            response =  await axios.delete(`${baseUrl}/api/Employees/${id}`, apiConfig)

            return {
                //Status: 201 > 204. Moet ook aangepast worden in de RobotWrapper. 
                success: response.status === 204
            }
        }
        catch(error) {
            response = error.response

            return {
                success: response.data["success"],
                message: response.data["message"]
            }
        }
    }
} 