package com.ftn.restaurant.service;

import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public MenuItem findOne(Long id) {
        return menuItemRepository.findById(id).orElseGet(null);
    }
}
