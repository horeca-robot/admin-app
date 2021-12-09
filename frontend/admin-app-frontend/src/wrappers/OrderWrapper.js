import axios from "axios";

// Base url can be found in the .env file in the root folder
const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/order`;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },

    async getOrderHistory(){
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
            orders: response.data["data"]["orders"]
        }
    },

    async getOrderHistoryByDates(from, to){
        var response;

        try{
            response =  await axios.get(`${baseUrl}?from=${from}&to=${to}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            orders: response.data["data"]["orders"]
        }
    }
} 