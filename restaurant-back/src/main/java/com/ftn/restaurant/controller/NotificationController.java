package com.ftn.restaurant.controller;

import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @ResponseBody
    @GetMapping(value = "/getActiveNotificationsForEmployee/{username}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER')")
    public ResponseEntity<?> getAllActiveNotificationsForEmployee(@PathVariable("username") String username){
        try {
            return new ResponseEntity<>(notificationService.getAllActiveNotificationsForEmployee(username), HttpStatus.OK);
        }
        catch (EmployeeNotFoundException e){
            return new ResponseEntity<>("Couldn't find employee with username " + username, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/getAllNotificationsForEmployee/{username}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER')")
    public ResponseEntity<?> getAllNotificationsForEmployee(@PathVariable("username") String username){
        try {
            return new ResponseEntity<>(notificationService.getAllNotificationsForEmployee(username), HttpStatus.OK);
        }
        catch (EmployeeNotFoundException e){
            return new ResponseEntity<>("Couldn't find employee with username " + username, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/setNotificationInactive/{notificationId}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER')")
    public ResponseEntity<?> setNotificationInactive(@PathVariable("notificationId") long notificationId){
        try {
            return new ResponseEntity<>(notificationService.setNotificationInactive(notificationId), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find notification with id: " + notificationId, HttpStatus.NOT_FOUND);
        }
    }
}
