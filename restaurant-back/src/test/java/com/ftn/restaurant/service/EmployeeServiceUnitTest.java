package com.ftn.restaurant.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.ArgumentMatchers.any;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.exception.BadUserRoleException;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.UsernameExistsException;
import com.ftn.restaurant.model.Chef;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.EmployeeRepository;
import com.ftn.restaurant.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EmployeeServiceUnitTest {
	
	@Autowired
	private UserService employeeService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private EmployeeRepository employeeRepository;
			
	private EmployeeDTO waiterDTO = new EmployeeDTO(0L, "waiter", "pas123", "zika", "mikic", "img", "123", "WAITER");
	
	private EmployeeDTO chefDTO = new EmployeeDTO(1L, "chef", "pas123", "mika", "mikic", "img", "123", "CHEF");
	
	@BeforeAll
	public void setup() {
		
		Waiter waiter = new Waiter(waiterDTO);
		Chef chef = new Chef(chefDTO);

		Mockito.when(employeeRepository.findByUsername("waiter")).thenReturn(Optional.of(waiter));
		Mockito.when(employeeRepository.findByUsername("perica")).thenReturn(Optional.empty());
		Mockito.when(employeeRepository.findByUsername("chef")).thenReturn(Optional.of(chef));
		
		Mockito.when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(null);
		Mockito.when(userRepository.saveAndFlush(any(Employee.class))).thenReturn(null);
		
		Mockito.when(employeeRepository.findByUsernameAndNotDeleted("perica")).thenReturn(Optional.empty());
		Mockito.when(employeeRepository.findByUsernameAndNotDeleted("waiter")).thenReturn(Optional.of(waiter));
		Mockito.when(employeeRepository.findByUsernameAndNotDeleted("chef")).thenReturn(Optional.of(chef));
	}
	
	@Test
	public void addEmployeeTest_Success() {
		EmployeeDTO user = new EmployeeDTO(0L, "perica", "1234", "pera", "peric", "img", "123654", "CHEF");
		Assert.assertEquals("perica", employeeService.addUser(user).getUsername());
	}
	
	@Test(expected = UsernameExistsException.class)
	public void addEmployeeTest_Employee_Exists_Exception() {
		EmployeeDTO waiterDTO = new EmployeeDTO(0L, "waiter2", "pas123", "zika", "mikic", "img", "123", "WAITER");
		Waiter waiter = new Waiter(waiterDTO);
		Mockito.when(employeeRepository.findByUsernameAndNotDeleted("waiter2")).thenReturn(Optional.of(waiter));
		employeeService.addUser(waiterDTO);
	}
	
	@Test(expected = BadUserRoleException.class)
	public void addEmployeeTest_Bad_Role_Exception() {
		EmployeeDTO user = new EmployeeDTO(0L, "perica", "123", "pera", "peric", "img", "123654", "WAITERX");
		employeeService.addUser(user);
	}
	
	@Test
	public void editEmployeeTest_Success() {
		EmployeeDTO editedDTO = new EmployeeDTO(0L, "waiter", "pas123", "perica", "mikic", "img", "123", "WAITER");
		Waiter waiter = new Waiter(editedDTO);
		Mockito.when(employeeRepository.findByUsername("waiter")).thenReturn(Optional.of(waiter));
		Assert.assertEquals("perica", employeeService.editUser(editedDTO).getName());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void editEmployeeTest_User_Not_Found_Exception() {
		EmployeeDTO user = new EmployeeDTO(0L, "perica", "123", "pera123", "peric", "img", "123654", "WAITER");
		employeeService.editUser(user);
	}
	
	@Test
	public void deleteEmployeeSuccess() {
		EmployeeDTO chefDTO = new EmployeeDTO(1L, "chef", "pas123", "mika", "mikic", "img", "123", "CHEF");
		Chef chef = new Chef(chefDTO);
		Mockito.when(employeeRepository.findByUsername("chef")).thenReturn(Optional.of(chef));
		Assert.assertEquals("chef", employeeService.deleteUser("chef").getUsername());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void deleteEmployeeTest_User_Not_Found_Exception() {
		employeeService.deleteUser("123");
	}
}
