<template>
    <div class="page">
        <div class="panel">
            <table>
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Pin</th>
                        <th style="text-align: right;">Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="employee in employees" :key="employee.id">
                        <Employees :currentId="employee.id" :currentUsername="employee.username" :currentPincode="employee.pincode" :isNew="employee.isNew" @getEmployees="getEmployees" @addEmployees="addEmployees" @updateEmployees="updateEmployees" @deleteEmployees="deleteEmployees"/>            
                    </tr>
                </tbody>
            </table>
        </div>
        <button @click="addRow">Add Employee</button>
    </div>
</template>

<script>
import EmployeeWrapper from '../wrappers/EmployeeWrapper'
import Employees from '../components/employee_components/Employees.vue'

export default {
    data() {
        return {
            id: '',
            username: '',
            pincode: '',
            employees: []
        }
    },
    created() {
        this.getEmployees()      
    },
    components: {
        Employees
    },
    methods: {
        async getEmployees() {
            const response = await EmployeeWrapper.getEmployees()
 
            if(response.success) {
                this.employees = response.employees
            }
            else {
                alert(response.message)
            }
        },
        async addEmployees(payload) {
            //geeft een lege id mee!
            console.log(payload);

            if(this.doesEmployeesListContainUsername(payload.username)) {
                alert('Already exists a employees with this username')
            }
            else if(this.doesEmployeesListContainPincode(payload.pincode)) {
                alert('Already exists a employees with this pincode')
            }
            else {
                const response = await EmployeeWrapper.postEmployees(payload)

                if(response.success) {
                    alert('Succesfully added employees #' + payload.id)
                }
                else {
                    alert(response.message)
                }
            }

            this.getEmployees()
        },
        async updateEmployees(payload) {
            if(this.doesEmployeesListContainUsername(payload.username)) {
                alert('Already exists a employees with this username')
            }
            else {
                const response = await EmployeeWrapper.putRobot(payload)

                if(response.success) { 
                    alert('Succesfully updated employees #' + payload.id)
                }
                else {
                    alert(response.message)
                }
            }

            this.getEmployees()
        },
        async deleteEmployees(id) {
            const response = await EmployeeWrapper.deleteEmployees(id)

            if(response.success) {
                await this.getEmployees()
                alert('Succesfully deleted employees #' + id)
            }
            else {
                alert(response.message)
            }
        },
        addRow() {
            if(!this.isCreating) {
                this.employees.push({ id: '', username: '', pincode: '', isNew: true })
                this.isCreating = true
            }
        },
        doesEmployeesListContainUsername(username) {
            return this.employees.some(r => r.username === username)
        },
        doesEmployeesListContainPincode(pincode) {
            return this.employees.some(r => r.pincode === pincode)
        }
    }
}
</script>

<style scoped>
    .page {
        top:0;
        bottom: 0;
        right: 0;
        margin: auto;
        height: fit-content;
        width: fit-content;
        text-align: right;
    }
    .panel {
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
    .panel::-webkit-scrollbar-track {
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
    table {
        width: 100%;
        border: none;
        border-collapse: collapse;
    }
    thead {
        color: var(--secondary-color);
        background-color: var(--primary-color);
        border-radius: 5px;
        box-shadow: 3px 3px 5px 0px rgba(0, 0, 0, 0.5);
    }
    thead tr {
        text-align: left;
    }
    tbody tr {
        text-align: left;
        border-bottom: 1px solid var(--text-color);
    }
    th {
        padding: 10px;
        font-size: 1.25rem;
        font-weight: normal;
    }
    table th:first-child {
        border-radius:5px 0 0 5px;
    }
    table th:last-child {
        border-radius:0 5px 5px 0;
    }
    button {
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
    button:hover {
        cursor: pointer;
    }
</style>