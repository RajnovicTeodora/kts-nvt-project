package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PostMapping(value = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> createOrder( @RequestBody OrderDTO orderDTO) {
        try {
            return new ResponseEntity(orderService.createOrder(orderDTO), HttpStatus.CREATED);
        } catch (ForbiddenException e){
            return new ResponseEntity("Order has to contain ordered items.", HttpStatus.FORBIDDEN);
        } catch (NotFoundException e){
            return new ResponseEntity("Couldn't find menu item/ingredient", HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(value = "/updateOrder/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> updateOrder(@PathVariable("id") long id, @RequestBody OrderDTO orderDTO) {
        try {
            return new ResponseEntity(new OrderDTO(orderService.updateOrder(id, orderDTO)), HttpStatus.OK);
        } catch (ForbiddenException e){
            return new ResponseEntity("Can't change order that is already paid.", HttpStatus.FORBIDDEN);
        }
    }

    @ResponseBody
    @GetMapping(value = "/payOrder/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?>  payOrder(@PathVariable("id") long id) {
        try {
            return new ResponseEntity(orderService.setTotalPriceAndPay(id), HttpStatus.OK);
        } catch (ForbiddenException e){
            return new ResponseEntity("Order with id " + id + " is already paid.", HttpStatus.FORBIDDEN);
        } catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order with id: "+ id, HttpStatus.NOT_FOUND);
        }
    }
}
