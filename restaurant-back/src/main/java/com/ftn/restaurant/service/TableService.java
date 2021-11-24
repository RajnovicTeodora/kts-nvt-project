package com.ftn.restaurant.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.AreaNotFoundException;
import com.ftn.restaurant.exception.TableNotFoundException;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void occupyTable(Long id){
        RestaurantTable rt = tableRepository.findByTableId(id);
        rt.setOccupied(true);
        save(rt);
    }

    public void clearTable(Long id){
        RestaurantTable rt = tableRepository.findByTableId(id);
        rt.setOccupied(false);
        save(rt);
    }

    public void claimTable(Long id, String waiterUsername){
        RestaurantTable rt = tableRepository.findByTableId(id);
        Waiter waiter = waiterService.findByUsername(waiterUsername);
        rt.setWaiter(waiter);
        save(rt);
    }

    public void leaveTable(Long id){
        RestaurantTable rt = tableRepository.findByTableId(id);
        rt.setWaiter(null);
        save(rt);
    }


    public RestaurantTable findOneWithWaiter(Long tableId, String waiterUsername) {
        return tableRepository.findOneWithWaiter(tableId, waiterUsername);
    }

    public RestaurantTable findOneWithoutWaiter(Long tableId) {
        return tableRepository.findOneWithoutWaiter(tableId);
    }

    public RestaurantTable deleteTable(Long tableId){
        Optional<RestaurantTable> optTable = tableRepository.findById(tableId);
        if(!optTable.isPresent()) 
            throw new TableNotFoundException("Table not found!");
        tableRepository.delete(optTable.get());
        return optTable.get();
    }

    public RestaurantTable addTable(RestaurantTableDTO tableDTO){
        if(tableRepository.findByPositionXAndPositionY(tableDTO.getX(), tableDTO.getY()).isPresent())
            return null; //ovo mozda nece biti moguce pa ne bude greske

        RestaurantTable newTable = new RestaurantTable(tableDTO);

        Optional<Area> area = areaRepository.findById(tableDTO.getAreaId());

        if(area.isPresent()) {throw new AreaNotFoundException("Area with id "+tableDTO.getAreaId()+" not found!");}
        newTable.setArea(area.get());
        area.get().addTable(newTable);
        areaRepository.saveAndFlush(area.get());
        tableRepository.saveAndFlush(newTable);
        return newTable;
    }
}
