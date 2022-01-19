package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.OrderAlreadyPaidException;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderedItemService {
    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuItemService menuItemService;

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
          return  "You accepted order with id "+ id;
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
            return  "You finished order with id "+ id;
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

    public List<OrderedItem> findAllByOrderId(long id){
        return orderedItemRepository.findAllByOrderId(id);
    }

    public Order findOrderByOrderedItemId(long id){
        return orderedItemRepository.findOrderByOrderedItemId(id);
    }

    public String confirmPickup(long id){
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if(item.isPresent()){
            if(item.get().isDeleted()){
                throw new BadRequestException("Can't deliver DELETED ordered item.");
            }
            else if(item.get().getStatus() != OrderedItemStatus.READY ){
                throw new ForbiddenException("Can't deliver ordered item when status is not READY.");
            }
            item.get().setStatus(OrderedItemStatus.DELIVERED);
            orderedItemRepository.save(item.get());
            return  "Successfully delivered ordered item with id: "+ id;
        }
        throw new NotFoundException("Couldn't find ordered item.");
    }

    public String deleteOrderedItem(long id){
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if(item.isPresent()){
            if(item.get().isDeleted()){
                throw new BadRequestException("Already deleted ordered item with id: "+ id);
            }
            else if(item.get().getStatus() != OrderedItemStatus.ORDERED){
                throw new ForbiddenException("Can't delete ordered item with id: "+ id);
            }
            item.get().setDeleted(true);
            orderedItemRepository.save(item.get());
            return  "Successfully deleted ordered item with id: "+ id;
        }
        throw new NotFoundException("Couldn't find ordered item.");
    }

    public OrderedItem updateOrderedItem(long id, OrderItemDTO orderItemDTO){
        Order order = findOrderByOrderedItemId(id);
        if(order != null) {
            if (order.isPaid()) {
                throw new OrderAlreadyPaidException("Can't change order that is already paid.");
            }
            OrderedItem orderItem = this.orderedItemRepository.findOneWithActiveIngredients(id);
            if (orderItem.isDeleted()) {
                throw new BadRequestException("Can't update deleted ordered item with id: " + id);
            }
            if (orderItem.getStatus() != OrderedItemStatus.ORDERED) {
                throw new ForbiddenException("Can't change ordered item in preparation.");
            }
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPriority(orderItemDTO.getPriority());
            orderItem.setActiveIngredients(new ArrayList<>());
            for (IngredientDTO ingredientDTO : orderItemDTO.getActiveIngredients()) {
                Optional<Ingredient> i = ingredientService.findByIngredientNameAndIsAlergen(ingredientDTO.getName(), ingredientDTO.isAlergen());
                i.ifPresent(orderItem::addActiveIngredients);
            }
            save(orderItem);
            return orderItem;
        }
        throw new NotFoundException("Couldn't find order.");
    }

    public OrderedItem addOrderItemToOrder(long id, OrderItemDTO orderItemDTO){
        Order order = orderService.findOneWithOrderItems(id);
        if(order != null) {
            if (order.isPaid()) {
                throw new OrderAlreadyPaidException("Can't add order items to order that is already paid.");
            }
            OrderedItem orderItem = new OrderedItem();
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPriority(orderItemDTO.getPriority());
            Optional<MenuItem> menuItem = menuItemService.findByMenuItemNameAndImage(orderItemDTO.getMenuItem().getName(), orderItemDTO.getMenuItem().getImage());
            menuItem.ifPresent(orderItem::setMenuItem);
            orderItem.setDeleted(false);
            orderItem.setStatus(OrderedItemStatus.ORDERED);
            orderItem.setActiveIngredients(new ArrayList<>());
            for (IngredientDTO ingredientDTO : orderItemDTO.getActiveIngredients()) {
                Optional<Ingredient> i = ingredientService.findByIngredientNameAndIsAlergen(ingredientDTO.getName(), ingredientDTO.isAlergen());
                orderItem.addActiveIngredients(i.get());
            }
            order.addOrderedItem(orderItem);
            orderService.save(order);
            save(orderItem);

            return orderItem;
        }
        throw new NotFoundException("Couldn't find order.");
    }

    public List<OrderItemDTO> findAllByOrderIdDTO(long id){
        List<OrderItemDTO> listItems = new ArrayList<>();
        for(OrderedItem item : orderedItemRepository.findAllByOrderIdNotDeletedAndNew(id)){//findAllByOrderId(id)){//
            OrderItemDTO dto = new OrderItemDTO(item,""); //todo ovo promeni, jer izlazi neki load exc
            listItems.add(dto);
        }
        return listItems;
    }
}
