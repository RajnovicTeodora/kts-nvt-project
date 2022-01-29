package com.ftn.restaurant.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;

import static com.ftn.restaurant.constants.PaycheckConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "manager", roles = {"MANAGER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PaycheckControllerIntegrationTest {

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
    public void testChangePaycheck() throws Exception {

        String dto = json(INVALID_USERNAME_PAYCHECK_DTO);

        mockMvc.perform(post("/api/paychecks/changePaycheck").contentType(contentType).content(dto))
                .andExpect(status().isNotFound());

        ////////////////////////////////////////

        String dto1 = json(INVALID_NUMBER_PAYCHECK_DTO);

        mockMvc.perform(post("/api/paychecks/changePaycheck").contentType(contentType).content(dto1))
                .andExpect(status().isForbidden());

        ////////////////////////////////////////

        String dto2 = json(UPDATE_PAYCHECK_DTO);

        mockMvc.perform(post("/api/paychecks/changePaycheck").contentType(contentType).content(dto2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.paycheck").value(199));


        ////////////////////////////////////////

        String dto3 = json(UPDATE_PAYCHECK_DTO1);

        mockMvc.perform(post("/api/paychecks/changePaycheck").contentType(contentType).content(dto3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paycheck").value(29));

    }

    public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper.writeValueAsString(object);
    }
}
