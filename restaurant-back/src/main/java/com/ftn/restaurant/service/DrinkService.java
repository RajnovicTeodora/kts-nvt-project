package com.ftn.restaurant.service;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.Items.DrinkWithIngredientsDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<DrinkDTO> getDrinks(){
        ArrayList<Drink> drinks = (ArrayList<Drink>) this.drinkRepository.getApprovedDrinks();
        ArrayList<DrinkDTO> drinksDTO = new ArrayList<>();
        for (Drink drink : drinks) {
            DrinkDTO dto = new DrinkDTO(drink, "");
            drinksDTO.add(dto);
        }
        return drinksDTO;
    }

    public DrinkDTO getDrink(long id) {
        Optional<Drink> drink = this.drinkRepository.findById(id);
        if(drink.isPresent()){
            if(drink.get().isApproved() && !drink.get().isDeleted()){
                return new DrinkWithIngredientsDTO(drink.get());
            }
        }
        return null;
    }

    public Drink addDrinkByBartender(NewDrinkDTO drinkDTO){
        Drink drink = addDrink(drinkDTO);
        drink.setApproved(false);

        if(drinkDTO.getIngredients() != null){
        for (IngredientDTO ingredient : drinkDTO.getIngredients()){
            Ingredient newIngredient = new Ingredient(ingredient);
            drink.getIngredients().add(newIngredient);
            }
        }
        return drinkRepository.save(drink);
    }

    public Drink addDrinkByManager(NewDrinkDTO drinkDTO){
        Drink drink = addDrink(drinkDTO);
        return drinkRepository.save(drink);
    }

    public Drink addDrink(NewDrinkDTO drinkDTO) {

        if (drinkDTO.getName().isEmpty() || drinkDTO.getImage().isEmpty())
            throw new ForbiddenException("Drink must have a name and image");

        Optional<Drink> maybeDrink = drinkRepository.findByNameAndDrinkTypeAndContainerType(drinkDTO.getName(), drinkDTO.getDrinkType(), drinkDTO.getContainerType());
        if (maybeDrink.isPresent())
            throw new DrinkExistsException("Drink already exists!");

        return new Drink(drinkDTO.getName(), drinkDTO.getImage(), true, false, new ArrayList<>(), drinkDTO.getDrinkType(), drinkDTO.getContainerType(), new ArrayList<>());
    }

    public List<Drink> getSearchedOrFiltered(String searchName, String filterName) {
        List<Drink> drinks = drinkRepository.getApprovedDrinks();
        List<Drink> searchedDrinks = new ArrayList<>();
        if(!searchName.equals("")){
            for(Drink drink : drinks){
                if(drink.getName().equalsIgnoreCase(searchName)){
                    searchedDrinks.add(drink);
                }
            }drinks = searchedDrinks;
        }

        searchedDrinks = new ArrayList<>();
        System.out.println("moj filter"+filterName);
        if(!filterName.equals("")){
            for(Drink drink : drinks){
                System.out.println("yoj filter"+drink.getDrinkType().name());
                if(drink.getDrinkType().name().equals(filterName)){
                    searchedDrinks.add(drink);
                }
            }
            drinks =searchedDrinks;
        }
        return drinks;
    }
}
