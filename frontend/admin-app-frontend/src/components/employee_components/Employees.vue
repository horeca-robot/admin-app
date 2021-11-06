<template>
    <template v-if="isEditing">
        <td><input v-model="username"/></td>
        <td><input v-model="pincode"/></td>
        <td style="text-align: right;">
            <i class="icon fas fa-check" @click="handleSubmit"></i>
            <i class="icon fas fa-times" @click="deleteEmployees"></i>
        </td>
    </template>
    <template v-else>
        <td>{{ currentUsername }}</td>
        <td>{{ currentPincode }}</td>
        <td style="text-align: right;"><i class="icon fas fa-edit" @click="changeToEdit"></i></td>
    </template>
</template>

<script>
export default {
    name: 'Employees',
    emits: ['addEmployees', 'updateEmployees', 'getEmployees', 'deleteEmployees'],
    data() {
        return {
            id: '',
            username: '',
            pincode: '',
            isEditing: false
        }
    },
    props: {
        currentId: String,
        currentUsername: String,
        currentPincode: Number,
        isNew: Boolean
    },
    created() {
        this.id = this.currentId
        this.username = this.currentUsername
        this.pincode = this.currentPincode 
        this.isEditing = this.isNew 
    },
    methods: {
        changeToEdit() {
            this.isEditing = true
        },
        handleSubmit() {
            if(!this.username.trim() || !this.pincode.trim()) {
                alert('All fields need to be filled in.')
                return
            }
            const employee = { 
                id: this.id, 
                username: this.username, 
                pincode: this.pincode
            }
            if(this.isNew) {
                //Het type UUID kan niet worden meegestuurd vanuit de backend
                console.log('add_employee_id: '+ employee.id);
                console.log('add_employee_name: '+ employee.username);
                console.log('add_employee_pin: '+ employee.pincode);

                this.$emit('addEmployees', employee)
            }
            else {
                this.$emit('updateEmployees', employee)
            }

            this.isEditing = false
        },
        deleteEmployees() {
            //Postman maakt van het id (UUID) een string en dan werkt het
            console.log('delete_employee_id: '+ this.id);
            console.log('delete_employee_name: '+ this.username);
            console.log('delete_employee_pin: '+ this.pincode);

            if(this.isNew) {
                this.$emit('getEmployees')
            }
            else {
                if(!confirm('Are you sure you want to delete Employees: ' + this.username + ' #' + this.id + '?')) {
                    return;
                }
                this.isEditing = false;
                this.$emit('deleteEmployees', this.id);
            }
        }
    }
}
</script>

<style scoped>
    td {
        padding: 10px;
        font-size: 1.25rem;
        font-weight: normal;
    }
    input {
        font-family: inherit;
        font-size: inherit;
        color: var(--primary-color);
        background-color: var(--secondary-color);
        border: 1px solid var(--text-color);
        width: 100%;
    }
    input:focus {
        outline: none;
    }
    .icon {
        color: var(--text-color);
        font-size: 1.2em;
        margin-left: 5px;
    }
    .icon:hover {
        color: var(--primary-color);
        cursor: pointer;
    }
</style>