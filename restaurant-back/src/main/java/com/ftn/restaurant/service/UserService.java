package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.dto.UserTokenStateDTO;
import com.ftn.restaurant.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.repository.EmployeeRepository;
import com.ftn.restaurant.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Employee addUser(EmployeeDTO employeeDTO){
        if(employeeRepository.findByUsername(employeeDTO.getUsername())!= null){
            return null;
        }
        Employee newEmployee = new Employee(employeeDTO);
        ArrayList<Paychecks> paycheckList = new ArrayList<Paychecks>();
        paycheckList.add(new Paychecks(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 0), null, 100, newEmployee));
        newEmployee.setPaychecksList(paycheckList);

        employeeRepository.saveAndFlush(newEmployee);
        return newEmployee;
    }

    public Employee editUser(EmployeeDTO employeeDTO){
        Employee employee = employeeRepository.findByUsername(employeeDTO.getUsername());
        if(employee == null) return null;
        
        if(employee.getPassword()!= employeeDTO.getPassword()) employee.setPassword(employeeDTO.getPassword());
        if(employee.getName()!= employeeDTO.getName()) employee.setName(employeeDTO.getName());
        if(employee.getSurname()!= employeeDTO.getSurname()) employee.setSurname(employeeDTO.getSurname());
        if(employee.getImage()!= employeeDTO.getImage()) employee.setImage(employeeDTO.getImage());
        if(employee.getTelephone()!= employeeDTO.getTelephone()) employee.setPassword(employeeDTO.getPassword());

        
        return employee;
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        else{
            return user;
        }
    }

    public void loggedFirstTime(LoginDTO loginDTO){
        User user = findByUsername(loginDTO.getUsername());
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
        user.setLoggedFirstTime(true);
        save(user);
    }

    public boolean tryChangePassword(String username, String oldPassword,String newPassword){
        User user = findByUsername(username);
        if (user == null) {
            return false;
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, oldPassword));
            user.setPassword(passwordEncoder.encode(newPassword));
            save(user);
        }catch (AuthenticationException e) {
            return false;
        }

        return true;
    }

    public UserTokenStateDTO login( LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRoles().get(0).getName().substring(5));
        int expiresIn = tokenUtils.getExpiredIn();
        if (!user.isDeleted()) {
            return null;
        }
        return new UserTokenStateDTO(jwt, expiresIn);
    }
}
