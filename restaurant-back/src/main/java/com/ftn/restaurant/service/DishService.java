package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DishExistsException;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DishRepository;
import com.ftn.restaurant.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DishService {

    private DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish addDish(NewDishDTO dishDTO) { //ingredients, approved false todo

        if (dishDTO.getName().isEmpty() || dishDTO.getImage().isEmpty())
            throw new ForbiddenException("Dish must have a name and image");

        Optional<Dish> maybeDish = dishRepository.findByNameAndDrinkType(dishDTO.getName(), dishDTO.getType());
        if (maybeDish.isPresent())
            throw new DishExistsException("Dish already exists!");

        Dish dish = new Dish(dishDTO.getName(), dishDTO.getImage(), false, false, new ArrayList<MenuItemPrice>(), dishDTO.getType());
        return dishRepository.save(dish);
    }

}
