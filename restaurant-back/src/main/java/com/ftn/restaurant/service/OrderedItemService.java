package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.*;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class OrderedItemService {
    @Autowired
    private OrderedItemRepository orderedItemRepository;

    @Autowired
    private BartenderRepository bartenderRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemPriceService menuItemPriceService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    public OrderedItemService(OrderedItemRepository orderedItemRepository, BartenderRepository bartenderRepository,
                              NotificationRepository notificationRepository) {

        this.orderedItemRepository = orderedItemRepository;
        this.bartenderRepository = bartenderRepository;
        this.notificationRepository = notificationRepository;
        this.chefRepository  = chefRepository;
    }

    public String acceptOrderedItem(long id, String username) {
        System.out.println("usloo");
        Optional<OrderedItem> item = this.orderedItemRepository.findWithId(id);
        if (item.isPresent()){
          if(item.get().getStatus() != OrderedItemStatus.ORDERED && !item.get().isDeleted()){
              return "You can't accept order if it is not in status ordered.";
          }
          User user = userService.findByUsername(username); //mora da ostane user zbog sefa
          if( user instanceof Bartender){
              ((Bartender)user).getOrderedItems().add(item.get());
              item.get().setWhoPreapiring((Bartender)user);
              item.get().setStatus(OrderedItemStatus.IN_PROGRESS);
              this.orderedItemRepository.save(item.get());
              this.bartenderRepository.save((Bartender) user);
              String message = "Item " + item.get().getMenuItem().getName() + " is accepted. Table " + item.get().getOrder().getRestaurantTable().getTableNum();
              Notification n = new Notification( message);
              n.setRecipient(item.get().getOrder().getWaiter());
              notificationRepository.save(n);
          }else{
                ((Chef)user).getOrderedItems().add(item.get());
                item.get().setWhoPreapiring((Chef)user);
                item.get().setStatus(OrderedItemStatus.IN_PROGRESS);
                this.orderedItemRepository.save(item.get());
                this.chefRepository.save((Chef) user);
                String message = "Item " + item.get().getMenuItem().getName() + " is accepted. Table " + item.get().getOrder().getRestaurantTable().getTableNum();
                Notification n = new Notification( message);
                n.setRecipient(item.get().getOrder().getWaiter());
                notificationRepository.save(n);
            }
          return  "You accepted order "+ item.get().getMenuItem().getName();
        }
        return "Order doesn't exists";
    }

    public String finishOrderedItem(long id) { //todo da li je izbrisana
        Optional<OrderedItem> item = this.orderedItemRepository.findById(id);

        if (item.isPresent()){
            if(item.get().getStatus() != OrderedItemStatus.IN_PROGRESS && !item.get().isDeleted()){
                return "You can't finish order if it is not in status in progres.";
            }
            item.get().setStatus(OrderedItemStatus.READY);
            String message = "Item " + item.get().getMenuItem().getName() + " is finished. Table " + item.get().getOrder().getRestaurantTable().getTableNum();
            Notification n = new Notification( message);
            n.setRecipient(item.get().getOrder().getWaiter());
            notificationRepository.save(n);
            //.getNotifications().add(n);
            //((Bartender)item.get().getWhoPreapiring()).getOrderedItems().remove(item); //Todo da li ovde ide save
            this.orderedItemRepository.save(item.get());
            return  "You finished order "+ item.get().getMenuItem().getName();
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
/*
    public Order findOrderByOrderedItemId(long id){
        return orderedItemRepository.findOrderByOrderedItemId(id);
    }*/

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
            Order order = orderedItemRepository.findOrderByOrderedItemId(id);
            orderedItemRepository.save(item.get());
            notificationService.sendOrderedItemStatusChangedNotification(order.getOrderNumber(),
                    order.getRestaurantTable().getTableNum(),
                    orderedItemRepository.findEmployeePreparingOrderedItemById(id),
                    OrderedItemStatus.DELIVERED,
                    orderedItemRepository.findMenuItemNameForOrderedItemId(id));
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

    public String updateOrderedItem( OrderItemDTO orderItemDTO){
        OrderedItem orderItem = this.orderedItemRepository.findOneWithActiveIngredients(orderItemDTO.getId());
        if(orderItem == null){
            throw new OrderedItemNotFoundException("Couldn't find ordered item.");
        }
        if (orderItem.isDeleted()) {
            throw new BadRequestException("Can't update deleted ordered item with id: " + orderItemDTO.getId());
        }
        if (orderItem.getStatus() != OrderedItemStatus.ORDERED) {
            throw new ForbiddenException("Can't change ordered item in preparation.");
        }
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPriority(orderItemDTO.getPriority());
        orderItem.setActiveIngredients(new ArrayList<>());
        for (IngredientDTO ingredientDTO : orderItemDTO.getActiveIngredients()) {
            Optional<Ingredient> i = ingredientService.findByIngredientId(ingredientDTO.getId());
            if(!i.isPresent()){
                throw new IngredientNotFoundException("Couldn't find ingredient with id: " + ingredientDTO.getId());
            }else
                orderItem.addActiveIngredients(i.get());
        }
        save(orderItem);
        return "Successfully updated ordered item with id: "+ orderItemDTO.getId();

    }

    public String addOrderItemToOrder(long id, OrderItemDTO orderItemDTO){
        Order order = orderService.findOne(id);
        if(order != null) {
            if (order.isPaid()) {
                throw new OrderAlreadyPaidException("Can't add order items to order that is already paid.");
            }
            OrderedItem orderItem = new OrderedItem();
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPriority(orderItemDTO.getPriority());
            orderItem.setOrder(order);
            Optional<MenuItem> menuItem = menuItemService.findByMenuItemId(orderItemDTO.getMenuItemId());
            if(!menuItem.isPresent()){
                throw new MenuItemNotFoundException("Couldn't find menu item with id: " + orderItemDTO.getMenuItemId());
            }
            menuItem.ifPresent(orderItem::setMenuItem);
            orderItem.setDeleted(false);
            orderItem.setStatus(OrderedItemStatus.ORDERED);
            orderItem.setActiveIngredients(new ArrayList<>());
            for (IngredientDTO ingredientDTO : orderItemDTO.getActiveIngredients()) {
                Optional<Ingredient> i = ingredientService.findByIngredientId(ingredientDTO.getId());
                if(!i.isPresent()){
                    throw new IngredientNotFoundException("Couldn't find ingredient with id: " + ingredientDTO.getId());
                }else
                    orderItem.addActiveIngredients(i.get());
            }
            save(orderItem);
            /*order.addOrderedItem(orderItem);
            orderService.save(order);*/

            return "Successfully added new ordered item to order id: " + id;
        }
        throw new NotFoundException("Couldn't find order.");
    }

    public List<OrderItemDTO> getOrderedItemsForOrderId(long id){
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        Order order = orderService.findOneWithOrderItems(id);

        if(order != null){
            List<OrderedItem> orderedItems = orderedItemRepository.findAllByOrderId(order.getId());
            for (OrderedItem orderedItem: orderedItems) {
                List<Ingredient> ingredients = ingredientService.findByOrderedItemId(orderedItem.getId());
                orderedItem.setActiveIngredients(ingredients);
                OrderItemDTO orderItemDTO = new OrderItemDTO(orderedItem);
                double price = menuItemPriceService.findCurrentPriceForMenuItemById(orderedItem.getMenuItem().getId());
                orderItemDTO.setPrice(price);
                orderItemDTOList.add(orderItemDTO);
            }
            return orderItemDTOList;
        }

        throw new NotFoundException("Couldn't find order.");
    }

    public boolean orderContainsActiveOrderedItems(long id){
        return !orderedItemRepository.findAllActiveByOrderId(id).isEmpty();
    }

    public List<OrderItemDTO> findAllByOrderIdDTO(long id){
        List<OrderItemDTO> listItems = new ArrayList<>();
        for(OrderedItem item : orderedItemRepository.findAllByOrderIdNotDeletedAndNew(id)){//findAllByOrderId(id)){//
            OrderItemDTO dto = new OrderItemDTO(item,""); //todo ovo promeni, jer izlazi neki load exc
            listItems.add(dto);
        }
        return listItems;
    }

    public List<OrderItemDTO> findAllAcceptedByOrderIdDTO(long id, String username) {
        List<OrderItemDTO> listItems = new ArrayList<>();
        User user = userService.findByUsername(username);
        List<OrderedItem> accepted = new ArrayList<>();

        if(user instanceof Bartender){ accepted = ((Bartender)user).getOrderedItems();}
        else{ accepted = ((Chef)user).getOrderedItems();}

        for(OrderedItem item : accepted){
            if(item.getOrder().getId() == id && item.getStatus()==OrderedItemStatus.IN_PROGRESS){
                OrderItemDTO dto = new OrderItemDTO(item,""); //todo ovo promeni, jer izlazi neki load exc
                listItems.add(dto);
            }
        }
        return listItems;

    }

    public List<Employee> findAllChefsAndBartendersPreparingOrderById(Long orderId){
        return orderedItemRepository.findAllChefsAndBartendersPreparingOrderById(orderId);
    }

    public List<IngredientDTO> getActiveIngredients(long id) {
        List<IngredientDTO> dtos = new ArrayList<>();
        this.ingredientService.findByOrderedItemId(id).forEach(ingredient -> dtos.add(new IngredientDTO(ingredient)));
        return dtos;
    }
}
