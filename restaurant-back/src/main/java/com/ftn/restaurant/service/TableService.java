package com.ftn.restaurant.service;

import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

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
}
