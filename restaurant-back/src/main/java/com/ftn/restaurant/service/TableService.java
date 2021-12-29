package com.ftn.restaurant.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.*;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private WaiterService waiterService;

    public RestaurantTable findOne(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public RestaurantTable save(RestaurantTable restaurantTable) {
        return tableRepository.save(restaurantTable);
    }

    public String occupyTable(String waiter, long id){
        RestaurantTable rt = findOneWithWaiter(id, waiter);
        if (rt != null) {
            if(rt.isOccupied()){
                throw new ForbiddenException("Table is already occupied");
            }
            rt.setOccupied(true);
            save(rt);
            return "Successfully occupied table with id: " + id;
        }
        throw new NotFoundException("Couldn't find table with id: " + id);
    }

    public String clearTable(String waiter, long id){
        RestaurantTable rt = findOneWithWaiter(id, waiter);
        if (rt != null) {
            if(!rt.isOccupied()){
                throw new ForbiddenException("Table is already cleared");
            }
            rt.setOccupied(false);
            save(rt);
            return "Successfully cleared table with id: " + id;
        }
        throw new NotFoundException("Couldn't find table with id: " + id);
    }

    public String claimTable(String waiterUsername, long id){
        RestaurantTable rt = findOne(id);
        Waiter waiter = waiterService.findByUsername(waiterUsername);
        if (rt != null && waiter!=null) {
            if(rt.getWaiter()!= null){
                throw new ForbiddenException("Table is already claimed");
            }
            rt.setWaiter(waiter);
            save(rt);
            return "Successfully claimed table with id: " + id;
        }
        throw new NotFoundException("Couldn't find table with id: " + id);
    }

    public String leaveTable( String waiterUsername, long id){
        RestaurantTable rt = findOneWithWaiter(id, waiterUsername);
        if (rt != null ) {
            if(rt.isOccupied()){
                throw new ForbiddenException("Can't leave table while its occupied");
            }
            rt.setWaiter(null);
            save(rt);
            return "Successfully left table with id: " + id;
        }
        throw new NotFoundException("Couldn't find table with id: " + id);

    }

    public RestaurantTable findOneWithWaiter(Long tableId, String waiterUsername) {
        return tableRepository.findOneWithWaiter(tableId, waiterUsername);
    }

    public RestaurantTable deleteTable(Long tableId){
        Optional<RestaurantTable> optTable = tableRepository.findById(tableId);
        if(!optTable.isPresent()) {
            // throw new TableNotFoundException("Table not found!");
            return null;
        }
            
        tableRepository.delete(optTable.get());
        return optTable.get();
    }

    public RestaurantTable addTable(RestaurantTableDTO tableDTO){
        if(tableRepository.findByPositionXAndPositionY(tableDTO.getX(), tableDTO.getY()).isPresent())
            return null; //ovo mozda nece biti moguce pa ne bude greske

        RestaurantTable newTable = new RestaurantTable(tableDTO);

        Optional<Area> area = areaRepository.findById(tableDTO.getAreaId());

        if(!area.isPresent()) {throw new AreaNotFoundException("Area with id "+tableDTO.getAreaId()+" not found!");}
        newTable.setArea(area.get());
        //area.get().addTable(newTable);
        //areaRepository.saveAndFlush(area.get());
        tableRepository.saveAndFlush(newTable);
        return newTable;
    }
}
