package com.ftn.restaurant.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WithMockUser(username = "manager", roles = {"MANAGER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuControllerIntegrationTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testToggleIsMenuItemActive() throws Exception {

        // Id invalid
        mockMvc.perform(put("/api/menu/toggleIsActive").param("id", "15").contentType(contentType))
                .andDo(print())
                .andExpect(status().isForbidden());

        // Item doesn't have a price
        mockMvc.perform(put("/api/menu/toggleIsActive").param("id", "2").contentType(contentType))
                .andDo(print())
                .andExpect(status().isForbidden());

        // False to True
        mockMvc.perform(put("/api/menu/toggleIsActive").param("id", "1").contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.active").value(true));

        // True to False
        mockMvc.perform(put("/api/menu/toggleIsActive").param("id", "3").contentType(contentType))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    public void testGetActiveMenuItems() throws Exception {
        mockMvc.perform(get("/api/menu/getAll").param("searchName", "").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(5)));

        mockMvc.perform(get("/api/menu/getAll").param("searchName", "Ice").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].menuItem.name").value("Ice Latte"));

        mockMvc.perform(get("/api/menu/getAll").param("searchName", "random").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void testDeleteMenuItems() throws Exception {
        mockMvc.perform(delete("/api/menu/deleteMenuItem/9").contentType(contentType))
                .andDo(print())
                .andExpect(status().isForbidden());

        mockMvc.perform(delete("/api/menu/deleteMenuItem/6").contentType(contentType))
                .andExpect(status().isForbidden());


        mockMvc.perform(delete("/api/menu/deleteMenuItem/5").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType));
    }


}
