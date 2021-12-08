package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.RestaurantTableDTO;
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
    //@PreAuthorize("hasRole('WAITER')")
    public String occupyTable(@PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        return tableService.occupyTable(waiter, id);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/clearTable/{waiter}")
    //@PreAuthorize("hasRole('WAITER')")
    public String clearTable(@PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        return tableService.clearTable(waiter,id);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/claimTable/{waiter}")
    //@PreAuthorize("hasRole('WAITER')")
    public String claimTable( @PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        return tableService.claimTable(waiter,id);
    }

    @ResponseBody
    @GetMapping(value = "/{id}/leaveTable/{waiter}")
    //@PreAuthorize("hasRole('WAITER')")
    public String leaveTable( @PathVariable("id") long id, @PathVariable("waiter") String waiter) {
        return tableService.leaveTable(waiter, id);
    }

    @ResponseBody
    @PostMapping(path = "/addTable")
    //@PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO addTable(@RequestBody RestaurantTableDTO tableDTO){
        return new RestaurantTableDTO(tableService.addTable(tableDTO));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteTable/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RestaurantTableDTO deleteTable(@PathVariable(value = "id") Long id){
        return new RestaurantTableDTO(tableService.deleteTable(id));
    }

}
