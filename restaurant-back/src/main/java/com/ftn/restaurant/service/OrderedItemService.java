package com.ftn.restaurant.service;

import com.ftn.restaurant.model.Ingredient;
import com.ftn.restaurant.model.OrderedItem;
import com.ftn.restaurant.repository.IngredientRepository;
import com.ftn.restaurant.repository.OrderedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedItemService {

    @Autowired
    private OrderedItemRepository orderedItemRepository;

    public OrderedItem findOne(Long id) {
        return orderedItemRepository.findById(id).orElseGet(null);
    }

    public OrderedItem save(OrderedItem orderItem) {
        return orderedItemRepository.save(orderItem);
    }

}
