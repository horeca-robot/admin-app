<template>
    <div id="employees">
        <h1>Employees</h1>
        <form>
            <input type="text" placeholder="add username here" v-model="employeeUsername" autofocus required minlength="4">
            <input type="text" pattern="[0-9]" placeholder="add pincode here" v-model="employeePincode" required minlength="4"> 
            <button v-on:click="addEmployee($event)">Add</button>      
        </form>
        <ul v-for="employee of employees" :key="employee.id">
            <li>         
                {{employee.username}} , {{employee.pincode}}
                <button v-on:click="removeEmployee(employee.id)">Remove</button>
            </li>  
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
        if (this.employeePincode.length >= 4 && this.employeeUsername.length >= 4 && isNaN(parseInt(this.employeePincode)) == false) {
            const res = await axios.post("http://localhost:3000/employees", { username: this.employeeUsername, pincode: this.employeePincode});

            this.employees = [...this.employees, res.data];

            this.employeeUsername = '';
            this.employeePincode = '';
        }      
      },
      async removeEmployee(employee_id) {
        if(typeof employee_id !== 'undefined' && employee_id !== null) {
            var employees = this.employees.filter(employee => {
                return employee.id != employee_id;
            });

            console.log(employees);
            
            //this.employees = [];
            //const res = await axios.post("http://localhost:3000/employees", { employees: employees});
            //this.employees = [...this.employees, res.data];
        }    
      } 
  }
}
</script>

<style scoped>
#employees input:first-of-type  {
    margin-right: 10px;
}
#employees button {
    margin-left: 8px;
}
#employees button:hover {
    cursor: pointer;
}
</style>
