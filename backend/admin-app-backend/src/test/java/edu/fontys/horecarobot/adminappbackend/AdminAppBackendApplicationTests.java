package edu.fontys.horecarobot.adminappbackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.fontys.horecarobot.adminappbackend.dtos.EmployeeModel;
import edu.fontys.horecarobot.adminappbackend.services.EmployeeService;
import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import java.util.List;

@SpringBootTest
class AdminAppBackendApplicationTests {

	@Autowired private EmployeeService employeeService;

	@Test
	void TestGetEmployeeUsers() {
		List<EmployeeUser> employees = employeeService.getAllEmployeeUsers();

        Assertions.assertThat(employees).hasSizeGreaterThan(0);

        for (EmployeeUser employee : employees) {
            System.out.println(employee);
        }
	}

	@Test
	void TestAddEmployeeUser() {
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setUsername("employee1");
		employeeModel.setPin((short) 1234);

		employeeService.addEmployeeUser(employeeModel);

		List<EmployeeUser> employees = employeeService.getAllEmployeeUsers();	
        Assertions.assertThat(employees).hasSizeGreaterThan(0);

        for (EmployeeUser employee : employees) {
            System.out.println(employee);
        }
	}
}
