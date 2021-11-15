package com.ftn.restaurant.controller;

import com.ftn.restaurant.model.Order;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.service.OrderService;
import com.ftn.restaurant.service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderedItem")
public class OrderedItemController {

    @Autowired
    private OrderedItemService orderedItemService;

    @GetMapping(value = "/confirmPickup")
    public ResponseEntity<Void> confirmPickup(@RequestParam Long id){
        orderedItemService.confirmPickup(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping(value = "/setDeleted", consumes = "application/json")
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
