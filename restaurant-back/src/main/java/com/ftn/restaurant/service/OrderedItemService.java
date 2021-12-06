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

    public OrderedItem save(OrderedItem orderItem) {
        return orderedItemRepository.save(orderItem);
    }

    public OrderedItem findOne(long id){
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);
        return item.orElse(null);
    }

    public String confirmPickup(long id){
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if(item.isPresent() && item.get().getStatus() == OrderedItemStatus.READY && !item.get().isDeleted()){
            item.get().setStatus(OrderedItemStatus.DELIVERED);
            orderedItemRepository.save(item.get());
            return  "You delivered ordered item with id: "+ id;
        }
        return "Ordered item doesn't exists";
    }

    public String deleteOrderedItem(long id){
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if(item.isPresent()){
            if(item.get().isDeleted()){
                return  "Already deleted ordered item with id: "+ id;
            }
            else if(item.get().getStatus() != OrderedItemStatus.ORDERED){
                return  "Can't delete ordered item with id: "+ id;
            }
            item.get().setDeleted(true);
            orderedItemRepository.save(item.get());
            return  "You deleted ordered item with id: "+ id;
        }
        return "Ordered item doesn't exists";
    }

}
