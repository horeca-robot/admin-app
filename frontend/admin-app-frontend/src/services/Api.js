import axios from "axios";

export const baseUrl = "http://localhost:8080"

export default {

    get(endpoint) {
        return axios.get(`${baseUrl}/${endpoint}`)
    },

    post(endpoint) {
        return axios.post(`${baseUrl}/${endpoint}`)
    },
}