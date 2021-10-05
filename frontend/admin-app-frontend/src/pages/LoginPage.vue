<template>
    <div class="panel">
        <h1 class="title">HorecaRobot</h1>
        <div class="group">
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
        <div class="group" >
            <p class="forgot-password">Forgot Password?</p>
            <button class="btn" @click="handleSubmit">Log In</button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            email: '',
            password: ''
        }
    },
    methods: {
        handleSubmit(e) {
            e.preventDefault()

            if(!this.email || !this.password){
                alert('All fields need to be filled in.')
                return
            }

            const loginForm = {
                email: this.email,
                password: this.password
            }

            //TODO: Add API login endpoint
            this.$http.post('[API LOGIN ENDPOINT]]', { loginForm })
            .then(response => {
                console.log(response)
                //TODO: Add response code based on API response model

                //If response is unsuccesful:
                //-Show error alert with given message

                //If response is succesful:
                //-Add JWT to localstorage
                //-Redirect to new page:
                if(this.$route.params.nextUrl != null) {
                    this.$router.push(this.$route.params.nextUrl)
                }
                else {
                    this.$router.push('employees')
                }
            })
            .catch(function (error) {
                console.error(error.response);
            });
        }
    }
}
</script>

<style scoped>
    .group{
        width: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .panel{
        position: absolute;
        min-width: 500px;
        width: 32.5vw;
        min-height: fit-content;
        height: 50vh;
        top:0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        background-color: white;
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .title{
        font-weight: 400;
        font-style: normal;
        font-size: 4em;
        color: rgba(0, 0, 0, 0.85);
    }

    .input{
        width: 75%;
        height: 50px;
        border: 3px solid rgb(1, 87, 228);
        border-radius: 10px;
        display: flex;
        align-items: center;
        margin-bottom: 25px;
    }

    .icon{
        font-size: 2rem;
        color: rgba(0, 0, 0, 0.85);
        margin: 20px;
        min-width: 32px;
        display: flex;
        justify-content: center;
    }

    .line{
        border: 1px solid rgba(0, 0, 0, 0.85);
        height: 35px;
    }

    .input-field{
        font-family: Strait;
        padding: 20px;
        font-weight: 400;
        font-style: normal;
        font-size: 1.5rem;
        color: rgba(0, 0, 0, 0.85);
        height: 0px;
        width: 100%;
        border: none;
    }

    .input-field:focus{
        outline: none;
    }

    .forgot-password{
        font-weight: 400;
        font-style: normal;
        font-size: 1.5rem;
        color: rgb(1, 87, 228);
    }

    .forgot-password:hover{
        color: rgba(1, 87, 228, 0.85);
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
        color: white;
        background: #0157E4;
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
    }
</style>
