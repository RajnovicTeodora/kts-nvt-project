package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.dto.UserPaycheckDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.enums.ContainerType;
import com.ftn.restaurant.model.enums.DrinkType;
import com.ftn.restaurant.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ResponseBody //todo autorizacija, i ono nesto sa ing
    @GetMapping(path = "/getDrinks")
    @ResponseStatus(HttpStatus.OK)
    public List<DrinkDTO> getDrinks(){
        return this.drinkService.getDrinks(); //fali price todo
    }

    @ResponseBody
    @GetMapping(path = "/getDrinkTypes")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getDrinkTypes(){
        // return Arrays.asList(DrinkType.values());
        return DrinkType.getNames();
    }

    @ResponseBody
    @GetMapping(path = "/getDrinkContainers")
    @ResponseStatus(HttpStatus.OK)
    public List<ContainerType> getDrinkContainers(){
        return Arrays.asList(ContainerType.values());
    }

    @ResponseBody
    @GetMapping(path = "/getDrink/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DrinkDTO getDrink(@PathVariable long id){
        return this.drinkService.getDrink(id); //fali price todo
    }

    @ResponseBody
    @PostMapping(path = "/addDrink")
    @PreAuthorize("hasAnyRole('MANAGER', 'BARTENDER')")
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkDTO addDrink( @RequestBody NewDrinkDTO drinkDTO,@AuthenticationPrincipal User user){
        return new DrinkDTO(drinkService.addDrinkByBartender(drinkDTO)); 
    }
    //getSearchedOrFiltered
    @ResponseBody
    @GetMapping(path = "/getSearchedOrFiltered")
    @PreAuthorize("hasRole('BARTENDER')")
    @ResponseStatus(HttpStatus.OK)
    public List<DrinkDTO> getSearchedOrFiltered( @RequestParam(name = "searchName", required = false, defaultValue = "") String searchName,
                                                     @RequestParam(name = "filterName", required = false, defaultValue = "") String filterName) {
        List<DrinkDTO> drinkDTOS = new ArrayList<>();
        drinkService.getSearchedOrFiltered(searchName, filterName).forEach(drink -> drinkDTOS.add(new DrinkDTO(drink)));
        return drinkDTOS;
    }

}
