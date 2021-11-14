package com.ftn.restaurant.service;

import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.repository.IngredientRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient findOne(Long id) {
        return ingredientRepository.findById(id).orElseGet(null);
    }
}
