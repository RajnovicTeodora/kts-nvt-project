package com.ftn.restaurant.controller;

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.service.AreaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/area")
public class AreaController {

    @Autowired 
    private AreaService areaService;

    @ResponseBody
    @PostMapping(path = "/addArea")
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDTO addArea(@RequestBody String name){
        return new AreaDTO(areaService.addArea(name));
    }

    @ResponseBody
    @DeleteMapping(path = "/deleteArea/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AreaDTO deleteArea(@PathVariable(value = "id") Long id){
        return new AreaDTO(areaService.deleteArea(id));
    }

    @ResponseBody
    @PutMapping(path = "/editTables")
    public AreaDTO editTables(@RequestBody AreaDTO area){
        return new AreaDTO(areaService.editTables(area));
    }
    
}
