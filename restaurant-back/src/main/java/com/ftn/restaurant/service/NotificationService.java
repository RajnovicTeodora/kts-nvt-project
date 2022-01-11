package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NotificationDTO;
import com.ftn.restaurant.model.Notification;
import com.ftn.restaurant.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public List<NotificationDTO> getAllActiveNotificationsForWaiterUsername(String username){

        List<Notification> nt = notificationRepository.getAllActiveNotificationsForWaiter(userService.findUserIdByUsername(username));
        List<NotificationDTO> dto = new ArrayList<>();
        for (Notification n : nt) {
            dto.add(new NotificationDTO(n.getText(), n.isActive(), n.getItem().getId()));
        }
        return dto;
    }



}
