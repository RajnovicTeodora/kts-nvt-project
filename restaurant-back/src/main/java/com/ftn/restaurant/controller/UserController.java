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
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasAnyRole('CHEF', 'BARTENDER', 'HEAD_CHEF', 'WAITER')")
    @PostMapping(value = "/firstTimeChangePassword", consumes = "application/json")
    public ResponseEntity<?> loggedFirstTime(@RequestBody LoginDTO loginDTO)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity(userService.loggedFirstTime(loginDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/tryChangePassword")
    public ResponseEntity<Boolean> tryChangePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword,
                                                     @RequestParam("newPassword") String newPassword) {
        return new ResponseEntity<>(userService.tryChangePassword(username, oldPassword, newPassword), HttpStatus.OK);
    }
}
