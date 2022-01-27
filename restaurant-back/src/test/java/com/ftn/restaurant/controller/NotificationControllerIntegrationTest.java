package com.ftn.restaurant.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username="waiter", roles= {"WAITER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class NotificationControllerIntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllActiveNotificationsForEmployeeTest() throws Exception {
        mockMvc.perform(get("/notification/getActiveNotificationsForEmployee/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        /////////////////////////////////////////

        mockMvc.perform(get("/notification/getActiveNotificationsForEmployee/nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find employee with username nonExisting" ));
    }

    @Test
    public void getAllNotificationsForEmployeeTest() throws Exception {
        mockMvc.perform(get("/notification/getAllNotificationsForEmployee/waiter"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

        /////////////////////////////////////////

        mockMvc.perform(get("/notification/getAllNotificationsForEmployee/nonExisting"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find employee with username nonExisting" ));
    }

    @Test
    public void setNotificationInactiveTest() throws Exception {
        mockMvc.perform(get("/notification/setNotificationInactive/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Couldn't find notification with id: -1" ));

        /////////////////////////////////////////

        mockMvc.perform(get("/notification/setNotificationInactive/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully changed notification to inactive." ));
    }
}
