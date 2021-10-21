package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.adminappbackend.services.EmployeeService;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "api/Employees")
@CrossOrigin(origins = "http://localhost:4000/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getEmployees() {
        try {
            ArrayList<EmployeeUser> employees = employeeService.getAllEmployeeUsers();
            return new ResponseEntity<>(ApiResponse.ok().addData("employees", employees), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Cannot retrieve employees from database. Please try again later.").addData("exception", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addEmployee(@RequestBody EmployeeModel employeeModel) {
        if(employeeModel.getUsername().isBlank()) {
            return new ResponseEntity<>(ApiResponse.error("Not all required fields are filled in."), HttpStatus.BAD_REQUEST);
        }

        try {
            employeeService.addEmployeeUser(employeeModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error("Cannot add an employee to the database. Please try again later.").addData("exception", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
