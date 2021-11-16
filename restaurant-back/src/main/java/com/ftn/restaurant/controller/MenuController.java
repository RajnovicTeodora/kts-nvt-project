package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

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
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemPriceDTO> setActiveMenuItems(@RequestBody SelectedMenuItemsDTO selectedMenuItemsDTO) {
        LOG.info("Updating active menu items...");
        List<MenuItemPriceDTO> menuItemPriceDTOS = new ArrayList<>();

        menuService.defineActiveMenuItem(selectedMenuItemsDTO).forEach(item ->
                menuItemPriceDTOS.add(new MenuItemPriceDTO(item)));
        return menuItemPriceDTOS;
    }

    @ResponseBody
    @GetMapping(path = "/searchMenuItems/{group}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuItemPriceDTO> searchMenuItems(@PathParam("group") String group, @PathParam("name") String name){
        LOG.info("searching menu items...");
        return menuService.searchMenuItems(group, name);
        
    }
}
