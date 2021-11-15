package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping(path = "/addUser")
    //autorizacija za admina
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO addUser(@RequestBody EmployeeDTO employeeDTO){
        LOG.info("Adding new user...");
        return (new EmployeeDTO(userService.addUser(employeeDTO)));
    }

    @ResponseBody
    @PostMapping(path = "/editUser")
    //autorizacija za admina
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO editUser(@RequestBody EmployeeDTO employeeDTO){
        LOG.info("Editing user...");
        return (new EmployeeDTO(userService.editUser(employeeDTO)));
    }

}
