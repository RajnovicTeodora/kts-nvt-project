package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.*;
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

    @Autowired
    private TableService tableService;

    @Autowired
    private WaiterService waiterService;
    @Autowired
    private UserService userService;

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

        if(ordDTO.getOrderItems().isEmpty()){
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
            if(menuItem.isPresent()) {
                orderItem.setDeleted(false);
                orderItem.setMenuItem(menuItem.get());
                orderItem.setPriority(orderItemDto.getPriority());
                orderItem.setStatus(OrderedItemStatus.ORDERED);
                orderItem.setQuantity(orderItemDto.getQuantity());
                orderItem.setOrder(o);
                for (IngredientDTO acIngr : orderItemDto.getActiveIngredients()) {
                    Ingredient i = ingredientService.findOne(acIngr.getId());
                    if(i == null){
                        throw new NotFoundException("Couldn't find ingredient with id: " + acIngr.getId());
                    }
                    orderItem.addActiveIngredients(i);
                }
                o.addOrderedItem(orderItem);
                orderedItemService.save(orderItem);
            }else{
                throw new NotFoundException("Couldn't find menu item with id: " + orderItemDto.getMenuItemId());
            }
        }
        save(o);

        return o.getId();
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
            List<OrderedItem> orderedItems = orderedItemService.findAllByOrderId(id);
            for (OrderedItem oi : orderedItems) {
                totalprice += menuItemPriceService.findCurrentPriceForMenuItemById(oi.getMenuItem().getId()) *oi.getQuantity();
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

    public boolean checkIfOrderIsPaid(long id){
        Order order = findOneWithOrderItems(id);
        if(order != null) {
            return order.isPaid();
        }

        throw new NotFoundException("Couldn't find order with id: "+ id);
    }

    public List<Integer> getActiveOrdersForTable(int tableNum, String waiterUsername){
        Optional<RestaurantTable> table = tableService.findByTableNumber(tableNum);
        List<Integer> retval = new ArrayList<>();
        if(table.isPresent()){
            List<Long> orders =  orderRepository.getAllActiveOrdersForTable(tableNum, waiterUsername);
            if(orders.size() != 0){
                for (Long orderid: orders) {
                    if(orderedItemService.orderContainsActiveOrderedItems(orderid)){
                        retval.add(Integer.parseInt(orderid.toString()));
                    }
                }
            }
            return retval;
        }

        throw new NotFoundException("Couldn't find table with table number: "+ tableNum);
    }

    public List<OrderDTO> getNewOrders() {
        List<Order> orders = this.orderRepository.findAll();
        List<OrderDTO> dtos = new ArrayList<>();
        for( Order ord : orders){
            for(OrderedItem item : this.orderedItemService.findAllByOrderId(ord.getId()) ){
                if(item.getStatus().name().equals("ORDERED") && !item.isDeleted()){
                    dtos.add(new OrderDTO(ord, "without items"));
                    break;
                }
            }
        }
        return dtos;
    }

    public List<OrderDTO> getAcceptedOrders(String username) {
        User user = this.userService.findByUsername(username);
        List<OrderDTO> orders = new ArrayList<>();
        if(user.getRole().getName().equals("CHEF") || user.getRole().getName().equals("HEAD_CHEF")){
            for(OrderedItem item:((Chef)user).getOrderedItems()){
                if(item.getStatus().name().equals("IN_PROGRESS")){
                    if(!checkIsInList(orders, item.getOrder().getId())){
                        orders.add(new OrderDTO(item.getOrder(), ""));
                    }
                }
            }
        }else{
            for(OrderedItem item:((Bartender)user).getOrderedItems()){
                if(item.getStatus().name().equals("IN_PROGRESS")){
                    if(!checkIsInList(orders, item.getOrder().getId())){
                        orders.add(new OrderDTO(item.getOrder(), ""));
                    }
                }
            }
        }
        return orders;
    }

    private boolean checkIsInList(List<OrderDTO> orders, Long id) {
        for(OrderDTO dto : orders){
            if(dto.getId()==id){
                return true;
            }
        }
        return false;
    }
}
