package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Notification;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.NotificationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class NotificationServiceUnitTest {

    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private UserService userService;

    @Test(expected = EmployeeNotFoundException.class )
    public void getAllActiveNotificationsForEmployee_ThrowEmployeeNotFoundException(){
        Mockito.when(userService.findByUsername("nonExisting")).thenReturn(null);
        notificationService.getAllActiveNotificationsForEmployee("nonExisting");
    }

    @Test
    public void getAllActiveNotificationsForEmployee_ReturnEmptyListOfNotificationsDTOWhenNotificationsNonExisting(){
        Mockito.when(userService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(notificationRepository.getAllActiveNotificationsForEmployee("waiter")).thenReturn(null);
        Assert.assertTrue(notificationService.getAllActiveNotificationsForEmployee("waiter").isEmpty());
    }

    @Test
    public void getAllActiveNotificationsForEmployee_ReturnListOfNotificationsDTOWhenAllOk(){
        Notification n1 = new Notification(1L, true, "text1");
        Notification n2 = new Notification(2L, true, "text2");
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(n1);
        notificationList.add(n2);
        Mockito.when(userService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(notificationRepository.getAllActiveNotificationsForEmployee("waiter")).thenReturn(notificationList);
        Assert.assertEquals(2, notificationService.getAllActiveNotificationsForEmployee("waiter").size());
    }

    @Test(expected = EmployeeNotFoundException.class )
    public void getAllNotificationsForEmployee_ThrowEmployeeNotFoundException(){
        Mockito.when(userService.findByUsername("nonExisting")).thenReturn(null);
        notificationService.getAllNotificationsForEmployee("nonExisting");
    }

    @Test
    public void getAllNotificationsForEmployee_ReturnEmptyListOfNotificationsDTOWhenNotificationsNonExisting(){
        Mockito.when(userService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(notificationRepository.getAllNotificationsForEmployee("waiter")).thenReturn(null);
        Assert.assertTrue(notificationService.getAllNotificationsForEmployee("waiter").isEmpty());
    }

    @Test
    public void getAllNotificationsForEmployee_ReturnListOfNotificationsDTOWhenAllOk(){
        Notification n1 = new Notification(1L, true, "text1");
        Notification n2 = new Notification(2L, false, "text2");
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(n1);
        notificationList.add(n2);
        Mockito.when(userService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(notificationRepository.getAllNotificationsForEmployee("waiter")).thenReturn(notificationList);
        Assert.assertEquals(2, notificationService.getAllNotificationsForEmployee("waiter").size());
    }

    @Test(expected = NotFoundException.class )
    public void setNotificationInactive_ThrowNotFoundException(){
        Mockito.when(notificationRepository.findById(-1L)).thenReturn(Optional.empty());
        notificationService.setNotificationInactive(-1L);
    }

    @Test
    public void setNotificationInactive_ReturnStringWhenAllOk(){
        Notification n1 = new Notification(1L, true, "text1");
        Mockito.when(notificationRepository.findById(1L)).thenReturn(Optional.of(n1));
        Assert.assertEquals("Successfully changed notification to inactive." , notificationService.setNotificationInactive(1L));
    }
}
