package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.dto.UserTokenStateDTO;
import com.ftn.restaurant.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> login(@RequestBody LoginDTO loginRequest) {
        UserTokenStateDTO dto = userService.login(loginRequest);
        if(dto == null){
            return new ResponseEntity<>(null, HttpStatus.LOCKED);
        }
        return ResponseEntity.ok(dto);

    }

    @PostMapping(value = "/firstTimeChangePassword", consumes = "application/json")
    public ResponseEntity<Boolean> loggedFirstTime(@RequestBody LoginDTO loginDTO)
    {
        userService.loggedFirstTime(loginDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/tryChangePassword")
    public ResponseEntity<Boolean> tryChangePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword,
                                                     @RequestParam("newPassword") String newPassword) {
        return new ResponseEntity<>(userService.tryChangePassword(username, oldPassword, newPassword), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(path = "/addUser")
    //autorizacija za admina
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO addUser(@RequestBody EmployeeDTO employeeDTO){
        return (new EmployeeDTO(userService.addUser(employeeDTO)));
    }

    @ResponseBody
    @PostMapping(path = "/editUser")
    //autorizacija za admina
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO editUser(@RequestBody EmployeeDTO employeeDTO){
        return (new EmployeeDTO(userService.editUser(employeeDTO)));
    }

}
