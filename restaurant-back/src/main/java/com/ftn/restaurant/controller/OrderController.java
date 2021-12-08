package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping(value = "/createOrder")
    //@PreAuthorize("hasRole('WAITER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder( @RequestBody OrderDTO orderDTO) {
        return new OrderDTO(orderService.createOrder(orderDTO));
    }

    @ResponseBody
    @PostMapping(value = "/updateOrder/{id}")
    //@PreAuthorize("hasRole('WAITER')")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO updateOrder(@PathVariable("id") long id, @RequestBody OrderDTO orderDTO) {
        return new OrderDTO(orderService.updateOrder(id, orderDTO));
    }

    @ResponseBody
    @GetMapping(value = "/payOrder/{id}")
    //@PreAuthorize("hasRole('WAITER')")
    public String payOrder(@PathVariable("id") long id) {
        return orderService.setTotalPriceAndPay(id);
    }
}
