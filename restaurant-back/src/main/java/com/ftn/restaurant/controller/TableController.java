package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.service.TableService;
import com.ftn.restaurant.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private WaiterService waiterService;

    @PutMapping(value = "/occupyTable", consumes = "application/json")
    public ResponseEntity<Void> occupyTable(@RequestParam String waiterUsername, @RequestParam Long tableId) {
        RestaurantTable rt = tableService.findOneWithWaiter(tableId, waiterUsername);
        if (rt != null) {
            tableService.occupyTable(tableId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/clearTable", consumes = "application/json")
    public ResponseEntity<Void> clearTable(@RequestParam String waiterUsername, @RequestParam Long tableId) {
        RestaurantTable rt = tableService.findOneWithWaiter(tableId, waiterUsername);
        if (rt != null) {
            tableService.clearTable(tableId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/claimTable", consumes = "application/json")
    public ResponseEntity<Void> claimTable(@RequestParam String waiterUsername, @RequestParam Long tableId) {
        RestaurantTable rt = tableService.findOneWithoutWaiter(tableId);
        Waiter waiter = waiterService.findByUsername(waiterUsername);
        if (rt != null && waiter!=null && rt.getWaiter()== null) {
            tableService.claimTable(tableId, waiterUsername);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/leaveTable", consumes = "application/json")
    public ResponseEntity<Void> leaveTable(@RequestParam String waiterUsername, @RequestParam Long tableId) {
        RestaurantTable rt = tableService.findOneWithWaiter(tableId, waiterUsername);
        if (rt != null ) {
            tableService.leaveTable(tableId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(path = "/addTable")
    public RestaurantTableDTO addTable(@RequestBody RestaurantTableDTO tableDTO){
        return new RestaurantTableDTO(tableService.addTable(tableDTO));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteTable/{id}")
    public RestaurantTableDTO deleteTable(@PathVariable(value = "id") Long id){
        return new RestaurantTableDTO(tableService.deleteTable(id));
    }

}
