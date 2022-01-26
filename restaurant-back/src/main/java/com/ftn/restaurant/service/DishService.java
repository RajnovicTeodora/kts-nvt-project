package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.Items.DishWithIngredientsDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.exception.DishExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Dish;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DishRepository;
import com.ftn.restaurant.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    public DishService(DishRepository dishRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public Dish addDish(NewDishDTO dishDTO) {
        System.out.println(dishDTO == null);
        System.out.println("jej1");
        System.out.println(dishDTO.getName());
        System.out.println(dishDTO.getImage());
        if(dishDTO == null)
            throw new ForbiddenException("Dish must have some values.");
        System.out.println("jej");
        if (dishDTO.getName().isEmpty() || dishDTO.getImage().isEmpty())
            throw new ForbiddenException("Dish must have a name and image");
        System.out.println("jej2");
        Optional<Dish> maybeDish = dishRepository.findByNameAndDishType(dishDTO.getName(), dishDTO.getType());
        if (maybeDish.isPresent())
            throw new DishExistsException("Dish already exists!");

        Dish dish = new Dish(dishDTO.getName(), dishDTO.getImage(), false, false, new ArrayList<MenuItemPrice>(), dishDTO.getType());

        for (IngredientDTO ingredient : dishDTO.getIngredients()){

            Ingredient newIngredient = new Ingredient(ingredient);
            dish.getIngredients().add(newIngredient);
            this.ingredientRepository.save(newIngredient);
        }
        System.out.println("stiglo");
        return dishRepository.save(dish);
    }

    public List<DishDTO> getDishes() {

        List<Dish> allDishes = dishRepository.findAproved();
        List<DishDTO> dtos= new ArrayList<>();
        for(Dish d: allDishes){
            dtos.add(new DishDTO(d));
        }
        return dtos;
    }

    public List<Dish> getSearchedOrFiltered(String searchName, String filterName) {
        List<Dish> dishes = dishRepository.getApprovedDishes();
        List<Dish> searchedDishes = new ArrayList<>();
        if(!searchName.equals("")){
            for(Dish dish : dishes){
                if(dish.getName().toLowerCase().equals(searchName.toLowerCase())){
                    searchedDishes.add(dish);
                }
            }dishes = searchedDishes;
        }

        searchedDishes = new ArrayList<>();
        System.out.println("moj filter"+filterName);
        if(!filterName.equals("")){
            for(Dish dish : dishes){
                System.out.println("yoj filter"+dish.getDishType().name());
                if(dish.getDishType().name().equals(filterName)){
                    searchedDishes.add(dish);
                }
            }
            dishes =searchedDishes;
        }
        return dishes;
    }
}
