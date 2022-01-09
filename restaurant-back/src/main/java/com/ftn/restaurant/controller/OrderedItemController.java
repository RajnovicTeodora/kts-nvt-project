package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.OrderAlreadyPaidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/orderedItem")
public class OrderedItemController {
    @Autowired
    private OrderedItemService orderedItemService;

    @Autowired
    public OrderedItemController(OrderedItemService orderedItemService){
        this.orderedItemService = orderedItemService; }

    @ResponseBody
    @PostMapping(path = "/acceptOrderedItem/{id}")
    //@PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")@AuthenticationPrincipal User user,
    public String acceptOrderedItem( @PathVariable long id){
        return this.orderedItemService.acceptOrderedItem(id); //kada bude autorizacija moci ce da se doda i ko ga priprema todo
    }
    @ResponseBody
    @PostMapping(path = "/finishOrderedItem/{id}")
    //@PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")//@AuthenticationPrincipal User user
    public String finishOrderedItem( @PathVariable long id){
        return this.orderedItemService.finishOrderedItem(id);
    }

    @ResponseBody
    @GetMapping(value = "/confirmPickup/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> confirmPickup(@PathVariable long id){
        try {
            return new ResponseEntity(orderedItemService.confirmPickup(id), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find ordered item.", HttpStatus.NOT_FOUND);
        }catch (ForbiddenException e){
            return new ResponseEntity("Can't deliver ordered item when status is not READY.", HttpStatus.FORBIDDEN);
        }catch (BadRequestException e){
            return new ResponseEntity("Can't deliver DELETED ordered item.", HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping(value = "/setDeleted/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> deleteOrderedItem( @PathVariable long id) {
        try {
            return new ResponseEntity(orderedItemService.deleteOrderedItem(id), HttpStatus.OK);
        } catch (ForbiddenException e){
            return new ResponseEntity("Can't delete ordered item with id: "+ id, HttpStatus.FORBIDDEN);
        }catch (BadRequestException e){
            return new ResponseEntity("Already deleted ordered item with id: "+ id, HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find ordered item.", HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(value = "/updateOrderedItem/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> updateOrderedItem(@PathVariable("id") long id, @RequestBody OrderItemDTO orderItemDTO) {
        try {
            return new ResponseEntity(new OrderItemDTO(orderedItemService.updateOrderedItem(id, orderItemDTO)), HttpStatus.OK);
        } catch (ForbiddenException e){
            return new ResponseEntity("Can't change ordered item in preparation.", HttpStatus.FORBIDDEN);
        } catch (OrderAlreadyPaidException e){
            return new ResponseEntity("Can't change order that is already paid.", HttpStatus.FORBIDDEN);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order.", HttpStatus.NOT_FOUND);
        }catch (BadRequestException e){
            return new ResponseEntity("Can't update deleted ordered item with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping(value = "/addOrderItemToOrder/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<OrderItemDTO> addOrderItemToOrder(@PathVariable("id") long id, @RequestBody OrderItemDTO orderItemDTO) {
        try {
            return new ResponseEntity(new OrderItemDTO(orderedItemService.addOrderItemToOrder(id, orderItemDTO)), HttpStatus.CREATED);
        }catch (OrderAlreadyPaidException e){
            return new ResponseEntity("Can't add order items to order that is already paid.", HttpStatus.FORBIDDEN);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order.", HttpStatus.NOT_FOUND);
        }

    }
}
