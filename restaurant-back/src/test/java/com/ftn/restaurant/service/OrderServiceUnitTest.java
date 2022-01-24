package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_DTO_1;
import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_DTO_2;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderServiceUnitTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private MenuItemPriceRepository menuItemPriceRepository;
/*
    @Test(expected = ForbiddenException.class )
    public void createOrder_ThrowForbiddenExceptionWhenOrderedItemsListIsEmpty(){
        orderService.createOrder(ORDER_DTO_2);
    }

    @Test
    public void createOrder_ReturnsOrderWhenAllOk(){
        Order created = orderService.createOrder(ORDER_DTO_1);

        Assert.assertNotNull(created);
        Assert.assertEquals(ORDER_DTO_1.getNote(), created.getNote());
    }

    @Test(expected = ForbiddenException.class )
    public void updateOrder_ThrowForbiddenExceptionWhenOrderIsPaid(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(true, 2000, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(order);
        orderService.updateOrder(1L, ORDER_DTO_1);
    }

    @Test
    public void updateOrder_ReturnsOrderWhenAllOk(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 2000, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(order);
        Assert.assertNotNull(orderService.updateOrder(1L, ORDER_DTO_1));
    }

    @Test(expected = ForbiddenException.class )
    public void setTotalPriceAndPay_ThrowForbiddenExceptionWhenOrderIsPaid(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(true, 2000, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(order);
        orderService.setTotalPriceAndPay(1L);
    }

    @Test(expected = NotFoundException.class )
    public void setTotalPriceAndPay_ThrowNotFoundExceptionWhenOrderIsNonExisting(){
        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(null);
        orderService.setTotalPriceAndPay(1L);
    }

    @Test
    public void setTotalPriceAndPay_ReturnsStringWhenAllOk(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1,1,menuItem,new ArrayList<>(), false);
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(order);
        Mockito.when(menuItemPriceRepository.findCurrentPriceForMenuItemById(1L)).thenReturn(700.0);
        Assert.assertEquals("Successfully paid order with id: 1", orderService.setTotalPriceAndPay(1L));
        Assert.assertTrue(order.isPaid());
    }*/
}
