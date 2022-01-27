package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.*;
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
        } catch (IngredientNotFoundException e){
            return new ResponseEntity("Couldn't find ingredient.", HttpStatus.NOT_FOUND);
        } catch (MenuItemNotFoundException e){
            return new ResponseEntity("Couldn't find menu item.", HttpStatus.NOT_FOUND);
        } catch (RestaurantTableNotFoundException e){
            return new ResponseEntity("Couldn't find restaurant table.", HttpStatus.NOT_FOUND);
        } catch (EmployeeNotFoundException e){
            return new ResponseEntity("Couldn't find waiter.", HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(value = "/updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> updateOrder( @RequestBody OrderDTO orderDTO) {
        try {
            return new ResponseEntity(orderService.updateOrder(orderDTO), HttpStatus.OK);
        }catch (ForbiddenException e){
            return new ResponseEntity("Can't change ordered item in preparation.", HttpStatus.FORBIDDEN);
        } catch (OrderAlreadyPaidException e){
            return new ResponseEntity("Can't change order that is already paid.", HttpStatus.FORBIDDEN);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order.", HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity("Can't update deleted ordered item.", HttpStatus.BAD_REQUEST);
        }catch (IngredientNotFoundException e){
            return new ResponseEntity("Couldn't find ingredient.", HttpStatus.NOT_FOUND);
        }catch (MenuItemNotFoundException e){
            return new ResponseEntity("Couldn't find menu item.", HttpStatus.NOT_FOUND);
        }catch (OrderedItemNotFoundException e){
            return new ResponseEntity("Couldn't find ordered item.", HttpStatus.NOT_FOUND);
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

    @ResponseBody
    @GetMapping(value = "/checkIfOrderIsPaid/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?>  checkIfOrderIsPaid(@PathVariable("id") long id) {
        try {
            return new ResponseEntity(orderService.checkIfOrderIsPaid(id), HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order with id: "+ id, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/getActiveOrdersForTable/{tableNum}/{waiterUsername}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?>  getActiveOrdersForTable(@PathVariable("tableNum") int tableNum, @PathVariable("waiterUsername") String waiterUsername) {
        try {
            return new ResponseEntity(orderService.getActiveOrdersForTable(tableNum, waiterUsername), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/getNote/{id}")
    @PreAuthorize("hasRole('BARTENDER')")
    public String getNote( @PathVariable long id){
        return this.orderService.getNote(id);

    }

    @ResponseBody
    @GetMapping(value = "/getOrder/{orderNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?>  getOrder(@PathVariable("orderNum") int orderNum) {
        try {
            return new ResponseEntity(orderService.getOrder(orderNum), HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order with order number: "+ orderNum, HttpStatus.NOT_FOUND);
        }
    }
}
