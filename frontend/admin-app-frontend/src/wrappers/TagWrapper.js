import axios from "axios";

// Base url can be found in the .env file in the root folder
export const baseUrl = `${process.env.VUE_APP_API_BASE_URL}/api/tag`;

export default {

    getHeaders(){
        return {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            }
        }
    },

    async getTags(){
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
            tags: response.data["data"]["tags"]
        }
    },

    async getTagById(id){
        var response;

        try{
            response =  await axios.get(`${baseUrl}/${id}`, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return {
            success: response.data["success"],
            message: response.data["message"],
            tag: response.data["data"]["tag"]
        }
    },
    
    async postTag(data) {
        var response;

        try{
            response =  await axios.post(baseUrl, data, this.getHeaders())
        }
        catch(error){
            response = error.response
        }

        return{
            success: response.status === 201
        }
    }

} 