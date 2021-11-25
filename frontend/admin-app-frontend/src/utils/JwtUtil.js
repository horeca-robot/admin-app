export default {

    parseJwt(token) {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload);
    },

    //Check expiration date of JWT
    checkExpiration(exp) {
        const expDate = new Date(exp * 1000)
        const currentDate = new Date()

        return expDate.getTime() <= currentDate.getTime();
    },

    getEmail(token){
        const claims = this.parseJwt(token)
        const email = claims["sub"]
        return email
    }
}