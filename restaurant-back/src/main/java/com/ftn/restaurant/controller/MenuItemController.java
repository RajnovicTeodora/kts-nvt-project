package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.*;
import com.ftn.restaurant.service.DrinkService;
import com.ftn.restaurant.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/menuItem")
public class MenuItemController {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemController.class);

    private final MenuItemService menuItemService;
    private final DrinkService drinkService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService, DrinkService drinkService) {
        this.menuItemService = menuItemService;
        this.drinkService = drinkService;
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteMenuItem/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemDTO deleteMenuItem(@PathVariable Long id) {
        LOG.info("Deleting menu item price...");
        return new MenuItemDTO(menuItemService.deleteMenuItem(id));
    }

    @ResponseBody
    @GetMapping(path = "/approveMenuItem/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemDTO approveMenuItem(@PathVariable Long id) {
        LOG.info("Approving menu item price...");
        return new MenuItemDTO(menuItemService.approveMenuItem(id));
    }

    @ResponseBody
    @PostMapping(path = "/addDrink")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkDTO addDrink(@RequestBody NewDrinkDTO drinkDTO) {
        LOG.info("Add new drink...");
        return new DrinkDTO(drinkService.addDrinkByManager(drinkDTO));
    }

    @ResponseBody
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MenuItemDTO> getAll(@RequestParam(name = "searchName", required = false, defaultValue = "") String searchName) {
        LOG.info("Get all items..." + searchName);
        return menuItemService.getAll(searchName);
    }

    @ResponseBody
    @GetMapping(path = "/getById/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemDTO getItem(@PathVariable long id) {
        LOG.info("Get item with id " + id);
        return this.menuItemService.getItem(id);
    }

    @ResponseBody
    @GetMapping(value = "/getWithIngredientsById/{id}")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<?> getWithIngredientsById(@PathVariable("id") long id){
        try {
            return new ResponseEntity<>(menuItemService.getWithIngredientsById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("An error has occurred!", HttpStatus.FORBIDDEN);
        }
    }
}
