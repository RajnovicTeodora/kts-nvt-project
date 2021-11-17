package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.repository.DishRepository;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;

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

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private DishRepository dishRepository;

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

    public List<MenuItemPriceDTO> searchMenuItems(String group, String name){
        List<MenuItem> menuItems = new ArrayList<MenuItem>(); 

        if (group.equalsIgnoreCase("")){
            menuItems = menuItemRepository.findByName(name);
            return collectPrices(menuItems);
        }

        if (group.equalsIgnoreCase("drink")){
            menuItems = drinkRepository.findByNameAndType(name, -1);
            return collectPrices(menuItems);
        } 

        if (group.equalsIgnoreCase("dish")){
            menuItems = dishRepository.findByNameAndType(name, -1);
            return collectPrices(menuItems);
        } 

        int groupIndex = DrinkType.isValid(group);
        if (groupIndex != -1) {
            menuItems = drinkRepository.findByNameAndType(name, groupIndex);
            return collectPrices(menuItems);
        }

        groupIndex = DishType.isValid(group);
        if (groupIndex != -1) {
            menuItems = dishRepository.findByNameAndType(name, groupIndex);
            return collectPrices(menuItems);
        }

        throw new MenuItemNotFoundException("Invalid group for filtering menu items!");
    }

    private List<MenuItemPriceDTO> collectPrices(List<MenuItem> menuItems){
        List<MenuItemPriceDTO> itemsDTOs = new ArrayList<MenuItemPriceDTO>(); 

        menuItems.forEach(item -> {
            MenuItemPrice menuItemPrice = item.getPriceList().get(item.getPriceList().size()-1);
            if (menuItemPrice.isActive() == true) {
                itemsDTOs.add(new MenuItemPriceDTO(menuItemPrice));
            } 
        });

        return itemsDTOs;
    }
}
