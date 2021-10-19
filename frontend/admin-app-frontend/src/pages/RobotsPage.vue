<template>
    <div class="panel">
        <button @click="handleSubmit">Add Robot</button>
    </div>
</template>

<script>
import api from '../wrappers/RobotWrapper'

export default {
    data() {
        return {
            id: '',
            name: ''
        }
    },
    methods: {
        async handleSubmit(e){
            e.preventDefault()

            this.id = 'test'
            this.name = 'test'

            if(!this.id.trim() || !this.name.trim()){
                alert('All fields need to be filled in.')
                return
            }

            const payload = { 
                id: this.id, 
                name: this.name 
            };
            
            const response = await api.postRobot(payload);

            if(response.status === 201){
                alert('woopwoop')
            }
            else{
                alert(response.status)
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
        right: 0;
        margin: auto;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
</style>