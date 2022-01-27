package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.ActiveOrdersPresentException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.service.TableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @ResponseBody
    @GetMapping(value = "/{tableNum}/occupyTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> occupyTable(@PathVariable("tableNum") int tableNum, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.occupyTable(waiter, tableNum), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already occupied", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{tableNum}/clearTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> clearTable(@PathVariable("tableNum") int tableNum, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.clearTable(waiter, tableNum), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already cleared", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
        catch (ActiveOrdersPresentException e){
            return new ResponseEntity<>("Can't unoccupy table when there are active orders.", HttpStatus.FORBIDDEN);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{tableNum}/claimTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> claimTable( @PathVariable("tableNum") int tableNum, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.claimTable(waiter, tableNum), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already claimed", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{tableNum}/leaveTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> leaveTable( @PathVariable("tableNum") int tableNum, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.leaveTable(waiter, tableNum), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Can't leave table while its occupied", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(path = "/addTable")
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO addTable(@RequestBody RestaurantTableDTO tableDTO){
        return new RestaurantTableDTO(tableService.addTable(tableDTO));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteTable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO deleteTable(@PathVariable("id") Long id) throws Exception{
        return new RestaurantTableDTO(tableService.deleteTable(id));
    }

    @ResponseBody
    @GetMapping(value = "/getTableByTableNumber/{tableNum}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> getTableByTableNumber( @PathVariable("tableNum") int tableNum) {
        try {
            return new ResponseEntity<>(tableService.getTableByTableNumber(tableNum), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with table number: " + tableNum, HttpStatus.NOT_FOUND);
        }
    }
}
