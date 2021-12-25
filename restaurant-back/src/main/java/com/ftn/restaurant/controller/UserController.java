package com.ftn.restaurant.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.UserService;
import com.ftn.restaurant.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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


    /*@PostMapping("/login")
    public void login(@RequestBody LoginDTO loginRequest, HttpServletResponse response) {
        
        LOG.info("Received request for login");

        User user = userService.login(loginRequest);
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());

		// Create a cookie
		Cookie cookie = new Cookie("accessToken", jwt);
		cookie.setMaxAge(7 * 24 * 60 * 60); // Expires in 7 days
		// cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/"); // Global cookie accessible everywhere

		// Add cookie to response
		response.addCookie(cookie);

        LOG.info("Login done :)");

    }*/

    @PostMapping("/logout")
	public void logout(HttpServletResponse response) {
		// create a cookie
		Cookie cookie = new Cookie("accessToken", null);
		cookie.setMaxAge(0);
		// cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");

		//add cookie to response
		response.addCookie(cookie);
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
}
