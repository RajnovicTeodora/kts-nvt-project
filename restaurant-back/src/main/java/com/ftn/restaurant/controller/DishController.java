package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DishDTO;
import com.ftn.restaurant.dto.NewDishDTO;
import com.ftn.restaurant.service.DishService;
import com.ftn.restaurant.service.OrderedItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/dish")
public class DishController {

    private DishService dishService;

    @Autowired
    public DishController(DishService dishService){
        this.dishService = dishService; }

    @ResponseBody
    @PostMapping(path = "/addDish")
    @ResponseStatus(HttpStatus.CREATED)
    public DishDTO addDish(@RequestBody NewDishDTO dishDTO){
        return new DishDTO(dishService.addDish(dishDTO));
    }
}
