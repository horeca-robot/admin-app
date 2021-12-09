import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/employee`;

export default {
    getHeaders() {
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },
    async getEmployees() {
        var response;

        try {
            response =  await axios.get(baseUrl, this.getHeaders())
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
            response =  await axios.post(baseUrl, data, this.getHeaders())

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
            response =  await axios.put(`${baseUrl}/${data.id}`, data, this.getHeaders())
            
            return {
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
    },
    async deleteEmployees(id) {
        var response;

        try {
            response =  await axios.delete(`${baseUrl}/${id}`, this.getHeaders())

            return {
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