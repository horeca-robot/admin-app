package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import edu.fontys.horecarobot.databaselibrary.repositories.EmployeeUserRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeUserRepository employeeUserRepository;

    public ArrayList<EmployeeUser> getAllEmployeeUsers() {
        return (ArrayList<EmployeeUser>) employeeUserRepository.findAll();
    }

    public void addEmployeeUser(EmployeeModel employeeModel) {
        EmployeeUser employeeUser = new EmployeeUser();

        employeeUser.setUsername(employeeModel.getUsername());
        employeeUser.setPincode(employeeModel.getPin());

        employeeUserRepository.save(employeeUser);
        employeeUserRepository.flush();
    }

    public void deleteEmployeeUser(EmployeeModel employeeModel) {    
        EmployeeUser exampleEmployeeUser = new EmployeeUser();    
        exampleEmployeeUser.setUsername(employeeModel.getUsername());
        exampleEmployeeUser.setPincode(employeeModel.getPin());  
        Example<EmployeeUser> example = Example.of(exampleEmployeeUser);
        
        Optional<EmployeeUser> optionalEmployeeUser = employeeUserRepository.findOne(example);

        if (optionalEmployeeUser.isPresent()) {
            EmployeeUser employeeUser = optionalEmployeeUser.get();       
            employeeUserRepository.delete(employeeUser);
        }  else {
            throw new UsernameNotFoundException("User with username not found!");
        }
    }
}
