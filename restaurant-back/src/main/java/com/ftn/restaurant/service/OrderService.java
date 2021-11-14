package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

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

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findOne(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order findOneWithOrderItems(Long id) {
        return orderRepository.findOneWithOrderItems(id);
    }

    public Order createOrder(OrderDTO ordDTO) {
        Order o = new Order();
        o.setDate(LocalDate.parse(ordDTO.getDate()));
        o.setDeleted(false);
        o.setNote(ordDTO.getNote());
        o.setPaid(ordDTO.isPaid());
        o.setTime(ordDTO.getTime());
        o.setTotalPrice(ordDTO.getTotalPrice());
        save(o);

        createAndAddOrderItems(ordDTO, o);

        return o;
    }

    public void createAndAddOrderItems(OrderDTO orderDTO, Order o){
        for (OrderItemDTO orderItemDto : orderDTO.getOrderItems()) {
            OrderedItem orderItem = new OrderedItem();

            MenuItem menuItem = menuItemService.findOne(orderItemDto.getMenuItem().getId());
            orderItem.setMenuItem(menuItem);
            orderItem.setPriority(orderItemDto.getPriority());
            orderItem.setStatus(orderItemDto.getStatus());
            for (IngredientDTO acIngr: orderItemDto.getActiveIngredients()) {
                Ingredient i = ingredientService.findOne(acIngr.getId());
                orderItem.addActiveIngredients(i);
            }
            orderedItemService.save(orderItem);
            o.addOrderedItem(orderItem);
        }
    }

    public Order updateOrder(OrderDTO ordDTO){
        Order order = findOne(ordDTO.getId());

        order.setNote(ordDTO.getNote());
        save(order);

        for (OrderItemDTO orderItemDTO : ordDTO.getOrderItems()) {
            OrderedItem orderItem = orderedItemService.findOne(orderItemDTO.getId());
            if(orderItemDTO.getStatus() == OrderedItemStatus.ORDERED) {
                orderItem.setActiveIngredients(new ArrayList<>());
                for(IngredientDTO ingredientDTO : orderItemDTO.getActiveIngredients()){
                    Ingredient i = ingredientService.findOne(ingredientDTO.getId());
                    orderItem.addActiveIngredients(i);
                }
                orderItem.setPriority(orderItemDTO.getPriority());
                orderedItemService.save(orderItem);
            }
        }
        order = findOneWithOrderItems(ordDTO.getId());
        return order;
    }

}
