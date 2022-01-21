package com.ftn.restaurant.controller;

import java.util.List;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/employees")
public class EmployeeController {
    
    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(path = "/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO addUser(@RequestBody EmployeeDTO employeeDTO){
        return (new EmployeeDTO(userService.addUser(employeeDTO)));
    }

    @ResponseBody
    @PostMapping(path = "/editUser")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO editUser(@RequestBody EmployeeDTO employeeDTO){
        return (new EmployeeDTO(userService.editUser(employeeDTO)));
    }

    @GetMapping(path = "/getAllEmployees")
    @ResponseBody
    public List<EmployeeDTO> getAllEmployees(@RequestParam(name= "search") String search,@RequestParam(name= "filter") String filter){
        return userService.searchAndFilterEmployees(search, filter);
    }
}
