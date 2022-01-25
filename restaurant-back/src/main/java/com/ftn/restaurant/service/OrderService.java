package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order findOne(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order findOneWithOrderItems(long id) {
        return orderRepository.findOneWithOrderItems(id);
    }

    public Order createOrder(OrderDTO ordDTO) {
        Order o = new Order();

        if(ordDTO.getOrderItems().isEmpty()){
            throw new ForbiddenException("Order has to contain ordered items.");
        }
        o.setDate(LocalDate.parse(ordDTO.getDate()));
        o.setNote(ordDTO.getNote());
        o.setPaid(ordDTO.isPaid());
        o.setTime(LocalTime.now());
        o.setTotalPrice(ordDTO.getTotalPrice());
        save(o);

        for (OrderItemDTO orderItemDto : ordDTO.getOrderItems()) {
            OrderedItem orderItem = new OrderedItem();

            Optional<MenuItem> menuItem = menuItemService.findByMenuItemNameAndImage(orderItemDto.getMenuItem().getName(), orderItemDto.getMenuItem().getImage());
            if(menuItem.isPresent()) {
                orderItem.setMenuItem(menuItem.get());
                orderItem.setPriority(orderItemDto.getPriority());
                orderItem.setStatus(OrderedItemStatus.ORDERED);
                for (IngredientDTO acIngr : orderItemDto.getActiveIngredients()) {
                    Optional<Ingredient> i = ingredientService.findByIngredientNameAndIsAlergen(acIngr.getName(), acIngr.isAlergen());
                    orderItem.addActiveIngredients(i.get());
                }
                o.addOrderedItem(orderItem);
                orderedItemService.save(orderItem);
                save(o);
            }
        }

        return o;
    }


    public Order updateOrder(long id, OrderDTO ordDTO){
        Order order = findOneWithOrderItems(id);

        if(order.isPaid()){
            throw new ForbiddenException("Can't change order that is already paid.");
        }

        order.setNote(ordDTO.getNote());
        save(order);

        return order;
    }

    public String setTotalPriceAndPay(long id){
        Order order = findOneWithOrderItems(id);
        if(order != null) {
            if(order.isPaid()){
                throw new ForbiddenException("Order with id " + id + " is already paid.");
            }
            double totalprice = 0;
            for (OrderedItem oi : order.getOrderedItems()) {
                totalprice += menuItemPriceService.findCurrentPriceForMenuItemById(oi.getMenuItem().getId());
            }
            order.setTotalPrice(totalprice);
            order.setPaid(true);
            save(order);
            return "Successfully paid order with id: " + id;
        }

        throw new NotFoundException("Couldn't find order with id: "+ id);
    }
    public String getNote(long id) {
        return this.orderRepository.findByOrderId(id).getNote();
    }

}
