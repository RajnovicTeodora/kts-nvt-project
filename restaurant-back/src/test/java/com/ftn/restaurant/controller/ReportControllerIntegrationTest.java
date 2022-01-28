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

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WithMockUser(username = "manager", roles = {"MANAGER"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReportControllerIntegrationTest {

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
    public void testIncomeAndSoldMonthlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/monthly/incomeAndSold"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].period").value("2021-10"))
                .andExpect(jsonPath("$.[2].period").value("2021-12"));
    }


    @Test
    public void testIncomeAndSoldQuarterlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/quarterly/incomeAndSold"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].period").value("Q4 - 2021"))
                .andExpect(jsonPath("$.[0].totalSoldItems").value(23));
    }

    @Test
    public void testIncomeAndSoldAnnualReport() throws Exception {
        mockMvc.perform(get("/api/reports/annual/incomeAndSold"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].period").value("2021"))
                .andExpect(jsonPath("$.[0].totalSoldItems").value(23));
    }

    @Test
    public void testPreparationCostsMonthlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/monthly/preparationCosts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].period").value("2021-10"))
                .andExpect(jsonPath("$.[2].period").value("2021-12"));
    }

    @Test
    public void testPreparationCostsQuarterlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/quarterly/preparationCosts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].period").value("Q4 - 2021"))
                .andExpect(jsonPath("$.[0].totalPreparationCosts").value(100));
    }

    @Test
    public void testPreparationCostsAnnualReport() throws Exception {
        mockMvc.perform(get("/api/reports/annual/preparationCosts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].period").value("2021"))
                .andExpect(jsonPath("$.[0].totalPreparationCosts").value(100));
    }

    @Test
    public void testPaychecksMonthlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/monthly/paychecks"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[0].period").value("2021-09"))
                .andExpect(jsonPath("$.[3].period").value("2021-12"));
    }

    @Test
    public void testPaychecksQuarterlyReport() throws Exception {
        mockMvc.perform(get("/api/reports/quarterly/paychecks"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].period").value("Q3 - 2021"))
                .andExpect(jsonPath("$.[1].period").value("Q4 - 2021"));
    }

    @Test
    public void testPaychecksAnnualReport() throws Exception {
        mockMvc.perform(get("/api/reports/annual/paychecks"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].period").value("2021"))
                .andExpect(jsonPath("$.[0].totalPaychecks").value(100));
    }
}
