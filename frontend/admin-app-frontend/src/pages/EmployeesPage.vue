<template>
    <div id="employees">
        <h1>Employees</h1>
        <input type="text" placeholder="add username here" v-model="employeeUsername" @keyup.enter="addEmployee">
        <input type="text" placeholder="add pincode here" v-model="employeePincode" @keyup.enter="addEmployee">
        <ul>
            <li v-for="employee of employees" :key="employee.id" >{{employee.username}} , {{employee.pincode}}</li>  
        </ul>
    </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "employees",
  data() {
      return {
          employees: [],
          employeeUsername: '',
          employeePincode: ''
      };
  },
  async created() {
      try {
          const res = await axios.get("http://localhost:3000/employees");

          this.employees = res.data;
      } catch (e) {
          console.error(e);
      }
  },
  methods: {
      async addEmployee() {
          if (this.employeeUsername != '' && this.employeePincode != '') {
            const res = await axios.post("http://localhost:3000/employees", { username: this.employeeUsername, pincode: this.employeePincode});

            this.employees = [...this.employees, res.data];

            this.employeeUsername = '';
            this.employeePincode = '';
          }
      }
  }
}
</script>

<style scoped>
#employees input:first-of-type {
    margin-right: 10px;
}
</style>
