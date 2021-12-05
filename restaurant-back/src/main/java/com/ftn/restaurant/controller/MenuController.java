package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PutMapping(path = "/activateItems")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemPriceDTO> setActiveMenuItems(@AuthenticationPrincipal User user, @RequestBody SelectedMenuItemsDTO selectedMenuItemsDTO) {
        LOG.info("Updating active menu items...");
        List<MenuItemPriceDTO> menuItemPriceDTOS = new ArrayList<>();

        menuService.defineActiveMenuItem(selectedMenuItemsDTO).forEach(item ->
                menuItemPriceDTOS.add(new MenuItemPriceDTO(item)));
        return menuItemPriceDTOS;
    }

    @ResponseBody
    @GetMapping(path = "/searchMenuItems/{group}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemPriceDTO> searchMenuItems(@PathVariable(value = "group") String group, @PathVariable(value = "name") String name){
        LOG.info("searching menu items...");
        LOG.info("group: " + group);
        LOG.info("name: "+name);
        return menuService.searchMenuItems(group, name);
        
    }
}
