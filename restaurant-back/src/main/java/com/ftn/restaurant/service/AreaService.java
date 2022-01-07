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
    
    @Autowired
    private TableService tableService;

    public Area addArea(String name) throws AreaAlreadyExistsException{
        if(areaRepository.findByName(name).isPresent()){
            throw new AreaAlreadyExistsException("Area with that name already exists!");
        }
        Area newArea = new Area(name);
        areaRepository.saveAndFlush(newArea);
        return newArea;
    }

    public Area deleteArea(Long id) throws Exception{
        Optional<Area> optArea = areaRepository.findById(id);
        if(!optArea.isPresent()){
            throw new AreaNotFoundException("Area not found!");
        }
        
        List<RestaurantTable> tables = tableRepository.findByAreaId(id);
        for(int i = 0; i < tables.size(); i++) {
        	tableService.deleteTable(tables.get(i).getId());
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

    public List<AreaDTO> getAllAreas(){
        List<AreaDTO> areas = new ArrayList<AreaDTO>();
        for (Area area : areaRepository.findAll()) {
            AreaDTO areaDto = new AreaDTO();
            areaDto.setId(area.getId());
            areaDto.setName(area.getName());
            List<RestaurantTable> tables = tableRepository.findByAreaId(area.getId());
            List<RestaurantTableDTO> tableDTOs = new ArrayList<RestaurantTableDTO>();
            for (RestaurantTable table : tables) {
                tableDTOs.add(new RestaurantTableDTO(table));
            }
            areaDto.setTables(tableDTOs);
            areas.add(areaDto);
        }
        //areaRepository.findAll().forEach(area -> areas.add(new AreaDTO(area)));
        return areas;
    }

    public Area findByID(Long id){
    	Optional<Area> optArea = areaRepository.findById(id);
    	if(!optArea.isPresent()) {
    		throw new AreaNotFoundException("Area not found!");
    	}
        return optArea.get();
    }
}
