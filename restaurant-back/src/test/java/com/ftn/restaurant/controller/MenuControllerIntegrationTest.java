package com.ftn.restaurant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static com.ftn.restaurant.constants.MenuConstants.*;

@WithMockUser(username = "manager", roles = {"MANAGER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuControllerIntegrationTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSetActiveMenuItems() throws Exception {

        /*
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(ITEM_ID_LIST);
        SelectedMenuItemsDTO invalidSelectedMenuItemsDTO = new SelectedMenuItemsDTO(INVALID_ITEM_ID_LIST);
        SelectedMenuItemsDTO emptySelectedMenuItemsDTO = new SelectedMenuItemsDTO(EMPTY_ITEM_ID_LIST);

        String json = json(selectedMenuItemsDTO);
        String invalidJson = json(invalidSelectedMenuItemsDTO);
        String emptyJson = json(emptySelectedMenuItemsDTO);

        mockMvc.perform(put("/api/menu/activateItems").contentType(contentType).content(json))
                .andExpect(status().isOk());

        //////////////////////////////

        mockMvc.perform(put("/api/menu/activateItems").content(invalidJson).contentType(contentType))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().string("Approved menu item with id 16 and active price not found."));

        //////////////////////////////

        mockMvc.perform(put("/api/menu/activateItems").content(emptyJson).contentType(contentType))
                .andExpect(status().isOk());*/
    }

    // *******************************************
    public static String json(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }

}
