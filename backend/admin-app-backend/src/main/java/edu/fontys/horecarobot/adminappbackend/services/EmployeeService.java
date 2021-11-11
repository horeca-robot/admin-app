package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.EmployeeRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.EmployeeResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeUserRepository employeeUserRepository;

    public boolean doesEmployeeExist(UUID id) {
        return employeeUserRepository.findById(id).isPresent();
    }

    public List<EmployeeResponseModel> getAllEmployees() {
        return employeeUserRepository.findAll()
                .stream()
                .map(EmployeeResponseModel::new)
                .collect(Collectors.toList());
    }

    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        var e = employeeUserRepository.saveAndFlush(convertFromEmployeeModel(employeeRequestModel));
        return new EmployeeResponseModel(e);
    }

    public void updateEmployee(EmployeeRequestModel employeeRequestModel) {
        employeeUserRepository.saveAndFlush(convertFromEmployeeModel(employeeRequestModel));
    }

    public void deleteEmployee(UUID id) {
        employeeUserRepository.deleteById(id);
    }

    private EmployeeUser convertFromEmployeeModel(EmployeeRequestModel employeeRequestModel) {
        var employee = new EmployeeUser();
        employee.setUsername(employeeRequestModel.getUsername());
        employee.setPincode(employeeRequestModel.getPin());
        return employee;
    }

}
