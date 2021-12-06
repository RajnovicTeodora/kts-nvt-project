package com.ftn.restaurant.service;

import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderedItemService {
    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    public OrderedItemService(OrderedItemRepository orderedItemRepository) {
        this.orderedItemRepository = orderedItemRepository;
    }

    public String acceptOrderedItem(long id) { //setovati i uloge todo
        for(OrderedItem it: this.orderedItemRepository.findAll()){
            System.out.println(it.getId());
            System.out.println(it.getStatus());
        }

        Optional<OrderedItem> item = this.orderedItemRepository.findWithId(id);
        if (item.isPresent()){
          if(item.get().getStatus() != OrderedItemStatus.ORDERED && !item.get().isDeleted()){
              return "You can't accept order if it is not in status ordered.";
          }
          item.get().setStatus(OrderedItemStatus.IN_PROGRESS);
          this.orderedItemRepository.save(item.get());
          return  "You accepted order with id: "+ id;
        }
        return "Order doesn't exists";
    }

    public String finishOrderedItem(long id) {
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if (item.isPresent()){
            if(item.get().getStatus() != OrderedItemStatus.IN_PROGRESS && !item.get().isDeleted()){
                return "You can't finish order if it is not in status in progres.";
            }

            item.get().setStatus(OrderedItemStatus.READY);
            String message = "Item " + item.get().getMenuItem().getName() + " is finished.";
            Notification n = new Notification(item.get(), message);
            item.get().getOrder().getWaiter().getNotifications().add(n);
            this.orderedItemRepository.save(item.get());
            return  "You finished order with id: "+ id;
        }
        return "Order doesn't exists";
    }

    public OrderedItem findOne(Long id) {
        return orderedItemRepository.findById(id).orElseGet(null);
    }

    public OrderedItem save(OrderedItem orderItem) {
        return orderedItemRepository.save(orderItem);
    }

    public void confirmPickup(Long id){
        OrderedItem oi = findOne(id);
        if(oi != null && oi.getStatus() == OrderedItemStatus.READY){
            oi.setStatus(OrderedItemStatus.DELIVERED);
            save(oi);
        }
    }

}
