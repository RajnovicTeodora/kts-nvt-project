package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.MenuItemPrice;

import com.ftn.restaurant.repository.MenuItemPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuItemPriceRepository menuItemPriceRepository;

    @Autowired
    public MenuService(MenuItemPriceRepository menuItemPriceRepository) {

        this.menuItemPriceRepository = menuItemPriceRepository;
    }

    //Sets active menu items and returns all items
    public List<MenuItemPrice> defineActiveMenuItem(SelectedMenuItemsDTO selectedItems) {
        List<MenuItemPrice> menuItems = new ArrayList<>();
        List<Long> ids = selectedItems.getItemIds();

        ids.forEach(itemId -> {
            Optional<MenuItemPrice> maybePrice = menuItemPriceRepository
                    .findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(itemId, LocalDate.now());
            if (!maybePrice.isPresent())
                throw new ForbiddenException("Approved menu item with " + itemId + " and active price not found.");

            maybePrice.get().setActive(true);
            menuItemPriceRepository.save(maybePrice.get());
            menuItems.add(maybePrice.get());
        });

        if (ids.isEmpty()) {
            menuItemPriceRepository.findAll().forEach(
                    item -> {
                        item.setActive(false);
                        menuItemPriceRepository.save(item);
                        menuItems.add(item);
                    }
            );
        } else {
            menuItemPriceRepository.findByItemIdNotIn(ids).forEach(
                    item -> {
                        item.setActive(false);
                        menuItemPriceRepository.save(item);
                        menuItems.add(item);
                    }
            );
        }
        return menuItems;
    }

}
