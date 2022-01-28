package com.ftn.restaurant.repository;

import com.ftn.restaurant.model.MenuItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"test"})
public class MenuItemRepositoryTest_SQL {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    public void testFindAllBySearchCriteria() {
        List<MenuItem> found = menuItemRepository.findAll("");
        assertEquals(2, found.size());

        found = menuItemRepository.findAll("Ice");
        assertEquals(2, found.size());

        found = menuItemRepository.findAll("Ice T");
        assertEquals(1, found.size());

        found = menuItemRepository.findAll("no match");
        assertEquals(0, found.size());
    }

    @Test
    public void testFindByDeletedFalseAndApprovedTrueAndBySearchCriteria() {
        List<MenuItem> found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("");
        assertEquals(4, found.size());

        found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("Ice");
        assertEquals(1, found.size());


        found = menuItemRepository.findByDeletedFalseAndApprovedTrueAndBySearchCriteria("no match");
        assertEquals(0, found.size());
    }
}