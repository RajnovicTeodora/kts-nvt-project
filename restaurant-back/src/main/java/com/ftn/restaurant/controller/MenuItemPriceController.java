package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.MenuItemPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/menuItemPrice")
public class MenuItemPriceController {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemPriceController.class);

    private final MenuItemPriceService menuItemPriceService;

    @Autowired
    public MenuItemPriceController(MenuItemPriceService menuItemPriceService) {
        this.menuItemPriceService = menuItemPriceService;
    }

    @ResponseBody
    @PostMapping(path = "/updatePrice")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    public MenuItemPriceDTO changeMenuItemPrice(@AuthenticationPrincipal User user, @RequestBody UpdateMenuItemPriceDTO updateMenuItemPriceDTO) {
        LOG.info("Updating menu item price...");
        return new MenuItemPriceDTO(menuItemPriceService.updateMenuItemPrice(updateMenuItemPriceDTO));
    }

}
