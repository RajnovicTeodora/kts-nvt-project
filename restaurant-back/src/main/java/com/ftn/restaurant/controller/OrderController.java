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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "/createOrder", consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<OrderDTO> saveOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(new OrderDTO(order), HttpStatus.CREATED);
    }

    @PutMapping(value = "/updateOrder", consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<OrderDTO> updateOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO orderDTO) {

        Order order = orderService.findOne(orderDTO.getId());
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<>(new OrderDTO(orderService.updateOrder(orderDTO)), HttpStatus.OK);

    }

    @PutMapping(value = "/pay", consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<Void> payOrder(@AuthenticationPrincipal User user, @RequestBody OrderDTO orderDTO) {

        Order order = orderService.findOne(orderDTO.getId());
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
            order.setPaid(true);
            orderService.save(order);
            return new ResponseEntity<>( HttpStatus.OK);

    }
}
