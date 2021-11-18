import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/info`;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    }
}

export default {
    async getRestaurantSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/restaurant`, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async putRestaurantSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/restaurant`, model, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async getWebsiteSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/website`, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async putWebsiteSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/website`, model, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    }
}