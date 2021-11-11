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
            username: '',
            pincode: 0,
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
        this.username = this.currentUsername
        this.pincode = this.currentPincode 
        this.isEditing = this.isNew 
    },
    methods: {
        changeToEdit() {
            this.isEditing = true
        },
        handleSubmit() {
            if(!this.username.trim() || !this.pincode) {
                alert('All fields need to be filled in.')
                return
            }
            if(this.isNew) {
                const employee = { 
                    username: this.username, 
                    pincode: parseInt(this.pincode)
                }

                this.$emit('addEmployees', employee)
            }
            else {
                const employee = { 
                    id: this.currentId,
                    username: this.username, 
                    pincode: parseInt(this.pincode)
                }
                
                this.$emit('updateEmployees', employee)
            }

            this.isEditing = false
        },
        deleteEmployees() {
            if(this.isNew) {
                this.$emit('getEmployees')
            }
            else {
                if(!confirm('Are you sure you want to delete employee#' + this.username + '?')) {
                    return;
                }
                
                this.isEditing = false;           
                this.$emit('deleteEmployees', this.username, this.currentId);
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