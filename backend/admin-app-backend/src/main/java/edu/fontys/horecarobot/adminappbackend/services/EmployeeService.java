package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeUserRepository employeeUserRepository;

    public void addEmployeeUser(EmployeeModel employeeModel) {
        EmployeeUser employeeUser = new EmployeeUser();

        employeeUser.setUsername(employeeModel.getUsername());
        employeeUser.setPincode(employeeModel.getPin());

        employeeUserRepository.save(employeeUser);
        employeeUserRepository.flush();
    }

    public ArrayList<EmployeeUser> getAllEmployeeUsers() {
        return (ArrayList<EmployeeUser>) employeeUserRepository.findAll();
    }
}
