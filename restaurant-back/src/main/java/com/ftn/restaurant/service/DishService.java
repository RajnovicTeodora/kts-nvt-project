package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.exception.DishExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish addDish(NewDishDTO dishDTO) {

        if(dishDTO == null)
            throw new ForbiddenException("Dish must have some values.");

        if (dishDTO.getName().isEmpty() || dishDTO.getImage().isEmpty())
            throw new ForbiddenException("Dish must have a name and image");

        Optional<Dish> maybeDish = dishRepository.findByNameAndDishType(dishDTO.getName(), dishDTO.getType());
        if (maybeDish.isPresent())
            throw new DishExistsException("Dish already exists!");

        Dish dish = new Dish(dishDTO.getName(), dishDTO.getImage(), false, false, new ArrayList<MenuItemPrice>(), dishDTO.getType());

        for (IngredientDTO ingredient : dishDTO.getIngredients()){
            Ingredient newIngredient = new Ingredient(ingredient);
            dish.getIngredients().add(newIngredient);
        }

        return dishRepository.save(dish);
    }

}
