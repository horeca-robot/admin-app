<template>
    <div class="page">
        <div class="panel">
            <table>
                <thead>
                    <tr>
                        <th>Robot</th>
                        <th>Id</th>
                        <th style="text-align: right;">{{text.RobotPage_Edit}}</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="robot in robots" :key="robot.rerender">
                        <Robot :currentId="robot.id" :currentName="robot.name" :isNew="robot.isNew" @getRobots="getRobots" @addRobot="addRobot" @updateRobot="updateRobot" @deleteRobot="deleteRobot"/>
                    </tr>
                </tbody>
            </table>
        </div>
        <button @click="addRow">{{text.RobotPage_Add}}</button>
    </div>
</template>

<script>
import RobotWrapper from '../wrappers/RobotWrapper'
import Robot from '../components/robot_components/Robot.vue'
import notification from '../utils/NotificationUtil'
import LanguageUtil from '../utils/LanguageUtil'

export default {
    data() {
        return {
            text: LanguageUtil.getTextObject(),
            robots: [],
            isCreating: false
        }
    },
    created(){
        this.getRobots()
    },
    components: {
        Robot
    },
    methods: {
        async getRobots(){
            const response = await RobotWrapper.getRobots()

            if(response.success){
                this.robots = response.robots
                this.robots.forEach(function (robot) {
                    robot.rerender = false
                });
            }
            else{
                notification.showErrorNotification(response.message)
            }

            this.isCreating = false
        },
        async addRobot(payload){
            if(this.doesRobotListContainId(payload.id)){
                notification.showErrorNotification(this.text.RobotPage_IdExistErr)
            }
            else if(this.doesRobotListContainName(payload.name)){
                notification.showErrorNotification(this.text.RobotPage_NameExistErr)
            }
            else{
                const response = await RobotWrapper.postRobot(payload)

                if(response.success){
                    notification.showSuccessNotification('robot #' + payload.id + this.text.RobotPage_AddSuccess)
                }
                else{
                    notification.showErrorNotification(response.message)
                }
            }

            await this.getRobots()
        },
        async updateRobot(payload){
            if(this.doesRobotListContainName(payload.name)){
                notification.showErrorNotification(this.text.RobotPage_NameExistErr)
            }
            else{
                const response = await RobotWrapper.putRobot(payload)

                if(response.success){
                    notification.showSuccessNotification('robot #' + payload.id + this.text.RobotPage_UpdateSuccess)
                }
                else{
                    notification.showErrorNotification(response.message)
                }
            }

            await this.getRobots()

            var robot = this.robots.find(i => i.id === payload.id)
            robot.rerender = !robot.rerender
        },
        async deleteRobot(id){
            const response = await RobotWrapper.deleteRobot(id)

            if(response.success){
                await this.getRobots()
            }
            else{
                notification.showErrorNotification(response.message)
            }
        },
        addRow(){
            if(!this.isCreating){
                this.robots.push({ id: '', name: '', isNew: true })
                this.isCreating = true
            }
        },
        doesRobotListContainId(id){
            return this.robots.some(r => r.id === id)
        },
        doesRobotListContainName(name){
            return this.robots.some(r => r.name === name)
        }
    }
}
</script>

<style scoped>
    .page{
        top:0;
        bottom: 0;
        right: 0;
        margin: auto;
        height: fit-content;
        width: fit-content;
        text-align: right;
    }

    .panel{
        min-width: 500px;
        width: 40vw;
        min-height: fit-content;
        max-height: 60vh;
        padding: 20px;
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        overflow-x: hidden;
        overflow-y: overlay;
    }

        .panel::-webkit-scrollbar {
            width: 5px;
        }

            .panel::-webkit-scrollbar-track{
                margin: 20px;
            }

            .panel::-webkit-scrollbar-thumb {
                border-radius: 3px;
                background: var(--primary-color);
                background-clip: padding-box;
            }

                .panel::-webkit-scrollbar-thumb:hover {
                    opacity: 0.9;
                }

    table{
        width: 100%;
        border: none;
        border-collapse: collapse;
    }

    thead{
        color: var(--secondary-color);
        background-color: var(--primary-color);
        border-radius: 5px;
        box-shadow: 3px 3px 5px 0px rgba(0, 0, 0, 0.5);
    }

    thead tr{
        text-align: left;
    }

    tbody tr{
        text-align: left;
        border-bottom: 1px solid var(--text-color);
    }


    th{
        padding: 10px;
        font-size: 1.25rem;
        font-weight: normal;
    }

        table th:first-child{
            border-radius:5px 0 0 5px;
        }

        table th:last-child{
            border-radius:0 5px 5px 0;
        }

    button{
        padding: 15px 20px 15px 20px;
        color: var(--text-color);
        background-color: var(--secondary-color);
        box-shadow: 5px 5px 5px 1px rgba(0, 0, 0, 0.5);
        border-radius: 10px;
        font-size: 1.5rem;
        font-family: inherit;
        margin-top: 15px;
        outline: none;
        border: none;
    }

        button:hover{
            color: var(--primary-color);
            cursor: pointer;
        }
</style>