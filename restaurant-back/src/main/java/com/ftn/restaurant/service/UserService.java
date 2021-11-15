package com.ftn.restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.ftn.restaurant.dto.EmployeeDTO;
import com.ftn.restaurant.model.Employee;
import com.ftn.restaurant.model.Paychecks;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.repository.EmployeeRepository;
import com.ftn.restaurant.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public User findByUsername(String username) {
        // TODO Auto-generated method stub
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
        paycheckList.add(new Paychecks(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 0), null, 100));
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
}
