package com.ftn.restaurant.controller;

import java.util.ArrayList;
import java.util.List;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.IngredientDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.enums.DishType;
import com.ftn.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    public DishController(DishService dishService){
        this.dishService = dishService; }

    @ResponseBody
    @PostMapping(path = "/addDish")
    @PreAuthorize("hasAnyRole('MANAGER', 'HEAD_CHEF')")
    @ResponseStatus(HttpStatus.CREATED)
    public DishDTO addDish( @RequestBody NewDishDTO dishDTO){
        return new DishDTO(dishService.addDish(dishDTO));
    }

    @ResponseBody
    @GetMapping(path = "/getDishTypes")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getDishTypes(){
        return DishType.getNames();
    }

    @ResponseBody
    @GetMapping(path = "/getDishes")
    @PreAuthorize("hasAnyRole('CHEF', 'HEAD_CHEF')")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDTO> getDishes(){
        return dishService.getDishes();
    }

    //getSearchedOrFiltered
    @ResponseBody
    @GetMapping(path = "/getSearchedOrFiltered")
    @PreAuthorize("hasAnyRole('CHEF', 'HEAD_CHEF')")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDTO> getSearchedOrFiltered(@RequestParam(name = "searchName", required = false, defaultValue = "") String searchName,
                                                @RequestParam(name = "filterName", required = false, defaultValue = "") String filterName) {
        List<DishDTO> dishDTOS = new ArrayList<>();
        dishService.getSearchedOrFiltered(searchName, filterName).forEach(dish -> dishDTOS.add(new DishDTO(dish)));
        return dishDTOS;
    }
}
