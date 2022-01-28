package com.ftn.restaurant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import java.nio.charset.Charset;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.restaurant.dto.RestaurantTableDTO;

@WithMockUser(username="waiter", roles= {"WAITER", "ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableControllerIntegrationTest {

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
    public void occupyTableTest() throws Exception {
        mockMvc.perform(get("/api/table/1/occupyTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully occupied table with table id: 1"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/occupyTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already occupied"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/occupyTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with table id: 1000"));

    }

    @Test
    public void clearTableTest() throws Exception {
        mockMvc.perform(get("/api/table/5/clearTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully cleared table with table id: 5"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/4/clearTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already cleared"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/clearTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with table id: 1000"));

        /////////////////////////////

        mockMvc.perform(get("/api/table/2/clearTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't unoccupy table when there are active orders."));

    }

    @Test
    public void claimTableTest() throws Exception {
        mockMvc.perform(get("/api/table/3/claimTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully claimed table with table id: 3"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/claimTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Table is already claimed"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/claimTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with table id: 1000"));

    }

    @Test
    public void leaveTableTest() throws Exception {
        mockMvc.perform(get("/api/table/6/leaveTable/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully left table with table id: 6"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/2/leaveTable/waiter"))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Can't leave table while its occupied"));

        //////////////////////////////

        mockMvc.perform(get("/api/table/1000/leaveTable/waiter"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with table id: 1000"));

    }

    @Test
    public void getTableByTableIdTest() throws Exception {
        mockMvc.perform(get("/api/table/getTableByTableId/7"))
                .andExpect(status().isOk());

        /////////////////////////////

        mockMvc.perform(get("/api/table/getTableByTableId/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find table with table id: 1000"));

    }
    
    @Test
    public void addTableTest_Success() throws Exception{
    	
    	RestaurantTableDTO table = new RestaurantTableDTO(12, 12, 1L);
    	
    	mockMvc.perform(post("/api/table/addTable").content(json(table)).contentType(contentType)).andExpect(status().isOk());
    	
    }
    
    @Test
    public void addTable_Area_Not_Found_Exception() throws Exception {
    	
    	RestaurantTableDTO table = new RestaurantTableDTO(12, 12, 13L);
    	
    	mockMvc.perform(post("/api/table/addTable").content(json(table)).contentType(contentType)).andExpect(status().isNotFound());
    	
    }
    
    @Test
    public void deleteTable_Success() throws Exception {
    	
    	mockMvc.perform(delete("/api/table/deleteTable/3")).andExpect(status().isOk());
    
    }
    
    @Test
    public void deleteTable_Table_Is_Occupied_Exception() throws Exception {
    	
    	mockMvc.perform(delete("/api/table/deleteTable/2")).andExpect(status().isForbidden());
    }
    
    @Test
    public void deleteTable_Table_Not_Found_Exception() throws Exception {
    	
    	mockMvc.perform(delete("/api/table/deleteTable/123")).andExpect(status().isNotFound());
    }
    
    private static String json(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        return mapper.writeValueAsString(object);
    }

}
