package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.EmployeeRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.EmployeeResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/employee")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getEmployees() {
        List<EmployeeResponseModel> employees;
        try {
            employees = employeeService.getAllEmployees();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("employees", employees));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postEmployee(@RequestBody EmployeeRequestModel employeeRequestModel) {
        if(employeeRequestModel.getUsername().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if((int)employeeModel.getPincode() < 1111 || (int)employeeModel.getPincode() > 9999)
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);
        if(employeeRequestModel.getPin() < 1111 || employeeRequestModel.getPin() > 9999)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        EmployeeResponseModel employeeResponseModel;

        try {
            employeeResponseModel = employeeService.addEmployee(employeeRequestModel);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/employee/" + employeeResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putEmployeeUser(@PathVariable UUID id, @RequestBody EmployeeRequestModel employeeRequestModel) {
        if(!employeeService.doesEmployeeExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate employee in database."));

        if(employeeRequestModel.getUsername().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if((int)employeeModel.getPincode() < 1111 || (int)employeeModel.getPincode() > 9999)
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);
        if(employeeRequestModel.getPin() < 1111 || employeeRequestModel.getPin() > 9999)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        try {
            employeeService.updateEmployee(employeeRequestModel);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeUser(@PathVariable UUID id) {
        if(!employeeService.doesEmployeeExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate employee in database."));

        try {
            employeeService.deleteEmployee(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }
}
