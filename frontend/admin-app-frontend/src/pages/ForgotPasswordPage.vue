<template>
    <div class="panel" v-if="hasToken">
        <div class="section" >
            <div class="input">
                <i class="icon fas fa-lock"/>
                <div class="line" />
                <input v-model="password" type="password" class="input-field" placeholder="Password"/>
            </div>
            <div class="input">
                <i class="icon fas fa-lock"/>
                <div class="line" />
                <input v-model="confirmPassword" type="password" class="input-field" placeholder="Confirm password"/>
            </div>
            <div >
                <button class="btn" @click="changePassword">Change password</button>
            </div>
        </div>
    </div>
    <div class="panel" v-else>
        <div class="section">
            <div class="input">
                <i class="icon fas fa-envelope"/>
                <div class="line" />
                <input v-model="email" type="text" class="input-field" placeholder="email"/>
            </div>
            <div>
                <button class="btn" @click="sendResetMail">Send Resetlink</button>
            </div>
        </div>
    </div>
</template>

<script>
import api from '../wrappers/PasswordWrapper.js'
import JwtUtil from '../utils/JwtUtil.js'

export default {
    data(){
        return{
            token: '',
            hasToken: false,
            password: '',
            confirmPassword: '',
            email: ''
        }
    },
    async created(){
        this.token = this.$route.query.token
        console.log("Token = " + this.token);
        if(this.token){
            console.log("Should come here when i have token")
            const claims = JwtUtil.parseJwt(this.token)
            console.log("Claims = " + claims)
            const isExpired = JwtUtil.checkExpiration(claims["exp"])
            console.log("is expired? " + isExpired)
            if(isExpired){
                alert("Token has expired")
                this.$router.push("login")
            }
            this.hasToken = true
            console.log(this.hasToken)
        }
        console.log("Has token = " + this.hasToken)
    },
    methods:{
        sendResetMail(){
            if(!this.email){
                alert("Please fill in an email to receive a reset link")
                return
            }
            api.postForgetPasswordLink(this.email)
        },
        changePassword(){
            if(this.password === this.confirmPassword){
                const payload = {
                    email: JwtUtil.getEmail(this.token),
                    password: this.password
                }
                console.log(payload)
                api.resetpassword(payload)
            }
        }
    }
}
</script>

<style scoped>
    .panel{
        min-width: 500px;
        width: 32.5vw;
        min-height: fit-content;
        height: 30vh;
        top:0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }

    .section{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .title{
        font-weight: 400;
        font-style: normal;
        font-size: 4em;
        color: var(--text-color);
        opacity: 0.85;
    }

    .input{
        width: 75%;
        height: 50px;
        border: 3px solid var(--primary-color);
        border-radius: 10px;
        display: flex;
        align-items: center;
        margin-bottom: 25px;
    }

    .icon{
        font-size: 2rem;
        color: var(--text-color);
        opacity: 0.85;
        margin: 20px;
        min-width: 32px;
        display: flex;
        justify-content: center;
    }

    .line{
        border: 1px solid var(--text-color);
        opacity: 0.85;
        height: 35px;
    }

    .input-field{
        font-family: Strait;
        padding: 20px;
        font-weight: 400;
        font-style: normal;
        font-size: 1.5rem;
        color: var(--text-color);
        opacity: 0.85;
        height: 0px;
        width: 100%;
        border: none;
        background: var(--secondary-color);
    }

    .input-field:focus{
        outline: none;
    }

    .forgot-password{
        font-weight: 400;
        font-style: normal;
        font-size: 1.5rem;
        color: var(--primary-color);
        opacity: 1;
    }

    .forgot-password:hover{
        opacity: 0.85 !important;
        cursor: pointer;
    }

    .btn{
        min-width: fit-content;
        height: 50px;
        font-family: Strait;
        padding: 20px;
        font-weight: 400;
        font-style: normal;
        font-size: 2rem;
        color: var(--secondary-color);
        background: var(--primary-color);
        border-radius: 10px;
        border: none;
        display: flex;
        justify-content: center;
        align-items: center;
        transition: 0.2s ease;
    }

    .btn:hover{
        transform: scale(1.025);
        box-shadow: 5px 5px 4px rgba(0, 0, 0, 0.25);
        cursor: pointer;
    }
</style>

