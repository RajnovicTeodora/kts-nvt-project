package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/menu")
public class MenuController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ResponseBody
    @PutMapping(path = "/toggleIsActive")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemPriceDTO toggleIsMenuItemActive(@RequestParam(name = "id") Long id) {
        LOG.info("Updating active menu items...");

        return (new MenuItemPriceDTO(menuService.toggleIsMenuItemActive(id)));

    }

    @ResponseBody
    @GetMapping(path = "/searchMenuItems/{group}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemDTO> searchMenuItems(@PathVariable(value = "group") String group, @PathVariable(value = "name") String name){
        LOG.info("searching menu items...");
        LOG.info("group: " + group);
        LOG.info("name: " + name);
        return menuService.searchMenuItems(group, name);

    }

    @ResponseBody
    @GetMapping(path = "/getAll")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemPriceDTO> getActiveMenuItems(@RequestParam(name = "searchName", required = false, defaultValue = "") String searchName) {
        LOG.info("Getting active menu items...");

        return menuService.getActiveMenuItem(searchName);
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteMenuItem/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemDTO deleteMenuItem(@PathVariable Long id) {
        LOG.info("Deleting menu item price...");
        return new MenuItemDTO(menuService.deleteMenuItem(id));
    }
}
