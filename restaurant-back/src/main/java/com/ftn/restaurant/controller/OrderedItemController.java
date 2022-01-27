package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.OrderDTO;
import com.ftn.restaurant.dto.OrderItemDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.OrderAlreadyPaidException;
import org.springframework.http.MediaType;
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

import java.util.List;

@Controller
@RequestMapping("/api/orderedItem")
public class OrderedItemController {
    @Autowired
    private OrderedItemService orderedItemService;

    @Autowired
    public OrderedItemController(OrderedItemService orderedItemService){
        this.orderedItemService = orderedItemService; }

    @ResponseBody
    @PostMapping(path = "/acceptOrderedItem/{id}/{username}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public String acceptOrderedItem( @PathVariable long id, @PathVariable String username){
        //kada bude autorizacija moci ce da se doda i ko ga priprema todo
        return this.orderedItemService.acceptOrderedItem(id, username);
    }

    @ResponseBody
    @GetMapping(path = "/itemsOfOrder/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public List<OrderItemDTO> getOrderedItemsFromTable( @PathVariable long id){
        return this.orderedItemService.findAllByOrderIdDTO(id);
    }
    @ResponseBody
    @PostMapping(path = "/finishOrderedItem/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public String finishOrderedItem( @PathVariable long id){
        return this.orderedItemService.finishOrderedItem(id);
    }
    //getAccepted
    @ResponseBody
    @GetMapping(path = "//getAccepted/{id}/{username}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public List<OrderItemDTO> getAcceptedItemsFromTable( @PathVariable long id, @PathVariable String username){
        return this.orderedItemService.findAllAcceptedByOrderIdDTO(id, username);
    }

    @ResponseBody
    @GetMapping(value = "/confirmPickup/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> confirmPickup(@PathVariable("id") long id){
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
    public ResponseEntity<?> deleteOrderedItem( @PathVariable("id") long id) {
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
    @GetMapping(value = "/getOrderedItemsForOrderId/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> getOrderedItemsForOrderId( @PathVariable("orderId") long orderId) {
        try {
            return new ResponseEntity(orderedItemService.getOrderedItemsForOrderId(orderId), HttpStatus.OK);
        }catch (NotFoundException e){
            return new ResponseEntity("Couldn't find order", HttpStatus.NOT_FOUND);
        }
    }

}
