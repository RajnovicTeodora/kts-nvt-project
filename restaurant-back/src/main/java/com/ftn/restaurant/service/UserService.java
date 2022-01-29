package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.LoginDTO;
import com.ftn.restaurant.dto.UserDTO;
import com.ftn.restaurant.exception.BadRequestException;
import com.ftn.restaurant.exception.BadUserRoleException;
import com.ftn.restaurant.exception.EmployeeNotFoundException;
import com.ftn.restaurant.exception.NotFoundException;
import com.ftn.restaurant.exception.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.model.Bartender;
import com.ftn.restaurant.model.Chef;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.HeadChef;
import com.ftn.restaurant.model.Manager;
import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.EmployeeRepository;
import com.ftn.restaurant.repository.UserRepository;
import com.ftn.restaurant.repository.UserRoleRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Employee addUser(EmployeeDTO employeeDTO){
        if(employeeRepository.findByUsernameAndNotDeleted(employeeDTO.getUsername()).isPresent()){
            throw new UsernameExistsException("Username already exists!");
        }

        Employee newEmployee;
        switch(employeeDTO.getRole()){
            case "MANAGER":
                newEmployee = new Manager(employeeDTO);
            break;
            case "HEAD_CHEF":
                newEmployee = new HeadChef(employeeDTO); 
            break;
            case "CHEF":
                newEmployee = new Chef(employeeDTO);
            break;
            case "BARTENDER":
                newEmployee = new Bartender(employeeDTO);
            break;
            case "WAITER":
                newEmployee = new Waiter(employeeDTO);
            break;
            default:
                throw new BadUserRoleException("Unknown user role!");
        }
        newEmployee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        newEmployee.setRole(userRoleRepository.findByName(employeeDTO.getRole()).get());
        ArrayList<Paychecks> paycheckList = new ArrayList<Paychecks>();
        paycheckList.add(new Paychecks(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1), null, 100, newEmployee));
        newEmployee.setPaychecksList(paycheckList);

        userRepository.saveAndFlush(newEmployee);
        employeeRepository.saveAndFlush(newEmployee);
        return newEmployee;
    }

    public Employee editUser(EmployeeDTO employeeDTO){
        Optional<Employee> optEmployee = employeeRepository.findByUsername(employeeDTO.getUsername());
        if(!optEmployee.isPresent()) throw new EmployeeNotFoundException("Username not found!");
        Employee employee = optEmployee.get();
        if(!employee.getName().equals(employeeDTO.getName())) employee.setName(employeeDTO.getName());
        if(!employee.getSurname().equals(employeeDTO.getSurname())) employee.setSurname(employeeDTO.getSurname());
        if(!employee.getImage().equals(employeeDTO.getImage())) employee.setImage(employeeDTO.getImage());
        if(!employee.getTelephone().equals(employeeDTO.getTelephone())) employee.setTelephone(employeeDTO.getTelephone());

        employeeRepository.saveAndFlush(employee);

        return employee;
    }

    public Employee deleteUser(String username){
        Optional<Employee> optEmployee = employeeRepository.findByUsername(username);
        if(optEmployee.isPresent()){
            Employee employee = optEmployee.get();
            employee.setDeleted(true);
            employeeRepository.saveAndFlush(employee);
            return employee;
        }
        throw new EmployeeNotFoundException("User not found!");
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        else{
            return user;
        }
    }

    public String loggedFirstTime(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
        if(!user.isPresent()){
            throw new NotFoundException("User with username "+ loginDTO.getUsername() + " not found!");
        }
        SecurityContextHolder.clearContext();
        Authentication authentication;
        try {
            user.get().setPassword(passwordEncoder.encode(loginDTO.getPassword()));
            user.get().setLoggedFirstTime(false);
            save(user.get());
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Your credentials are bad. Please, try again");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Successfully changed password!";
    }

    public String changePassword(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
        if(!user.isPresent()){
            throw new NotFoundException("User with username "+ loginDTO.getUsername() + " not found!");
        }
        SecurityContextHolder.clearContext();
        Authentication authentication;
        try {
            user.get().setPassword(passwordEncoder.encode(loginDTO.getPassword()));
            save(user.get());
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Your credentials are bad. Please, try again");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Successfully changed password!";
    }

    public List<UserDTO> getAllUsers(){
        List<UserDTO> users = new ArrayList<UserDTO>();
        userRepository.findAll().forEach(item -> users.add(new UserDTO(item)));
        return users;
    }

    public List<EmployeeDTO> searchAndFilterEmployees(String search, String filter) {
        List<EmployeeDTO> users = new ArrayList<EmployeeDTO>();
        employeeRepository.findBySearchCriteriaAndUserRoleAndNotDeleted(search, filter).forEach(item -> users.add(new EmployeeDTO(item)));

        return users;
    }

    public boolean findIsLoggedInFirstTimeByUsername(String username){
        return userRepository.findIsLoggedInFirstTimeByUsername(username);
    }

    public String switchToActiveAccount(LoginDTO loginDTO){
        Optional<User> user = userRepository.findByUsername(loginDTO.getUsername());
        if(!user.isPresent()){
            throw new NotFoundException("User with username "+ loginDTO.getUsername() + " not found!");
        }
        SecurityContextHolder.clearContext();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Your credentials are bad. Please, try again");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Successfully switched accounts!";
    }

    public long findUserIdByUsername(String username){
        return userRepository.findUserIdByUsername(username);
    }
}
