package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.ftn.restaurant.constants.DishConstants.DISH_1;
import static com.ftn.restaurant.constants.NewDishDTOConstants.NEW_DISH_DTO_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static com.ftn.restaurant.constants.OrderConstants.*;
import static com.ftn.restaurant.constants.OrderDTOConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class OrderServiceUnitTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;//mockBean

    @BeforeAll
    public void setup() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(ORDER_1);

        given(orderRepository.findAll()).willReturn(orderList);

        Order order = new Order(false, 0, LocalDate.now(), "note", LocalTime.now(), new ArrayList<>());
        given(orderRepository.save(any(Order.class))).willReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder(){
        OrderDTO orderDTO = ORDER_DTO_1;
        Order created = orderService.createOrder(orderDTO);

        verify(orderRepository, times(2)).save(any());
        Assert.assertEquals(orderDTO.getNote(), created.getNote());
    }

}
