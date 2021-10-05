import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = process.env.VUE_APP_API_BASE_URL;


// This are examples of request to the api using GET en POST request
export default {

    // Simple get request
    get(endpoint) {
        return axios.get(`${baseUrl}/${endpoint}`)
    },

    // Simple post request with json as the payload
    postJson(endpoint, data) {
        
        let jsonPayload = JSON.stringify(data);

        return axios.post(`${baseUrl}/${endpoint}`, {jsonPayload})
    },
}