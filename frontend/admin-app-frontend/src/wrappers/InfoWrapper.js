import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

var apiConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`,
    }
}

export default {
    async getRestaurantSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/api/info/restaurant`, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async saveRestaurantSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/api/info/restaurant`, model, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async getWebsiteSettings() {
        var response;

        try {
            response = await axios.get(`${baseUrl}/api/info/website`, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async saveWebsiteSettings(model) {
        var response;

        try {
            response = await axios.put(`${baseUrl}/api/info/website`, model, apiConfig)
        } catch (error) {
            response = error.response
        }

        return response;
    },
    async getBase64Image(name) {
        let response = ''
        await axios.get(`${baseUrl}/api/image/${name}`)
            .then(function(rsp) {
                response = rsp.data
            })
        return response
    }
}