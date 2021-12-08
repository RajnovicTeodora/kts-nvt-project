package com.ftn.restaurant.service;

import static com.ftn.restaurant.constants.MenuItemConstants.*;
import static com.ftn.restaurant.constants.DrinkConstants.*;

import com.ftn.restaurant.model.Drink;
import com.ftn.restaurant.model.MenuItem;
import com.ftn.restaurant.repository.MenuItemRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceUnitTest {

    @Autowired
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @BeforeAll
    public void setup() {
        Drink drink = new Drink(NEW_DRINK_NAME, "", true, false, new ArrayList<>(), NEW_DRINK_TYPE, NEW_CONTAINER_TYPE);

        given(menuItemRepository.findByIdAndDeletedFalse(DELETE_ITEM_ID)).willReturn(Optional.of(drink));
        Drink deleted = drink;
        deleted.setDeleted(true);

        given(menuItemRepository.save(drink)).willReturn(deleted);
    }

    @Test
    public void testDeleteMenuItem(){

        MenuItem deleted = menuItemService.deleteMenuItem(DELETE_ITEM_ID);
        assertTrue(deleted.isDeleted());
        verify(menuItemRepository, times(1)).findByIdAndDeletedFalse(DELETE_ITEM_ID);

    }


}
