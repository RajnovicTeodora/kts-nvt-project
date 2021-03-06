package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.*;
import com.ftn.restaurant.model.*;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.OrderedItemStatus;
import com.ftn.restaurant.repository.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ftn.restaurant.constants.DishConstants.DISH_1;
import static com.ftn.restaurant.constants.DishConstants.LIST_DISHES;
import static com.ftn.restaurant.constants.OrderDTOConstants.ORDER_ITEM_DTO_1;
import static com.ftn.restaurant.constants.OrderedItemConstants.*;
import static com.ftn.restaurant.constants.UserConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderedItemUnitTest {

    @Autowired
    private OrderedItemService orderedItemService;

    @MockBean
    private BartenderRepository bartenderRepository;

    @MockBean
    private NotificationRepository notificationRepository;

    @MockBean
    private ChefRepository chefRepository;

    @MockBean
    private OrderedItemRepository orderedItemRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemPriceService menuItemPriceService;

    @MockBean
    private UserService userService;


    @Test
    public void acceptOrderedItemTest(){
        given(orderedItemRepository.findWithId(7)).willReturn(Optional.of(ITEM_4));
        given(orderedItemRepository.findWithId(2)).willReturn(Optional.of(ITEM_2));
        given(orderedItemRepository.findWithId(3)).willReturn(Optional.of(ITEM_3));
        given(orderedItemRepository.findWithId(10000000)).willReturn(Optional.empty());
        given(orderedItemRepository.findWithId(-10)).willReturn(Optional.empty());
        given(userService.findByUsername("Pera")).willReturn(BARTENDER_1);
        given(userService.findByUsername("Sima")).willReturn(CHEF_1);

        Assert.assertEquals("You accepted order pizza",orderedItemService.acceptOrderedItem(7, "Pera"));

        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(10000000, "Pera"));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(-10, "Pera"));

        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(2, "Pera"));

        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(3, "Pera"));
    }
    @Test
    public void acceptOrderedItemChefTest(){
        given(orderedItemRepository.findWithId(7)).willReturn(Optional.of(ITEM_1));
        given(orderedItemRepository.findWithId(2)).willReturn(Optional.of(ITEM_2));
        given(orderedItemRepository.findWithId(3)).willReturn(Optional.of(ITEM_3));
        given(orderedItemRepository.findWithId(10000000)).willReturn(Optional.empty());
        given(orderedItemRepository.findWithId(-10)).willReturn(Optional.empty());
        given(userService.findByUsername("Sima")).willReturn(CHEF_1);

        Assert.assertEquals("You accepted order pizza",orderedItemService.acceptOrderedItem(7, "Sima"));

        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(10000000, "Sima"));
        Assert.assertEquals("Order doesn't exists",orderedItemService.acceptOrderedItem(-10, "Sima"));

        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(2, "Sima"));

        Assert.assertEquals("You can't accept order if it is not in status ordered.",
                orderedItemService.acceptOrderedItem(3, "Sima"));
    }
    @Test
    public void findAcceptedOrdersTest(){
        ITEM_1.setStatus(OrderedItemStatus.IN_PROGRESS);
        ITEM_1.getOrder().setId(1l);
        ((Chef)CHEF_1).setOrderedItems(new ArrayList<>(Arrays.asList(ITEM_1)));
        given(userService.findByUsername("Sima")).willReturn(CHEF_1);
        Assert.assertEquals(1,orderedItemService.findAllAcceptedByOrderIdDTO(1, "Sima").size());
    }

    @Test
    public void finishOrderedItemTest(){
        ITEM_3.setWhoPreapiring((Chef)CHEF_1);
        ITEM_1.setStatus(OrderedItemStatus.ORDERED);
        ((Chef)CHEF_1).setOrderedItems(new ArrayList<>(Arrays.asList(ITEM_3)));
        given(orderedItemRepository.findById(7)).willReturn(Optional.of(ITEM_1));
        given(orderedItemRepository.findById(2)).willReturn(Optional.of(ITEM_2));
        given(orderedItemRepository.findById(3)).willReturn(Optional.of(ITEM_3));
        given(orderedItemRepository.findById(10000000)).willReturn(Optional.empty());
        given(orderedItemRepository.findById(-10)).willReturn(Optional.empty());

        Assert.assertEquals("You finished order pizza",orderedItemService.finishOrderedItem(3));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(10000000));
        Assert.assertEquals("Order doesn't exists",orderedItemService.finishOrderedItem(-10));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(2));
        Assert.assertEquals("You can't finish order if it is not in status in progres.",
                orderedItemService.finishOrderedItem(7));
    }


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
        RestaurantTable rt = new RestaurantTable();
        rt.setTableNum(1);
        Order order = new Order();
        order.setOrderNumber(1);
        order.setRestaurantTable(rt);
        Employee chef = new Chef();
        chef.setId(1L);

        Mockito.when(orderedItemRepository.findById(1L)).thenReturn(Optional.of(orderedItem));
        Mockito.when(orderedItemRepository.findOrderByOrderedItemId(1L)).thenReturn(order);
        Mockito.when(orderedItemRepository.findEmployeePreparingOrderedItemById(1L)).thenReturn(chef);
        Mockito.when(orderedItemRepository.findMenuItemNameForOrderedItemId(1L)).thenReturn("pizza");
        Mockito.when(notificationService
                .sendOrderedItemStatusChangedNotification(1, 1, chef, OrderedItemStatus.DELIVERED,
                        "pizza")).thenReturn(true);
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

    @Test(expected = OrderedItemNotFoundException.class )
    public void updateOrderedItem_ThrowNotFoundExceptionWhenOrderNonExisting(){
        OrderItemDTO o = new OrderItemDTO();
        o.setId(1L);
        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(null);
        orderedItemService.updateOrderedItem(o);
    }

    @Test(expected = BadRequestException.class )
    public void updateOrderedItem_ThrowBadRequestExceptionWhenOrderedItemIsDeleted(){
        OrderItemDTO o = new OrderItemDTO();
        o.setId(1L);
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        List<Ingredient> activeIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("origano",false);
        activeIngredientList.add(ingredient);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1, menuItem, activeIngredientList, true);
        orderedItemList.add(orderedItem);

        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(orderedItem);
        orderedItemService.updateOrderedItem(o);
    }

    @Test(expected = ForbiddenException.class )
    public void updateOrderedItem_ThrowForbiddenExceptionWhenStatusIsNotOrdered(){
        OrderItemDTO o = new OrderItemDTO();
        o.setId(1L);
        List<OrderedItem> orderedItemList = new ArrayList<>();
        Dish menuItem = new Dish("supa","img.jpg",true,false, new ArrayList<>(), DishType.SOUP);
        menuItem.setId(1L);
        List<Ingredient> activeIngredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient("origano",false);
        activeIngredientList.add(ingredient);
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.IN_PROGRESS, 1,1, menuItem, activeIngredientList, false);
        orderedItemList.add(orderedItem);
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), orderedItemList);

        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(1L)).thenReturn(orderedItem);
        orderedItemService.updateOrderedItem(o);
    }

    @Test(expected = IngredientNotFoundException.class )
    public void updateOrderedItem_ThrowIngredientNotFoundException(){
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

        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(5L)).thenReturn(orderedItem);

        Assert.assertNotNull(orderedItemService.updateOrderedItem(ORDER_ITEM_DTO_1));
        Assert.assertEquals(2,orderedItem.getActiveIngredients().size());
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

        Mockito.when(orderedItemRepository.findOneWithActiveIngredients(5L)).thenReturn(orderedItem);
        Mockito.when(ingredientRepository.findByIngredientId(2)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.findByIngredientId(1)).thenReturn(Optional.of(ingredient1));

        Assert.assertEquals("Successfully updated ordered item with id: 5", orderedItemService.updateOrderedItem(ORDER_ITEM_DTO_1));
        Assert.assertEquals(2,orderedItem.getActiveIngredients().size());
    }


    @Test(expected = NotFoundException.class )
    public void addOrderItemToOrder_ThrowNotFoundExceptionWhenOrderNonExisting(){
        Mockito.when(orderRepository.findByOrderId(1L)).thenReturn(null);
        orderedItemService.addOrderItemToOrder(1L, new OrderItemDTO());
    }

    @Test(expected = OrderAlreadyPaidException.class )
    public void addOrderItemToOrder_ThrowOrderAlreadyPaidException(){
        Order order = new Order(true, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        Mockito.when(orderRepository.findByOrderId(1L)).thenReturn(order);
        orderedItemService.addOrderItemToOrder(1L, new OrderItemDTO());
    }

    @Test(expected = MenuItemNotFoundException.class )
    public void addOrderItemToOrder_ThrowMenuItemNotFoundException(){
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        Mockito.when(orderRepository.findByOrderId(1L)).thenReturn(order);
        Mockito.when(menuItemService.findByMenuItemId(1)).thenReturn(Optional.empty());
        orderedItemService.addOrderItemToOrder(1L, ORDER_ITEM_DTO_1);
    }

    @Test(expected = IngredientNotFoundException.class )
    public void addOrderItemToOrder_ThrowIngredientNotFoundException(){
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        Mockito.when(orderRepository.findByOrderId(1L)).thenReturn(order);
        Mockito.when(menuItemService.findByMenuItemId(1)).thenReturn(Optional.of(new Dish()));
        orderedItemService.addOrderItemToOrder(1L, ORDER_ITEM_DTO_1);
    }

    @Test
    public void addOrderItemToOrder_ReturnStringWhenAllOk(){
        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        Ingredient ingredient = new Ingredient("sladoled",true);
        Ingredient ingredient1 = new Ingredient("plazma",false);
        Mockito.when(orderRepository.findByOrderId(1L)).thenReturn(order);
        Mockito.when(menuItemService.findByMenuItemId(1)).thenReturn(Optional.of(new Dish()));
        Mockito.when(ingredientRepository.findByIngredientId(2)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.findByIngredientId(1)).thenReturn(Optional.of(ingredient1));
        Assert.assertEquals("Successfully added new ordered item to order id: 1", orderedItemService.addOrderItemToOrder(1L, ORDER_ITEM_DTO_1));

    }

    @Test(expected = NotFoundException.class )
    public void getOrderedItemsForOrderId_ThrowNotFoundExceptionWhenOrderNonExisting(){
        Mockito.when(orderRepository.findOneWithOrderItems(1)).thenReturn(null);
        orderedItemService.getOrderedItemsForOrderId(1);
    }

    @Test
    public void getOrderedItemsForOrderId_ReturnListWhenAllOk(){
        Order order = new Order();
        order.setId(1L);
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient ingredient = new Ingredient("sladoled",true);
        ingredients.add(ingredient);
        MenuItem menuItem = new Dish();
        menuItem.setId(1L);
        menuItem.setName("pizza");
        List<OrderedItem> orderedItems = new ArrayList<>();
        OrderedItem orderedItem = new OrderedItem(OrderedItemStatus.ORDERED, 1, 1, menuItem, ingredients, false);
        orderedItem.setId(1L);
        orderedItems.add(orderedItem);

        Mockito.when(orderRepository.findOneWithOrderItems(1)).thenReturn(order);
        Mockito.when(orderedItemRepository.findAllByOrderId(1L)).thenReturn(orderedItems);
        Mockito.when(ingredientRepository.findByOrderedItemId(1L)).thenReturn(ingredients);
        Mockito.when(menuItemPriceService.findCurrentPriceForMenuItemById(1L)).thenReturn(20.0);
        Assert.assertEquals(1, orderedItemService.getOrderedItemsForOrderId(1).size());
    }
}
