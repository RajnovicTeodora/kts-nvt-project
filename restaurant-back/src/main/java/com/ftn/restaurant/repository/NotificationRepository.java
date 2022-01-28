package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Notification;
import com.ftn.restaurant.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.recipient.username = ?1 and n.isActive = true")
    List<Notification> getAllActiveNotificationsForEmployee(String username);

    @Query("select n from Notification n where n.recipient.username = ?1")
    List<Notification> getAllNotificationsForEmployee(String username);

    @Query("select n from Notification n where n.id = ?1")
    Optional<Notification> findById(long id);
}
