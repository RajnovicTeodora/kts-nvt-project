package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderedItemService {

    private OrderedItemRepository orderedItemRepository;

    @Autowired
    public OrderedItemService(OrderedItemRepository orderedItemRepository) {
        this.orderedItemRepository = orderedItemRepository;
    }

    public String acceptOrderedItem(long id) { //setovati i uloge todo
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);
        if (item.isPresent()){
          if(item.get().getStatus() != OrderedItemStatus.ORDERED){
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
            if(item.get().getStatus() != OrderedItemStatus.IN_PROGRESS){
                return "You can't finish order if it is not in status in progres.";
            }

            item.get().setStatus(OrderedItemStatus.READY);
            String message = "Item " + item.get().getManuItem().getName() + " is finished.";
            Notification n = new Notification(item.get(), message);
            item.get().getOrder().getWaiter().getNotifications().add(n);
            this.orderedItemRepository.save(item.get());
            return  "You finished order with id: "+ id;
        }
        return "Order doesn't exists";
    }
}
