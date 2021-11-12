package com.ftn.restaurant.service;

import com.ftn.restaurant.controller.ManagerController;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.exception.DrinkExistsException;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.DrinkRepository;
import com.ftn.restaurant.repository.ManagerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerController.class);

    private ManagerRepository managerRepository;
    private DrinkRepository drinkRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, DrinkRepository drinkRepository) {
        this.managerRepository = managerRepository;
        this.drinkRepository = drinkRepository;
    }

    public Drink addDrink(NewDrinkDTO drinkDTO) {
        LOG.info("Add new drink...");

        if (drinkDTO.getName().isEmpty() || drinkDTO.getImage().isEmpty())
            throw new ForbiddenException("Drink must have a name and image");

        Optional<Drink> maybeDrink = drinkRepository.findByNameAndDrinkTypeAndContainerType(drinkDTO.getName(), drinkDTO.getType(), drinkDTO.getContainerType());
        if (maybeDrink.isPresent())
            throw new DrinkExistsException("Drink already exists!");

        Drink drink = new Drink(drinkDTO.getName(), drinkDTO.getImage(), true, false, new ArrayList<MenuItemPrice>(), drinkDTO.getType(), drinkDTO.getContainerType());
        return drinkRepository.save(drink);
    }

}
