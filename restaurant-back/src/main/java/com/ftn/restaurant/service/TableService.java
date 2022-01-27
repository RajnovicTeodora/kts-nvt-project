package com.ftn.restaurant.service;

import java.util.Optional;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.*;
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

    @Autowired
    private OrderService orderService;

    public RestaurantTable findOne(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    public RestaurantTable save(RestaurantTable restaurantTable) {
        return tableRepository.save(restaurantTable);
    }

    public String occupyTable(String waiter, int tableNum){
        RestaurantTable rt = findOneWithWaiter(tableNum, waiter);
        if (rt != null) {
            if(rt.isOccupied()){
                throw new ForbiddenException("Table is already occupied");
            }
            rt.setOccupied(true);
            save(rt);
            return "Successfully occupied table with table number: " + tableNum;
        }
        throw new NotFoundException("Couldn't find table with table number: " + tableNum);
    }

    public String clearTable(String waiter, int tableNum){//unoccupy
        RestaurantTable rt = findOneWithWaiter(tableNum, waiter);
        if (rt != null) {
            if(!rt.isOccupied()){
                throw new ForbiddenException("Table is already cleared");
            }
            if(!orderService.getActiveOrdersForTable(tableNum, waiter).isEmpty()){
                throw new ActiveOrdersPresentException("Can't unoccupy table when there are active orders.");
            }
            rt.setOccupied(false);
            save(rt);
            return "Successfully cleared table with table number: " + tableNum;
        }
        throw new NotFoundException("Couldn't find table with table number: " + tableNum);
    }

    public String claimTable(String waiterUsername, int tableNum){
        Optional<RestaurantTable> rt = tableRepository.getTableByTableNumber(tableNum);
        Waiter waiter = waiterService.findByUsername(waiterUsername);
        if (rt.isPresent() && waiter!=null) {
            if(rt.get().getWaiter()!= null){
                throw new ForbiddenException("Table is already claimed");
            }
            rt.get().setWaiter(waiter);
            save(rt.get());
            return "Successfully claimed table with table number: " + tableNum;
        }
        throw new NotFoundException("Couldn't find table with table number: " + tableNum);
    }

    public String leaveTable( String waiterUsername, int tableNum){//unclaim
        RestaurantTable rt = findOneWithWaiter(tableNum, waiterUsername);
        if (rt != null ) {
            if(rt.isOccupied()){
                throw new ForbiddenException("Can't leave table while its occupied");
            }
            rt.setWaiter(null);
            save(rt);
            return "Successfully left table with table number: " + tableNum;
        }
        throw new NotFoundException("Couldn't find table with table number: " + tableNum);

    }

    public RestaurantTable findOneWithWaiter(int tableNum, String waiterUsername) {
        return tableRepository.findOneWithWaiter(tableNum, waiterUsername);
    }

    public RestaurantTable deleteTable(Long tableId) throws Exception{
        Optional<RestaurantTable> optTable = tableRepository.findById(tableId);
        if(!optTable.isPresent()) {
            throw new TableNotFoundException("Table not found!");
        }
        
        if(optTable.get().isOccupied()) {
        	throw new TableOccupiedException("Table is occupied!");
        }
        Area area = optTable.get().getArea();
        area.getTables().remove(optTable.get());
        areaRepository.saveAndFlush(area);
            
        tableRepository.delete(optTable.get());
        return optTable.get();
    }

    public RestaurantTable addTable(RestaurantTableDTO tableDTO){
        // if(tableRepository.findByPositionXAndPositionY(tableDTO.getX(), tableDTO.getY()).isPresent())
        //     return null; //ovo mozda nece biti moguce pa ne bude greske

        RestaurantTable newTable = new RestaurantTable(tableDTO);

        Optional<Area> area = areaRepository.findById(tableDTO.getAreaId());

        if(!area.isPresent()) {throw new AreaNotFoundException("Area with id "+tableDTO.getAreaId()+" not found!");}
        newTable.setArea(area.get());
        //area.get().addTable(newTable);
        //areaRepository.saveAndFlush(area.get());
        tableRepository.saveAndFlush(newTable);
        return newTable;
    }

    public RestaurantTableDTO getTableByTableNumber(int tableNum){
        Optional<RestaurantTable> table = tableRepository.getTableByTableNumber(tableNum);
        if(table.isPresent()){
            RestaurantTableDTO restaurantTableDTO = new RestaurantTableDTO();
            restaurantTableDTO.setTableNum(table.get().getTableNum());
            restaurantTableDTO.setX(table.get().getPositionX());
            restaurantTableDTO.setY(table.get().getPositionY());
            if(table.get().getWaiter() != null){
                Optional<Waiter> waiter = waiterService.findById(table.get().getWaiter().getId());
                waiter.ifPresent(value -> restaurantTableDTO.setWaiterUsername(value.getUsername()));
            }else
                restaurantTableDTO.setWaiterUsername("");
            restaurantTableDTO.setAreaId(table.get().getArea().getId());
            restaurantTableDTO.setOccupied(table.get().isOccupied());
            return  restaurantTableDTO;
        }
        throw new NotFoundException("Couldn't find table with table number: " + tableNum);
    }

    public Optional<RestaurantTable> findByTableNumber(int tableNum){
        return tableRepository.getTableByTableNumber(tableNum);
    }


}
