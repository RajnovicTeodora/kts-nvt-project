package com.ftn.restaurant.controller;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.service.OrderService;
import org.springframework.http.ResponseEntity;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.service.DrinkService;
import com.ftn.restaurant.service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/orderedItem")
public class OrderedItemController {

    private OrderedItemService orderedItemService;

    @Autowired
    public OrderedItemController(OrderedItemService orderedItemService){
        this.orderedItemService = orderedItemService; }

    @ResponseBody
    @PostMapping(path = "/acceptOrderedItem/{id}")
    public String acceptOrderedItem(@PathVariable long id){
        return this.orderedItemService.acceptOrderedItem(id); //kada bude autorizacija moci ce da se doda i ko ga priprema todo
    }
    @ResponseBody
    @PostMapping(path = "/finishOrderedItem/{id}")
    public String finishOrderedItem(@PathVariable long id){
        return this.orderedItemService.finishOrderedItem(id);
    }

    @GetMapping(value = "/confirmPickup")
    public ResponseEntity<Void> confirmPickup(@RequestParam Long id){
        orderedItemService.confirmPickup(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping(value = "/setDeleted/{id}", consumes = "application/json")
    public ResponseEntity<Void> deleteOrderedItem(@PathVariable Long id) {

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
