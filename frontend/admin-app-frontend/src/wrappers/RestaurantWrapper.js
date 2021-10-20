import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;

export default {

    async saveSettings(data) {
        var response;

        var token = localStorage.getItem('jwt')

        try {
            response = await axios.post(`${baseUrl}/api/restaurant/save/settings`, data, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
        } catch (error) {
            response = error.response
        }

        return response;
    }
}