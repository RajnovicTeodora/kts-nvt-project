package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.DrinkDTO;
import com.ftn.restaurant.dto.NewDrinkDTO;
import com.ftn.restaurant.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/manager")
public class ManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerController.class);

    private ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @ResponseBody
    @PostMapping(path = "/addDrink")
    //TODO @PreAuthorize("hasRole('X')") + @AuthenticationPrincipal Manager manager
    @ResponseStatus(HttpStatus.CREATED)
    public DrinkDTO addDrink(@RequestBody NewDrinkDTO drinkDTO){
        LOG.info("Add new drink...");
        return new DrinkDTO(managerService.addDrink(drinkDTO));
    }

}
