package com.ftn.restaurant.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.ftn.restaurant.model.Area;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;


    @Test
    public void testFindByName() {
        Optional<Area> optionalArea = areaRepository.findByName("First floor");
        Assert.assertTrue(optionalArea.isPresent());
        assertEquals("First floor", optionalArea.get().getName());
        
        Optional<Area> optionalArea2 = areaRepository.findByName("abc");
        Assert.assertFalse(optionalArea2.isPresent());
        
    }
    
}
