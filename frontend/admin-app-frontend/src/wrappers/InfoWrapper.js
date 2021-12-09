import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/info`;

export default {
    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },
    async getRestaurantSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/restaurant`)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async putRestaurantSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/restaurant`, model, this.getHeaders())
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async getWebsiteSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/website`)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async putWebsiteSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/website`, model, this.getHeaders())
        } catch (error) {
            response = error.response
        }

        return response;
    }
}