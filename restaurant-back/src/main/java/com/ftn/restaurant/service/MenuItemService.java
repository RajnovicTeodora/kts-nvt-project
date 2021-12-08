package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;


    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public Optional<MenuItem> findByMenuItemId(long id) {
        return menuItemRepository.findByMenuItemId(id);
    }

    public Optional<MenuItem> findByMenuItemNameAndImage(String name, String img) {
        return menuItemRepository.findByMenuItemNameAndImage(name, img);
    }

    public MenuItem deleteMenuItem(Long id) {
        Optional<MenuItem> maybeItem = menuItemRepository.findByIdAndDeletedFalse(id);
        if (!maybeItem.isPresent())
            throw new MenuItemNotFoundException("Menu item with id " + id + "not found...");
        maybeItem.get().setDeleted(true);
        return menuItemRepository.save(maybeItem.get());
    }

}
