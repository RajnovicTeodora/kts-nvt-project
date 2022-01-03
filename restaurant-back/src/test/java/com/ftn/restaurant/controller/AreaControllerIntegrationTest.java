package com.ftn.restaurant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@WithMockUser(username="admin", roles= {"ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AreaControllerIntegrationTest {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addAreaTest() throws Exception{
        	
		mockMvc.perform(post("/api/area/addArea").content("Roof").contentType(contentType)).andExpect(status().isOk());
    	                   
    }
    
    @Test
    public void addAreaTest_Area_Already_Exists() throws Exception {
    	
    	mockMvc.perform(post("/api/area/addArea").content("First floor").contentType(contentType)).andExpect(status().isBadRequest());
    	
    }
    
    @Test
    public void deleteArea_Success() throws Exception {

    	mockMvc.perform(delete("/api/area/deleteArea/3")).andExpect(status().isOk());
    	
    }
    
    @Test
    public void deleteArea_Area_Not_Found_Exception() throws Exception {

    	mockMvc.perform(delete("/api/area/deleteArea/6")).andExpect(status().isNotFound());
    	
    }
    
    @Test
    public void deleteArea_Tables_Occupied_Exception() throws Exception {

    	mockMvc.perform(delete("/api/area/deleteArea/1")).andExpect(status().isForbidden());
    	
    }
    
}
