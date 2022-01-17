package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.Items.DishWithIngredientsDTO;
import com.ftn.restaurant.dto.Items.DrinkWithIngredientsDTO;
import com.ftn.restaurant.dto.MenuItemDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoPage;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public MenuItem approveMenuItem(Long id) {
        Optional<MenuItem> maybeItem = menuItemRepository.findByIdAndDeletedFalse(id);
        if (!maybeItem.isPresent())
            throw new MenuItemNotFoundException("Menu item with id " + id + "not found...");
        maybeItem.get().setApproved(true);
        return menuItemRepository.save(maybeItem.get());
    }

    public List<MenuItemDTO> getAll(String searchName) {

        List<MenuItemDTO> menuItemDTOS = new ArrayList<>();
        menuItemRepository.findAll(searchName)
                .forEach(item -> menuItemDTOS.add(new MenuItemDTO(item.getId(), item.getName(), item.getImage())));

        return menuItemDTOS;

    }

    public MenuItemDTO getItem(long id) {
        Optional<MenuItem> item = this.menuItemRepository.findById(id);
        if(item.isPresent()){
            if(item.get() instanceof Drink){
                return new DrinkWithIngredientsDTO((Drink) item.get());
            }else{
                return new DishWithIngredientsDTO((Dish) item.get());
            }

        }
        throw new MenuItemNotFoundException("Item with id " + id + "not found");
    }

}
