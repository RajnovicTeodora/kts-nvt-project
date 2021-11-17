package com.ftn.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.AreaAlreadyExistsException;
import com.ftn.restaurant.exception.AreaNotFoundException;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private TableRepository tableRepository;

    public Area addArea(String name){
        if(areaRepository.findByName(name).isPresent()){
            throw new AreaAlreadyExistsException("Area with that name already exists!");
        }
        Area newArea = new Area(name);
        areaRepository.saveAndFlush(newArea);
        return newArea;
    }

    public Area deleteArea(Long id){
        Optional<Area> optArea = areaRepository.findById(id);
        if(!optArea.isPresent()){
            throw new AreaNotFoundException("Area not found!");
        }
        areaRepository.deleteById(id);
        return optArea.get();
    }

    public Area editTables (AreaDTO areaDTO){
        Optional<Area> optArea = areaRepository.findById(areaDTO.getId());
        if(!optArea.isPresent()){
            throw new AreaNotFoundException("Area not found!");
        }

        Area area = optArea.get();
        List<RestaurantTable> editedTables = new ArrayList<RestaurantTable>();

        for (RestaurantTableDTO tableDTO : areaDTO.getTables()) {
            if(tableRepository.findById(tableDTO.getId()).isPresent()){
                RestaurantTable table = tableRepository.findByTableId(tableDTO.getId());
                table.setPositionX(tableDTO.getX());
                table.setPositionY(tableDTO.getY());
                tableRepository.saveAndFlush(table);
                editedTables.add(table);
            }
        }
        
        area.setTables(editedTables);
        areaRepository.saveAndFlush(area);

        return area;


    }
}
