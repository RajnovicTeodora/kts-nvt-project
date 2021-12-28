package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/table")
public class TableController {

    @Autowired
    private TableService tableService;

    @ResponseBody
    @GetMapping(value = "/{id}/occupyTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> occupyTable(@PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.occupyTable(waiter, id), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already occupied", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{id}/clearTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> clearTable(@PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.clearTable(waiter, id), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already cleared", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{id}/claimTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> claimTable( @PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.claimTable(waiter, id), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Table is already claimed", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping(value = "/{id}/leaveTable/{waiter}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> leaveTable( @PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        try {
            return new ResponseEntity<>(tableService.leaveTable(waiter, id), HttpStatus.OK);
        }
        catch (ForbiddenException e){
            return new ResponseEntity<>("Can't leave table while its occupied", HttpStatus.FORBIDDEN);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>("Couldn't find table with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping(path = "/addTable")
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO addTable(@AuthenticationPrincipal User user, @RequestBody RestaurantTableDTO tableDTO){
        return new RestaurantTableDTO(tableService.addTable(tableDTO));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteTable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO deleteTable(@AuthenticationPrincipal User user, @PathVariable(value = "id") Long id){
        return new RestaurantTableDTO(tableService.deleteTable(id));
    }

}
