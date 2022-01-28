package com.ftn.restaurant.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.UserService;
import com.ftn.restaurant.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private TokenUtils tokenUtils;
    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER')")
    @PostMapping(value = "/firstTimeChangePassword", consumes = "application/json")
    public ResponseEntity<?> loggedFirstTime(@RequestBody LoginDTO loginDTO)
    {
        try {
            return new ResponseEntity<>(userService.loggedFirstTime(loginDTO), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>("Your credentials are bad. Please, try again", HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e){
            return new ResponseEntity<>("User with username "+ loginDTO.getUsername() + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER', 'MANAGER')")
    @PostMapping(value = "/changePassword", consumes = "application/json")
    public ResponseEntity<?> tryChangePassword(@RequestBody LoginDTO loginDTO) {
        try {
            return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>("Your credentials are bad. Please, try again", HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e){
            return new ResponseEntity<>("User with username "+ loginDTO.getUsername() + " not found!", HttpStatus.NOT_FOUND);
        }
    }

    //@PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER', 'ADMIN', 'MANAGER')")
    @PostMapping(value = "/switchToActiveAccount", consumes = "application/json")
    public ResponseEntity<?> switchToActiveAccount(@RequestBody LoginDTO loginDTO){
        try {
            return new ResponseEntity<>(userService.switchToActiveAccount(loginDTO), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>("Your credentials are bad. Please, try again", HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e){
            return new ResponseEntity<>("User with username "+ loginDTO.getUsername() + " not found!", HttpStatus.NOT_FOUND);
        }

    }
}
