package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.dto.UserTokenStateDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.UserService;
import com.ftn.restaurant.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> login(
            @RequestBody LoginDTO loginRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        // Potrebno srediti uloge - koje slati iz liste ili slati celu listu
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRoles().get(0).getName().substring(5));
        int expiresIn = tokenUtils.getExpiredIn();

        if(!user.isDeleted())
        {
            return new ResponseEntity<>(null, HttpStatus.LOCKED);
        }

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

    @PostMapping(value = "/firstTimeChangePassword", consumes = "application/json")
    public ResponseEntity<Boolean> loggedFirstTime(@RequestBody LoginDTO loginDTO)
    {
        User user = userService.findByUsername(loginDTO.getUsername());

        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        user.setLoggedFirstTime(true);

        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/tryChangePassword")
    public ResponseEntity<Boolean> tryChangePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword,
                                                     @RequestParam("newPassword") String newPassword) {

        User user = userService.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

        //provera sifre i postavljanje nove!
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, oldPassword));

            //ispravna lozinka
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);



        }catch (AuthenticationException e) {
            return new ResponseEntity<>(false, HttpStatus.OK); //neispravna lozinka
        }


        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
