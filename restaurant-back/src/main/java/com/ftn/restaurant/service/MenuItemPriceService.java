package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.UpdateMenuItemPriceDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.exception.MenuItemNotFoundException;

import com.ftn.restaurant.exception.MenuItemPriceNotFoundException;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import com.ftn.restaurant.repository.MenuItemRepository;
import javassist.NotFoundException;
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

    public MenuItemPrice findLastPrice(long id) {
        Optional<MenuItemPrice> maybeMenuItemPrice = menuItemPriceRepository
                .findByItemIdAndItemDeletedFalseAndItemApprovedTrueAndDateToIsNull(id);
        return maybeMenuItemPrice.orElse(null);
    }

    public MenuItemPrice updateMenuItemPrice(UpdateMenuItemPriceDTO updateMenuItemPriceDTO) {
        if (updateMenuItemPriceDTO.getNewPrice() <= 0 || updateMenuItemPriceDTO.getNewPurchasePrice() <= 0)
            throw new ForbiddenException("New purchase or sale price must be a positive value");
        Optional<MenuItem> maybeItem = menuItemRepository.findByIdAndDeletedFalse(updateMenuItemPriceDTO.getMenuItemId());

        if (!maybeItem.isPresent()) {
            throw new MenuItemNotFoundException(
                    "Menu item with id " + updateMenuItemPriceDTO.getMenuItemId() + " not found");
        }

        MenuItemPrice oldPrice = findLastPrice(updateMenuItemPriceDTO.getMenuItemId());

        MenuItemPrice newPrice;
        if (oldPrice == null) {
            newPrice = new MenuItemPrice(
                    LocalDate.now().plusDays(1), null, updateMenuItemPriceDTO.getNewPrice(), false,
                    updateMenuItemPriceDTO.getNewPurchasePrice(), maybeItem.get());
            menuItemPriceRepository.save(newPrice);
            return newPrice;
        } else {
            LocalDate dateFrom = oldPrice.getDateFrom();

            if (!dateFrom.equals(LocalDate.now())) {
                newPrice = new MenuItemPrice(LocalDate.now().plusDays(1), null, updateMenuItemPriceDTO.getNewPrice(),
                        oldPrice.isActive(), updateMenuItemPriceDTO.getNewPurchasePrice(), oldPrice.getItem());
                oldPrice.setDateTo(LocalDate.now());
                oldPrice.setActive(false);
                oldPrice.getItem().getPriceList().add(newPrice);
                menuItemPriceRepository.save(newPrice);
                return newPrice;
            } else {
                oldPrice.setPrice(updateMenuItemPriceDTO.getNewPrice());
                oldPrice.setPurchasePrice(updateMenuItemPriceDTO.getNewPurchasePrice());
                menuItemPriceRepository.save(oldPrice);
                return oldPrice;
            }
        }
    }

    public double findCurrentPriceForMenuItemById(long id){
        return menuItemPriceRepository.findCurrentPriceForMenuItemById(id);
    }
}
