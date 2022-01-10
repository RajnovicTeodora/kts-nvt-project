package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.OrderAlreadyPaidException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.IngredientRepository;
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

import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_ITEM_DTO_1;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderedItemUnitTest {

    @Autowired
    private OrderedItemService orderedItemService;

    @MockBean
    private OrderedItemRepository orderedItemRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void acceptOrderedItemTest(){
        Assert.assertEquals("You accepted order with id: 7",orderedItemService.acceptOrderedItem(7));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(10000000));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(-10));
        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(2));
        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(3));
    }

    @Test
    public void finishOrderedItemTest(){
        Assert.assertEquals("You finished order with id: 1",orderedItemService.finishOrderedItem(1));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(10000000));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(-10));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(2));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(3));
    } //todo vrv verify?


    @Test(expected = NotFoundException.class )
    public void confirmPickup_ThrowNotFoundExceptionWhenOrderedItemNonExisting(){
        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.empty());
        orderedItemService.confirmPickup(1L);
    }

    @Test(expected = BadRequestException.class )
    public void confirmPickup_ThrowBadRequestExceptionWhenOrderedItemIsDeleted(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1,1,menuItem,new ArrayList<>(), true);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        orderedItemService.confirmPickup(1L);
    }

    @Test(expected = ForbiddenException.class )
    public void confirmPickup_ThrowForbiddenExceptionWhenOrderedItemStatusIsNotReady(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.DELIVERED, 1,1,menuItem,new ArrayList<>(), false);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        orderedItemService.confirmPickup(1L);
    }

    @Test
    public void confirmPickup_ReturnStringWhenAllOk(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.READY, 1,1,menuItem,new ArrayList<>(), false);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        Assert.assertEquals("Successfully delivered ordered item with id: 1", orderedItemService.confirmPickup(1L));
        Assert.assertEquals(OrderedItemStatus.DELIVERED, orderedItem.getStatus());
    }

    @Test(expected = NotFoundException.class )
    public void deleteOrderedItem_ThrowNotFoundExceptionWhenOrderedItemNonExisting(){
        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.empty());
        orderedItemService.deleteOrderedItem(1L);
    }

    @Test(expected = BadRequestException.class )
    public void deleteOrderedItem_ThrowBadRequestExceptionWhenOrderedItemIsDeleted(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1,1,menuItem,new ArrayList<>(), true);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        orderedItemService.deleteOrderedItem(1L);
    }

    @Test(expected = ForbiddenException.class )
    public void deleteOrderedItem_ThrowForbiddenExceptionWhenOrderedItemStatusIsNotOrdered(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1,menuItem,new ArrayList<>(), false);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        orderedItemService.deleteOrderedItem(1L);
    }

    @Test
    public void deleteOrderedItem_ReturnStringWhenAllOk(){
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1,1,menuItem,new ArrayList<>(), false);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        Assert.assertEquals("Successfully deleted ordered item with id: 1", orderedItemService.deleteOrderedItem(1L));
        Assert.assertTrue(orderedItem.isDeleted());
    }

    @Test(expected = NotFoundException.class )
    public void updateOrderedItem_ThrowNotFoundExceptionWhenOrderNonExisting(){
        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(null);
        orderedItemService.updateOrderedItem(1L, new OrderItemDTO());
    }

    @Test(expected = OrderAlreadyPaidException.class )
    public void updateOrderedItem_ThrowOrderAlreadyPaidException(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1,menuItem,new ArrayList<>(), false);
        orderedItemList.add(orderedItem);
        Order order = new Order(true, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(order);
        orderedItemService.updateOrderedItem(1L, new OrderItemDTO());
    }

    @Test(expected = BadRequestException.class )
    public void updateOrderedItem_ThrowBadRequestExceptionWhenOrderedItemIsDeleted(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        List<Ingredient> activeIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("origano",false);
        activeIngredientList.add(ingredient);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1, menuItem, activeIngredientList, true);
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(order);
        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(orderedItem);
        orderedItemService.updateOrderedItem(1L, new OrderItemDTO());
    }

    @Test(expected = ForbiddenException.class )
    public void updateOrderedItem_ThrowForbiddenExceptionWhenStatusIsNotOrdered(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        List<Ingredient> activeIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("origano",false);
        activeIngredientList.add(ingredient);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1, menuItem, activeIngredientList, false);
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(order);
        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(orderedItem);
        orderedItemService.updateOrderedItem(1L, new OrderItemDTO());
    }

    @Test
    public void updateOrderedItem_ReturnOrderedItemWhenAllOk(){
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        List<Ingredient> activeIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("sladoled",true);
        Ingredient ingredient1 = new Ingredient("plazma",false);
        activeIngredientList.add(ingredient);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1,1, menuItem, activeIngredientList, false);
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(order);
        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(orderedItem);
        Mockito.when(ingredientRepository.findByIngredientNameAndIsAlergen("sladoled",true)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.findByIngredientNameAndIsAlergen("plazma",false)).thenReturn(Optional.of(ingredient1));

        Assert.assertNotNull(orderedItemService.updateOrderedItem(1L, ORDER_ITEM_DTO_1));
        Assert.assertEquals(2,orderedItem.getActiveIngredients().size());
    }

    @Test(expected = NotFoundException.class )
    public void addOrderItemToOrder_ThrowNotFoundExceptionWhenOrderNonExisting(){
        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(null);
        orderedItemService.addOrderItemToOrder(1L, new OrderItemDTO());
    }

    @Test(expected = OrderAlreadyPaidException.class )
    public void addOrderItemToOrder_ThrowOrderAlreadyPaidException(){
        Order order = new Order(true, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        Mockito.when(orderRepository.findOneWithOrderItems(1L)).thenReturn(order);
        orderedItemService.addOrderItemToOrder(1L, new OrderItemDTO());
    }

}
