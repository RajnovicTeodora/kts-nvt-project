package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.DateTimeConstants.*;

import com.ftn.restaurant.dto.SelectedMenuItemsDTO;
import com.ftn.restaurant.exception.ForbiddenException;
import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.model.MenuItemPrice;
import com.ftn.restaurant.repository.MenuItemPriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ftn.restaurant.constants.MenuConstants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuServiceUnitTest {

    @Autowired
    private MenuService menuService;

    @MockBean
    private MenuItemPriceRepository menuItemPriceRepository;

    @Before
    public void setup() {

        MenuItem item = new Drink();
        MenuItemPrice menuItemPriceTrue1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPriceTrue1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);

        MenuItemPrice menuItemPriceFalse1 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPriceFalse1 = new MenuItemPrice(TODAY, null, 10, false, 5, item);


        MenuItemPrice menuItemPrice2 = new MenuItemPrice(TODAY, null, 10, true, 5, item);
        MenuItemPrice savedMenuItemPrice2 = new MenuItemPrice(TODAY, null, 10, false, 5, item);

        // One active item
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(ITEM_ID_LIST.get(0), TODAY)).willReturn(Optional.of(menuItemPriceTrue1));
        given(menuItemPriceRepository.save(menuItemPriceTrue1)).willReturn(savedMenuItemPriceTrue1);

        // Empty id list

        List<MenuItemPrice> menuItemPriceList = new ArrayList<MenuItemPrice>()
        {
            {
                add(menuItemPriceFalse1);
                add(menuItemPrice2);
            }
        };
        given(menuItemPriceRepository.findAll()).willReturn(menuItemPriceList);
        given(menuItemPriceRepository.save(menuItemPriceFalse1)).willReturn(savedMenuItemPriceFalse1);
        given(menuItemPriceRepository.save(menuItemPrice2)).willReturn(savedMenuItemPrice2);

        // MenuItem not found
        given(menuItemPriceRepository.findByMenuItemIdAndDeletedNotAndApprovedAndHasPrice(INVALID_ITEM_ID_LIST.get(0), TODAY)).willReturn(Optional.empty());
    }

    @Test
    public void testDefineActiveMenuItem(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(ITEM_ID_LIST);
        List<MenuItemPrice> menuItemPriceList = menuService.defineActiveMenuItem(selectedMenuItemsDTO);

        assertEquals(1, menuItemPriceList.size());
    }

    @Test
    public void testDefineEmptyActiveMenuItem(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(EMPTY_ITEM_ID_LIST);
        List<MenuItemPrice> menuItemPrices = menuService.defineActiveMenuItem(selectedMenuItemsDTO);
        assertEquals(2, menuItemPrices.size());

    }

    @Test(expected = ForbiddenException.class)
    public void testDefineActiveMenuItemAndExpectForbiddenException(){
        SelectedMenuItemsDTO selectedMenuItemsDTO = new SelectedMenuItemsDTO(INVALID_ITEM_ID_LIST);

        menuService.defineActiveMenuItem(selectedMenuItemsDTO);
    }

}
