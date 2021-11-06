<template>
    <template>
        <td><input v-model="username"/></td>
        <td><input v-model="pincode"/></td>
        <td>{{ currentUsername }}</td>
        <td>{{ currentPincode }}</td>
    </template>
</template>

<script>
export default {
    name: 'Employees',
    emits: ['getEmployees'],
    data() {
        return {
            id: '',
            username: '',
            pincode: ''
        }
    },
    props: {
        currentId: String,
        currentUsername: String,
        currentPincode: String
    },
    created() {
        this.id = this.currentId
        this.username = this.currentUsername
        this.pincode = this.currentPincode      
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