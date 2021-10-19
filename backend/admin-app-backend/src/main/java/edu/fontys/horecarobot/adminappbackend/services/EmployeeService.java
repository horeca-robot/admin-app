package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private ArrayList<EmployeeModel> employees;

    public ArrayList<EmployeeModel> getEmployees() {
        if(employees == null) {
            employees = new ArrayList<>();
        }
        return employees;
    }

    public void addEmploy(EmployeeModel employeeModel) {
        //testcode
        var employee = new EmployeeModel();
        employee.setUsername("employee1");
        employee.setPin("1234");
        this.employees.add(employee);

        //employees.add(employeeModel);
    }
}