package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;

import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class MenuItemPriceService {

    private final MenuItemPriceRepository menuItemPriceRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemPriceService(MenuItemPriceRepository menuItemPriceRepository, MenuItemRepository menuItemRepository) {
        this.menuItemPriceRepository = menuItemPriceRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItemPrice updateMenuItemPrice(UpdateMenuItemPriceDTO updateMenuItemPriceDTO) {
        if (updateMenuItemPriceDTO.getNewPrice() <= 0 || updateMenuItemPriceDTO.getNewPurchasePrice() <= 0)
            throw new ForbiddenException("New purchase or sale price must be a positive value");

        Optional<MenuItemPrice> maybeMenuItemPrice = menuItemPriceRepository
                .findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(updateMenuItemPriceDTO.getMenuItemId());
        Optional<MenuItem> maybeItem = menuItemRepository.findByIdAndDeletedFalse(updateMenuItemPriceDTO.getMenuItemId());

        if (!maybeItem.isPresent()){
            throw new MenuItemNotFoundException(
                    "Menu item with id " + updateMenuItemPriceDTO.getMenuItemId() + " not found");
        }

        MenuItemPrice newPrice;
        if (!maybeMenuItemPrice.isPresent()) {
            newPrice = new MenuItemPrice(
                    LocalDate.now().plusDays(1), null, updateMenuItemPriceDTO.getNewPrice(), false,
                    updateMenuItemPriceDTO.getNewPurchasePrice(), maybeItem.get());
            menuItemPriceRepository.save(newPrice);
            return newPrice;
        } else {
            LocalDate dateFrom = maybeMenuItemPrice.get().getDateFrom();

            if (!dateFrom.equals(LocalDate.now())) {
                MenuItemPrice price = maybeMenuItemPrice.get();
                maybeMenuItemPrice.get().setDateTo(LocalDate.now());
                maybeMenuItemPrice.get().setActive(false);
                newPrice = new MenuItemPrice(LocalDate.now().plusDays(1), null, updateMenuItemPriceDTO.getNewPrice(),
                        price.isActive(), updateMenuItemPriceDTO.getNewPurchasePrice(), maybeMenuItemPrice.get().getItem());
                maybeMenuItemPrice.get().getItem().getPriceList().add(newPrice);
                menuItemPriceRepository.save(newPrice);
                return newPrice;
            } else {
                maybeMenuItemPrice.get().setPrice(updateMenuItemPriceDTO.getNewPrice());
                maybeMenuItemPrice.get().setPrice(updateMenuItemPriceDTO.getNewPurchasePrice());
                menuItemPriceRepository.save(maybeMenuItemPrice.get());
                return maybeMenuItemPrice.get();
            }
        }
    }

    public double findCurrentPriceForMenuItemById(long id){
        return menuItemPriceRepository.findCurrentPriceForMenuItemById(id);
    }
}
