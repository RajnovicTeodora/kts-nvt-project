package com.ftn.restaurant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.nio.charset.Charset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuControllerIntergationTest {
    
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
    public void searchMenuItemsTest_Success() throws Exception {
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/.../...")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/drink/...")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/dish/...")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/MAIN_DISH/...")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/COLD_DRINK/...")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/MAIN_DISH/Burger")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/MAIN_DISH/ll")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    	
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/.../a")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    	
    }
    
    @Test
    public void searchMenuItems_Invalid_Menu_Item_Group_Exception() throws Exception {
    	
    	mockMvc.perform(get("/api/menu/searchMenuItems/CAJEVI/...")).andExpect(status().isNotFound());
    	
    }
    
    

}

