package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.OrderAlreadyPaidException;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private OrderedItemService orderedItemService;

    @Autowired
    private MenuItemPriceService menuItemPriceService;

    @Autowired
    private TableService tableService;

    @Autowired
    private WaiterService waiterService;

    @Autowired
    private NotificationService notificationService;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findOne(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order findOneWithOrderItems(long id) {
        return orderRepository.findOneWithOrderItems(id);
    }

    public long createOrder(OrderDTO ordDTO) {
        Order o = new Order();

        if (ordDTO.getOrderItems().isEmpty()) {
            throw new ForbiddenException("Order has to contain ordered items.");
        }
        o.setDate(LocalDate.now());
        o.setNote(ordDTO.getNote());
        o.setPaid(ordDTO.isPaid());
        o.setTime(LocalTime.now());
        o.setTotalPrice(ordDTO.getTotalPrice());
        o.setRestaurantTable(tableService.findOne(ordDTO.getTableId()));
        o.setWaiter(waiterService.findByUsername(ordDTO.getWaiterUsername()));
        save(o);
        o.setOrderNumber(Integer.parseInt(o.getId().toString()));


        for (OrderItemDTO orderItemDto : ordDTO.getOrderItems()) {
            OrderedItem orderItem = new OrderedItem();

            Optional<MenuItem> menuItem = menuItemService.findByMenuItemId(orderItemDto.getMenuItemId());
            if (menuItem.isPresent()) {
                orderItem.setDeleted(false);
                orderItem.setMenuItem(menuItem.get());
                orderItem.setPriority(orderItemDto.getPriority());
                orderItem.setStatus(OrderedItemStatus.ORDERED);
                orderItem.setQuantity(orderItemDto.getQuantity());
                orderItem.setOrder(o);
                for (IngredientDTO acIngr : orderItemDto.getActiveIngredients()) {
                    Ingredient i = ingredientService.findOne(acIngr.getId());
                    if (i == null) {
                        throw new NotFoundException("Couldn't find ingredient with id: " + acIngr.getId());
                    }
                    orderItem.addActiveIngredients(i);
                }
                o.addOrderedItem(orderItem);
                orderedItemService.save(orderItem);
            } else {
                throw new NotFoundException("Couldn't find menu item with id: " + orderItemDto.getMenuItemId());
            }
        }
        save(o);

        return o.getId();
    }


    public String updateOrder(OrderDTO ordDTO) {
        Order order = findOneWithOrderItems(ordDTO.getId());
        if (order == null) {
            throw new NotFoundException("Couldn't find order.");
        }
        if (order.isPaid()) {
            throw new OrderAlreadyPaidException("Can't change order that is already paid.");
        }
        for (OrderItemDTO item : ordDTO.getOrderItems()) {
            if (item.getId() == -1) {
                orderedItemService.addOrderItemToOrder(ordDTO.getId(), item);
            } else if (item.getStatusAsString().equals("PENDING")) {
                orderedItemService.updateOrderedItem(ordDTO.getId(), item);
            }
        }
        if (!order.getNote().equals(ordDTO.getNote())) {
            order.setNote(ordDTO.getNote());
            save(order);
            List<Employee> chefsAndBartendersPreparingOrder = orderedItemService.findAllChefsAndBartendersPreparingOrderById(ordDTO.getId());
            if (!chefsAndBartendersPreparingOrder.isEmpty()) {
                for (Employee e : chefsAndBartendersPreparingOrder) {
                    notificationService.sendNoteChangedNotification(order.getOrderNumber(), order.getRestaurantTable().getTableNum(), e);
                }
            }
        }

        return "Successfully updated order with order number: " + order.getOrderNumber();
    }

    public String setTotalPriceAndPay(long id) {
        Order order = findOneWithOrderItems(id);
        if (order != null) {
            if (order.isPaid()) {
                throw new ForbiddenException("Order with id " + id + " is already paid.");
            }
            double totalprice = 0;
            List<OrderedItem> orderedItems = orderedItemService.findAllByOrderId(id);
            for (OrderedItem oi : orderedItems) {
                totalprice += menuItemPriceService.findCurrentPriceForMenuItemById(oi.getMenuItem().getId()) * oi.getQuantity();
            }
            order.setTotalPrice(totalprice);
            order.setPaid(true);
            save(order);
            return "Successfully paid order with id: " + id;
        }

        throw new NotFoundException("Couldn't find order with id: " + id);
    }

    public String getNote(long id) {
        return this.orderRepository.findByOrderId(id).getNote();
    }

    public boolean checkIfOrderIsPaid(long id) {
        Order order = orderRepository.findByOrderId(id);
        if (order != null) {
            return order.isPaid();
        }

        throw new NotFoundException("Couldn't find order with id: " + id);
    }

    public List<Integer> getActiveOrdersForTable(int tableNum, String waiterUsername) {
        Optional<RestaurantTable> table = tableService.findByTableNumber(tableNum);
        List<Integer> retval = new ArrayList<>();
        if (table.isPresent()) {
            List<Long> orders = orderRepository.getAllActiveOrdersForTable(tableNum, waiterUsername);
            if (orders.size() != 0) {
                for (Long orderid : orders) {
                    if (orderedItemService.orderContainsActiveOrderedItems(orderid)) {
                        retval.add(Integer.parseInt(orderid.toString()));
                    } else if (!orderRepository.orderIsPaid(orderid)) {
                        retval.add(Integer.parseInt(orderid.toString()));
                    }
                }
            }
            return retval;
        }

        throw new NotFoundException("Couldn't find table with table number: " + tableNum);
    }

    public OrderDTO getOrder(int orderNum) {
        Order order = orderRepository.findByOrderNumber(orderNum);
        if (order != null) {
            List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
            List<OrderedItem> orderedItems = orderedItemService.findAllByOrderId(order.getId());
            for (OrderedItem oi : orderedItems) {
                List<Ingredient> ingredients = ingredientService.findByOrderedItemId(oi.getId());
                oi.setActiveIngredients(ingredients);
                OrderItemDTO orderItemDTO = new OrderItemDTO(oi);
                orderItemDTO.setPrice(menuItemPriceService.findCurrentPriceForMenuItemById(oi.getMenuItem().getId()));
                orderItemDTOList.add(orderItemDTO);
            }
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderItems(orderItemDTOList);
            orderDTO.setPaid(order.isPaid());
            orderDTO.setId(order.getId());
            orderDTO.setTotalPrice(order.getTotalPrice());
            orderDTO.setNote(order.getNote());
            orderDTO.setOrderItems(orderItemDTOList);
            return orderDTO;
        }
        throw new NotFoundException("Couldn't find order with order number: " + orderNum);
    }
}
