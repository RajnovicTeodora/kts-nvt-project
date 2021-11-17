package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/drink")
public class DrinkController {

    private DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService){
        this.drinkService = drinkService;
    }

    @ResponseBody
    @GetMapping(path = "/getDrinks")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DrinkDTO> getDrinks(){
        return this.drinkService.getDrinks(); //fali price todo
    }

    @ResponseBody
    @GetMapping(path = "/getDrinkTypes")
    @ResponseStatus(HttpStatus.FOUND)
    public List<DrinkType> getDrinkTypes(){
        return Arrays.asList(DrinkType.values());
    }

    @ResponseBody
    @GetMapping(path = "/getDrinkContainers")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ContainerType> getDrinkContainers(){
        return Arrays.asList(ContainerType.values());
    }

    @ResponseBody
    @GetMapping(path = "/getDrink/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public DrinkDTO getDrink(@PathVariable long id){
        return this.drinkService.getDrink(id); //fali price todo
    }

    @ResponseBody
    @PostMapping(path = "/addDrink")
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkDTO addDrink(@RequestBody NewDrinkDTO drinkDTO){
        return new DrinkDTO(drinkService.addDrink(drinkDTO));
    }

}
