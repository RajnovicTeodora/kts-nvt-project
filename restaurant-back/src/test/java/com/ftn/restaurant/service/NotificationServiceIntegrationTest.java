package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class NotificationServiceIntegrationTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    public void getAllActiveNotificationsForEmployeeTest(){
        Assert.assertEquals(2,notificationService.getAllActiveNotificationsForEmployee("waiter").size());
        //"Couldn't find employee with username nonExisting"
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {notificationService.getAllActiveNotificationsForEmployee("nonExisting");});
    }

    @Test
    public void getAllNotificationsForEmployeeTest(){
        Assert.assertEquals(3,notificationService.getAllNotificationsForEmployee("waiter").size());
        //"Couldn't find employee with username nonExisting"
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {notificationService.getAllNotificationsForEmployee("nonExisting");});
    }

    @Test
    public void setNotificationInactiveTest() {
        //ok
        Assert.assertEquals("Successfully changed notification to inactive.",notificationService.setNotificationInactive(2));
        //"Couldn't find notification with id: -1"
        Assertions.assertThrows(NotFoundException.class, () -> {notificationService.setNotificationInactive(-1);});
    }
}
