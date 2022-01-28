package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.dto.reports.IncomeReportDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReportControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;

    @Before
    public void login() {
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity("/auth/login",
                        new LoginDTO("manager", "test"),
                        String.class);

        accessToken = responseEntity.getBody();
    }

    @Test
    public void testIncomeAndSoldMonthlyReport(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", accessToken);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

        ResponseEntity<IncomeReportDTO[]> responseEntity = restTemplate
            .exchange("/api/monthly/incomeAndSold", HttpMethod.GET, httpEntity, IncomeReportDTO[].class);

        IncomeReportDTO[] incomeReportDTOS = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, incomeReportDTOS.length);
        assertEquals(10, incomeReportDTOS[0].getTotalSoldItems());
        assertEquals(1, incomeReportDTOS[1].getEarnings(), 0);
    }

}
