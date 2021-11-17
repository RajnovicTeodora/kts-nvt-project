package com.ftn.restaurant.service;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    private DrinkRepository drinkRepository;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<DrinkDTO> getDrinks(){
        ArrayList<Drink> drinks = (ArrayList<Drink>) this.drinkRepository.getApprovedDrinks();
        ArrayList<DrinkDTO> drinksDTO = new ArrayList<>();
        for (Drink drink : drinks) {
            DrinkDTO dto = new DrinkDTO(drink);
            drinksDTO.add(dto);
        }
        return drinksDTO;
    }

    public DrinkDTO getDrink(long id) {
        Drink drink = this.drinkRepository.getById(id);
        if(drink != null){
            DrinkDTO dto = new DrinkDTO(drink);
            return dto;
        }
        return null;
    }

    public Drink addDrink(NewDrinkDTO drinkDTO) { //ingredients, and if it made bartender, approved false todo

        if (drinkDTO.getName().isEmpty() || drinkDTO.getImage().isEmpty())
            throw new ForbiddenException("Drink must have a name and image");

        Optional<Drink> maybeDrink = drinkRepository.findByNameAndDrinkTypeAndContainerType(drinkDTO.getName(), drinkDTO.getType(), drinkDTO.getContainerType());
        if (maybeDrink.isPresent())
            throw new DrinkExistsException("Drink already exists!");

        Drink drink = new Drink(drinkDTO.getName(), drinkDTO.getImage(), true, false, new ArrayList<MenuItemPrice>(), drinkDTO.getType(), drinkDTO.getContainerType());
        return drinkRepository.save(drink);
    }
}
