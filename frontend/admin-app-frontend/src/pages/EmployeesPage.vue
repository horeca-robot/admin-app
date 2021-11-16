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
                    <tr v-for="employee in employees" :key="employee.rerender">
                        <Employee :currentId="employee.id" :currentUsername="employee.username" :currentPincode="employee.pincode" :isNew="employee.isNew" @getEmployees="getEmployees" @addEmployees="addEmployees" @updateEmployees="updateEmployees" @deleteEmployees="deleteEmployees"/>            
                    </tr>
                </tbody>
            </table>
        </div>
        <button @click="addRow">Add Employee</button>
    </div>
</template>

<script>
import EmployeeWrapper from '../wrappers/EmployeeWrapper'
import Employee from '../components/employee_components/Employee.vue'

export default {
    data() {
        return {
            employees: [],
            isCreating: false
        }
    },
    created() {
        this.getEmployees()      
    },
    components: {
        Employee
    },
    methods: {
        async getEmployees() {
            const response = await EmployeeWrapper.getEmployees()
 
            if(response.success) {
                this.employees = response.employees

                this.employees.forEach(function (employee) {
                    employee.rerender = false
                });
            }
            else {
                alert(response.message)
            }

            this.isCreating = false
        },
        async addEmployees(payload) {
            if(this.doesEmployeesListContainUsername(payload.username)) {
                alert('Already exists an employee with this username')
            }
            else if(payload.pincode < 1111 || payload.pincode > 9999) {
                alert("Pin should be a four digit number")
            }
            else {
                const response = await EmployeeWrapper.postEmployees(payload)

                if(response.success) {
                    alert('Succesfully added employee ' + payload.username)
                }
                else {
                    alert(response.message)
                }
            }

            await this.getEmployees()
        },
        async updateEmployees(payload) {
            if(this.doesEmployeesListContainUsername(payload.username, payload.id)) {
                alert('Already exists an employee with this username')
            }
            else if(payload.pincode < 1111 || payload.pincode > 9999) {
                alert("Pin should be a four digit number")
            }
            else {
                const response = await EmployeeWrapper.putEmployees(payload)

                if(response.success) {                    
                    alert('Succesfully updated employee ' + payload.username)
                }
                else {
                    alert(response.message)
                }
            }

            await this.getEmployees()

            var employee = this.employees.find(i => i.id === payload.id)
            employee.rerender = !employee.rerender
        },
        async deleteEmployees(id) {
            const response = await EmployeeWrapper.deleteEmployees(id)

            if(response.success) {
                await this.getEmployees()
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
        doesEmployeesListContainUsername(username, id) {
            return this.employees.some(r => r.username === username && r.id !== id)
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