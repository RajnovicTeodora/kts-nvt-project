package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NotificationDTO;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Notification;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    public List<NotificationDTO> getAllActiveNotificationsForEmployee(String username){

        List<Notification> nt = notificationRepository
                .getAllActiveNotificationsForEmployee(userService.findUserIdByUsername(username));
        List<NotificationDTO> dto = new ArrayList<>();
        for (Notification n : nt) {
            dto.add(new NotificationDTO(n.getId(), n.getText(), n.isActive()));
        }
        return dto;
    }

    public List<NotificationDTO> getAllNotificationsForEmployee(String username){

        List<Notification> nt = notificationRepository
                .getAllNotificationsForEmployee(userService.findUserIdByUsername(username));
        List<NotificationDTO> dto = new ArrayList<>();
        for (Notification n : nt) {
            dto.add(new NotificationDTO(n.getId(), n.getText(), n.isActive()));
        }
        return dto;
    }

    public void sendNoteChangedNotification(int orderNum, int tableNum, Employee employee){
        Notification notification = new Notification();
        notification.setRecipient(employee);
        notification.setActive(true);
        notification.setText("Note changed for order number " + orderNum + ", at table number " + tableNum);
        notificationRepository.save(notification);
    }

    public void sendOrderedItemStatusChangedNotification(int orderNum, int tableNum, Employee employee,
                                                         OrderedItemStatus status, String menuItemName){
        Notification notification = new Notification();
        notification.setRecipient(employee);
        notification.setActive(true);
        notification.setText("Status changed for " + menuItemName + " to " +
                status.name() + " for order number " + orderNum + ", at table number " + tableNum);
        notificationRepository.save(notification);
    }

    public String setNotificationInactive(long notificationId){
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if(notification.isPresent()){
            notification.get().setActive(false);
            notificationRepository.save(notification.get());
            return "Successfully changed notification to inactive.";
        }
        throw new NotFoundException("Couldn't find notification with id: " + notificationId);
    }

}
