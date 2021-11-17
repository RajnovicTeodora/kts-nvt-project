package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.*;
import com.ftn.restaurant.service.MenuItemPriceService;
import com.ftn.restaurant.service.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/menuItem")
public class MenuItemController {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemController.class);

    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @ResponseBody
    @GetMapping(path = "/deleteMenuItem")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public MenuItemDTO deleteMenuItem(@RequestParam Long id) {
        LOG.info("Deleting menu item price...");
        return new MenuItemDTO(menuItemService.deleteMenuItem(id));
    }

    @ResponseBody
    @PostMapping(path = "/addDrink")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkDTO addDrink(@RequestBody NewDrinkDTO drinkDTO){
        LOG.info("Add new drink...");
        return new DrinkDTO(menuItemService.addDrink(drinkDTO));
    }
}