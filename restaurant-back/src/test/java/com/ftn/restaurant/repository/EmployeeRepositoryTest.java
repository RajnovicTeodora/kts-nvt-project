package com.ftn.restaurant.repository;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EmployeeRepositoryTest {
	
	@Autowired
    private EmployeeRepository employeeRepository;

	@Test
	public void findByUsernameTest() {
		assertEquals("manager", employeeRepository.findByUsernameAndNotDeleted("manager").get().getUsername());
		assertEquals(Optional.empty(), employeeRepository.findByUsernameAndNotDeleted("Deleted"));
	}

}
