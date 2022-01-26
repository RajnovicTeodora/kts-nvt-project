package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.dto.MenuItemPriceDTO;
import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
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

    //Toggle active status for menu item
    public MenuItemPrice toggleIsMenuItemActive(long id) {

        Optional<MenuItemPrice> maybePrice = menuItemPriceRepository
                    .findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(id, LocalDate.now());
        if (!maybePrice.isPresent())
            throw new ForbiddenException("Approved menu item with " + id + " and active price not found.");
        maybePrice.get().setActive(!maybePrice.get().isActive()); //toggle
        return menuItemPriceRepository.save(maybePrice.get());
    }

    public List<MenuItemPriceDTO> searchMenuItems(String group, String name){
        List<MenuItem> menuItems = new ArrayList<MenuItem>(); 

        if (group.equalsIgnoreCase("...")){
            List<MenuItem> all = menuItemRepository.findAll();
            for (MenuItem item : all) {
                if(item.getName().toLowerCase().contains(name.toLowerCase()) || name.equalsIgnoreCase("...")){
                    if(item.isDeleted() == false && item.isApproved() == true){
                        menuItems.add(item);
                    }
                }
            };
            return collectPrices(menuItems);
        }

        if (group.equalsIgnoreCase("drink")){
            List<Drink> allDrinks = drinkRepository.findAll();
            for (Drink item : allDrinks) {
                if(item.getName().toLowerCase().contains(name.toLowerCase()) || name.equalsIgnoreCase("...")){
                    if(item.isDeleted() == false && item.isApproved() == true){
                        menuItems.add(item);
                    }
                }
            };
            return collectPrices(menuItems);
        } 

        if (group.equalsIgnoreCase("dish")){
            List<Dish> allDishes = dishRepository.findAll();
            for (Dish item : allDishes) {
                if(item.getName().toLowerCase().contains(name.toLowerCase()) || name.equalsIgnoreCase("...")){
                    if(item.isDeleted() == false && item.isApproved() == true){
                        menuItems.add(item);
                    }
                }
            };
            return collectPrices(menuItems);
        } 

        int groupIndex = DrinkType.isValid(group);
        if (groupIndex != -1) {
            List<Drink> allDrinks = drinkRepository.findAll();
            for (Drink item : allDrinks) {
                if((item.getName().toLowerCase().contains(name.toLowerCase()) || name.equalsIgnoreCase("...")) && item.getDrinkType() == DrinkType.valueOf(group)){
                    if(item.isDeleted() == false && item.isApproved() == true){
                        menuItems.add(item);
                    }
                }
            };
            return collectPrices(menuItems);
        }

        groupIndex = DishType.isValid(group);
        if (groupIndex != -1) {
            List<Dish> allDishes = dishRepository.findAll();
            for (Dish item : allDishes) {
                if((item.getName().toLowerCase().contains(name.toLowerCase()) || name.equalsIgnoreCase("...")) && item.getDishType() == DishType.valueOf(group)){
                    if(item.isDeleted() == false && item.isApproved() == true){
                        menuItems.add(item);
                    }
                }
            };
            return collectPrices(menuItems);
        }

        throw new MenuItemNotFoundException("Invalid group for filtering menu items!");
    }

    private List<MenuItemPriceDTO> collectPrices(List<MenuItem> menuItems){
        List<MenuItemPriceDTO> itemsDTOs = new ArrayList<MenuItemPriceDTO>(); 

        for (MenuItem item : menuItems) {
            Optional<MenuItemPrice> optionalPrice = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(item.getId());
            if (optionalPrice.isPresent() && optionalPrice.get().isActive() == true) {
                itemsDTOs.add(new MenuItemPriceDTO(optionalPrice.get()));
            } 
        }

        return itemsDTOs;
    }

    public List<MenuItemPriceDTO> getActiveMenuItem(String searchName) {
        List<MenuItemPriceDTO> menuItemPriceDTOS = new ArrayList<>();
        menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria(searchName).forEach(item -> {
            Optional<MenuItemPrice> price = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(item.getId());
            if(price.isPresent()){
                menuItemPriceDTOS.add(new MenuItemPriceDTO(price.get()));
            }else{
                MenuItemDTO menuItemDTO = new MenuItemDTO(item);
                menuItemPriceDTOS.add(new MenuItemPriceDTO(menuItemDTO, 0, 0, false, true));
            }
        });
        return menuItemPriceDTOS;
    }

    public MenuItem deleteMenuItem(long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findByIdAndDeletedFalse(id);
        if (!menuItem.isPresent())
            throw new ForbiddenException("Menu item with " + id + " not found.");

        //Get last price
        Optional<MenuItemPrice> menuItemPrice = menuItemPriceRepository.findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(id);

        if (menuItemPrice.isPresent()){
           menuItemPrice.get().setActive(false);
           menuItemPrice.get().setDateTo(LocalDate.now());
           menuItemPriceRepository.save(menuItemPrice.get());
        }
        menuItem.get().setDeleted(true);
        return menuItemRepository.save(menuItem.get());
    }
}
