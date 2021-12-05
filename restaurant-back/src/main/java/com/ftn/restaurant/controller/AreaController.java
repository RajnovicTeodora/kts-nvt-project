package com.ftn.restaurant.controller;

import java.util.List;

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.model.User;
import com.ftn.restaurant.service.AreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/area")
public class AreaController {

    @Autowired 
    private AreaService areaService;

    @ResponseBody
    @PostMapping(path = "/addArea")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDTO addArea(@AuthenticationPrincipal User user, @RequestBody String name){
        return new AreaDTO(areaService.addArea(name));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteArea/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AreaDTO deleteArea(@AuthenticationPrincipal User user, @PathVariable(value = "id") Long id){
        return new AreaDTO(areaService.deleteArea(id));
    }

    @ResponseBody
    @PutMapping(path = "/editTables")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AreaDTO editTables(@AuthenticationPrincipal User user, @RequestBody AreaDTO area){
        return new AreaDTO(areaService.editTables(area));
    }

    @ResponseBody
    @GetMapping(path = "/getAllAreas")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<AreaDTO> getAllAreas(@AuthenticationPrincipal User user){
        return areaService.getAllAreas();
    }
    
    @ResponseBody
    @GetMapping(path = "/getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public AreaDTO getById(@AuthenticationPrincipal User user, @PathVariable(value = "id") Long id){
        return new AreaDTO(areaService.findByID(id));
    }
    
}
