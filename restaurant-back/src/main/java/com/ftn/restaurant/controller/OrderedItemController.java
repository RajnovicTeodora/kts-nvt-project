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
    @PostMapping(value = "/confirmPickup/{id}")
    //@PreAuthorize("hasRole('WAITER')")
    public String confirmPickup(@PathVariable long id){ return orderedItemService.confirmPickup(id);   }

    @ResponseBody
    @PostMapping(value = "/setDeleted/{id}")
    //@PreAuthorize("hasRole('WAITER')")
    public String deleteOrderedItem( @PathVariable long id) { return orderedItemService.deleteOrderedItem(id);  }
}
