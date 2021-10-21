<template>
    <div class="panel">
        <div class="section">
            <h1 class="title">HorecaRobot</h1>
        </div>
        <div class="section">
            <div class="input">
                <i class="icon fas fa-envelope"/>
                <div class="line" />
                <input v-model="email" class="input-field" placeholder="Email"/>
            </div>
            <div class="input">
                <i class="icon fas fa-lock"/>
                <div class="line" />
                <input v-model="password" type="password" class="input-field" placeholder="Password"/>
            </div>
        </div>
        <div class="section" >
            <p class="forgot-password">Forgot Password?</p>
            <button class="btn" @click="handleLogIn">Log In</button>
        </div>
    </div>
</template>

<script>
import api from "../wrappers/AuthenticationWrapper.js"

export default {
    data() {
        return {
            email: '',
            password: ''
        }
    },
    methods: {
        async handleLogIn() {

            if(!this.email.trim() || !this.password.trim()){
                alert('All fields need to be filled in.')
                return
            }

            const payload = { 
                email: this.email, 
                password: this.password 
            };

            const response = await api.signIn(payload);

            if(response.success){
                localStorage.setItem('jwt', response.token)

                if(this.$route.params.nextUrl != null) {
                    this.$router.push(this.$route.params.nextUrl)
                }
                else {
                    this.$router.push('employees')
                }
            }
            else{
                alert(response.message)
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
        height: 50vh;
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
        width: 50%;
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
