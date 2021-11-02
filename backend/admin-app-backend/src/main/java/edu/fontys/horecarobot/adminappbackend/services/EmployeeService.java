package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeUserRepository employeeUserRepository;

    public boolean doesEmployeeExist(UUID id) {
        return employeeUserRepository.findById(id).isPresent();
    }

    public List<EmployeeUser> getAllEmployeeUsers() {
        return employeeUserRepository.findAll();
    }

    public void addEmployeeUser(EmployeeModel employeeModel) {
        employeeUserRepository.saveAndFlush(convertFromEmployeeModel(employeeModel));
    }

    public void updateEmployeeUser(EmployeeModel employeeModel) {
        employeeUserRepository.saveAndFlush(convertFromEmployeeModel(employeeModel));
    }

    public void deleteEmployeeUser(UUID id) {
        employeeUserRepository.deleteById(id);
    }

    private EmployeeUser convertFromEmployeeModel(EmployeeModel employeeModel) {
        var employee = new EmployeeUser();
        employee.setUsername(employeeModel.getUsername());
        employee.setPincode(employeeModel.getPin());
        return employee;
    }
}
