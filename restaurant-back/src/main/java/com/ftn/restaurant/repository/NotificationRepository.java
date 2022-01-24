package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.Notification;
import com.ftn.restaurant.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.waiter.id = ?1")
    List<Notification> getAllActiveNotificationsForWaiter(Long id);
}
