package com.ftn.restaurant.service;

import com.ftn.restaurant.exception.*;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.OrderRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
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
import java.util.Optional;

import static com.ftn.restaurant.constants.OrderDTOConstants.*;

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

    @MockBean
    private TableService tableService;

    @MockBean
    private WaiterService waiterService;

    @MockBean
    private MenuItemService menuItemService;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private OrderedItemRepository orderedItemRepository;

    @Test(expected = ForbiddenException.class )
    public void createOrder_ThrowForbiddenExceptionWhenOrderedItemsListIsEmpty(){
        orderService.createOrder(ORDER_DTO_2);
    }

    @Test(expected = RestaurantTableNotFoundException.class )
    public void createOrder_ThrowRestaurantTableNotFoundException(){
        Mockito.when(tableService.findOne(2L)).thenReturn(null);
        orderService.createOrder(ORDER_DTO_3);
    }

    @Test(expected = EmployeeNotFoundException.class )
    public void createOrder_ThrowEmployeeNotFoundException(){
        Mockito.when(tableService.findOne(2L)).thenReturn(new RestaurantTable());
        Mockito.when(waiterService.findByUsername("waiter")).thenReturn(null);
        orderService.createOrder(ORDER_DTO_3);
    }

    @Test(expected = MenuItemNotFoundException.class )
    public void createOrder_ThrowMenuItemNotFoundException(){
        Mockito.when(tableService.findOne(2L)).thenReturn(new RestaurantTable());
        Mockito.when(waiterService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(menuItemService.findByMenuItemId(2L)).thenReturn(Optional.empty());
        orderService.createOrder(ORDER_DTO_4);
    }

    @Test(expected = IngredientNotFoundException.class )
    public void createOrder_ThrowIngredientNotFoundException(){
        Mockito.when(tableService.findOne(2L)).thenReturn(new RestaurantTable());
        Mockito.when(waiterService.findByUsername("waiter")).thenReturn(new Waiter());
        Mockito.when(menuItemService.findByMenuItemId(1L)).thenReturn(Optional.of(new Dish()));
        Mockito.when(ingredientService.findByIngredientId(10000L)).thenReturn(Optional.empty());
        orderService.createOrder(ORDER_DTO_4);
    }

    @Test
    public void createOrder_ReturnIdWhenAllOk(){
        MenuItem item = new Dish();
        item.setId(1L);
        Ingredient i = new Ingredient();
        i.setId(1L);
        RestaurantTable rt = new RestaurantTable();
        rt.setId(2L);
        Waiter waiter = new Waiter();
        waiter.setId(1L);
        waiter.setUsername("waiter");
        Mockito.when(tableService.findOne(2L)).thenReturn(rt);
        Mockito.when(waiterService.findByUsername("waiter")).thenReturn(waiter);
        Mockito.when(menuItemService.findByMenuItemId(1L)).thenReturn(Optional.of(item));
        Mockito.when(ingredientService.findByIngredientId(10000L)).thenReturn(Optional.of(i));
        orderService.createOrder(ORDER_DTO_4);
    }

    @Test(expected = NotFoundException.class )
    public void updateOrder_ThrowNotFoundExceptionWhenOrderNotFound(){
        Mockito.when(orderRepository.findOneWithOrderItems(3L)).thenReturn(null);
        orderService.updateOrder(ORDER_DTO_3);
    }

    @Test(expected = OrderAlreadyPaidException.class )
    public void updateOrder_ThrowOrderAlreadyPaidException(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(true, 2000, LocalDate.now(), "note", LocalTime.now(), orderedItemList);
        Mockito.when(orderRepository.findOneWithOrderItems(3L)).thenReturn(order);
        orderService.updateOrder(ORDER_DTO_3);
    }

    @Test
    public void updateOrder_ReturnsStringWhenNoChangesMade(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 2000, LocalDate.now(), "Some note", LocalTime.now(), orderedItemList, 3);

        Mockito.when(orderRepository.findOneWithOrderItems(3L)).thenReturn(order);
        Assert.assertEquals("Successfully updated order with order number: 3", orderService.updateOrder(ORDER_DTO_11));
    }

    @Test
    public void updateOrder_ReturnsStringWhenNoteChanged(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem();
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 2000, LocalDate.now(), "note", LocalTime.now(), orderedItemList, 3);

        Mockito.when(orderRepository.findOneWithOrderItems(3L)).thenReturn(order);
        Mockito.when(orderedItemRepository.findAllChefsAndBartendersPreparingOrderById(3L)).thenReturn(new ArrayList<>());
        Assert.assertEquals("Successfully updated order with order number: 3", orderService.updateOrder(ORDER_DTO_11));
        Assert.assertEquals("Some note", order.getNote());
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
    }
}
