package com.ftn.restaurant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.restaurant.dto.EmployeeDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@WithMockUser(username="admin", roles= {"ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class EmployeeControllerIntegrationTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    private final EmployeeDTO newEmployee = new EmployeeDTO();
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        newEmployee.setUsername("User123");
    	newEmployee.setPassword("pass123");
    	newEmployee.setName("Pera");
    	newEmployee.setSurname("Peric");
    	newEmployee.setTelephone("1234");
    	newEmployee.setRole("CHEF");
    	newEmployee.setImage("img");
    }
    
    @Test
    public void addEmployeeTest() throws Exception{
        
		mockMvc.perform(post("/api/employees/addUser").content(json(newEmployee)).contentType(contentType)).andExpect(status().isOk());
		
    }
    
    @Test
    public void addEmployeeTest_User_Exists_Exception() throws IOException, Exception {
    	EmployeeDTO user = new EmployeeDTO(0L, "waiter", "123", "pera", "peric", "img", "123654", "WAITER");
    	mockMvc.perform(post("/api/employees/addUser").content(json(user)).contentType(contentType)).andExpect(status().isForbidden());
		
    }
   
    @Test
    public void addEmployeeTest_Role_Does_Not_Exist_Exception() throws Exception {
    	EmployeeDTO user = new EmployeeDTO(0L, "abc", "123", "pera", "peric", "img", "123654", "WAITERX");
		mockMvc.perform(post("/api/employees/addUser").content(json(user)).contentType(contentType)).andExpect(status().isBadRequest());
		
    }
    
    @Test
    public void editEmployeeTest() throws IOException, Exception {
    	EmployeeDTO user = new EmployeeDTO(0L, "chef", "123", "perica", "peric", "img", "123654", "CHEF");
		mockMvc.perform(post("/api/employees/editUser").content(json(user)).contentType(contentType)).andExpect(status().isOk());
		
    }
    
    @Test
    public void editEmployeeTest_User_Not_Found_Exception() throws IOException, Exception {
    	EmployeeDTO user = new EmployeeDTO(0L, "chef123456", "123", "perica", "peric", "img", "123654", "CHEF");
    	mockMvc.perform(post("/api/employees/editUser").content(json(user)).contentType(contentType)).andExpect(status().isNotFound());
		
    }
    
    @Test
    public void deleteEmployeeTest() throws Exception {
    	mockMvc.perform(delete("/api/employees/deleteUser/chef")).andExpect(status().isOk());
    }
    
    @Test
    public void deleteEmployeeTest_User_Not_Found_Exception() throws Exception {
    	mockMvc.perform(delete("/api/employees/deleteUser/chef123456")).andExpect(status().isNotFound());
    }
    
    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        return mapper.writeValueAsString(object);

    }
}
