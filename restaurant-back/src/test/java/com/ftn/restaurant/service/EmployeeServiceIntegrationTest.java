package com.ftn.restaurant.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.exception.BadUserRoleException;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.UsernameExistsException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EmployeeServiceIntegrationTest {
	
	@Autowired
	private UserService employeeService;
	
	@Test
	public void addEmployeeTest_Success() {
		EmployeeDTO user = new EmployeeDTO(0L, "perica", "123", "pera", "peric", "img", "123654", "WAITER");
		Assert.assertEquals("perica", employeeService.addUser(user).getUsername());
	}
	
	@Test(expected = UsernameExistsException.class)
	public void addEmployeeTest_User_Exists_Exception() throws Exception {
		EmployeeDTO user = new EmployeeDTO(0L, "waiter", "123", "pera", "peric", "img", "123654", "WAITER");
		employeeService.addUser(user);
	}
	
	@Test(expected = BadUserRoleException.class)
	public void addEmployeeTest_Bad_Role_Exception() throws Exception {
		EmployeeDTO user = new EmployeeDTO(0L, "perica", "123", "pera", "peric", "img", "123654", "WAITERX");
		employeeService.addUser(user);
	}
	
	@Test
	public void editEmployeeTest_Success() {
		EmployeeDTO user = new EmployeeDTO(0L, "waiter", "123", "perica", "peric", "img", "123654", "WAITER");
		Assert.assertEquals("perica", employeeService.editUser(user).getName());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void editEmployeeTest_Employee_Not_Found_Exception() throws Exception {
		EmployeeDTO user = new EmployeeDTO(0L, "perica123", "123", "pera", "peric", "img", "123654", "WAITER");
		employeeService.editUser(user);
	}
	
	@Test
	public void deleteEmployeeTest_Success() {
		Assert.assertEquals("chef", employeeService.deleteUser("chef").getUsername());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void deleteEmployeeTest_Employee_Not_Found_Exception() throws Exception{
		employeeService.deleteUser("abcd");
	}

}
