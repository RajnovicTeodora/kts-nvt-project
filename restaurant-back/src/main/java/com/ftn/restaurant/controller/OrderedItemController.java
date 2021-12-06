package com.ftn.restaurant.controller;

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
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public String acceptOrderedItem(@AuthenticationPrincipal User user, @PathVariable long id){
        return this.orderedItemService.acceptOrderedItem(id); //kada bude autorizacija moci ce da se doda i ko ga priprema todo
    }
    @ResponseBody
    @PostMapping(path = "/finishOrderedItem/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF')")
    public String finishOrderedItem(@AuthenticationPrincipal User user, @PathVariable long id){
        return this.orderedItemService.finishOrderedItem(id);
    }

    @GetMapping(value = "/confirmPickup")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<Void> confirmPickup(@AuthenticationPrincipal User user, @RequestParam Long id){
        orderedItemService.confirmPickup(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping(value = "/setDeleted/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<Void> deleteOrderedItem(@AuthenticationPrincipal User user, @PathVariable Long id) {

        OrderedItem orderedItem = orderedItemService.findOne(id);

        if (orderedItem != null) {
            orderedItem.setDeleted(true);   //TODO: remove from order?
            orderedItemService.save(orderedItem);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
