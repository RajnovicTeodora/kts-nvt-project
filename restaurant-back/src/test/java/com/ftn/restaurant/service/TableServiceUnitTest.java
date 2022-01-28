package com.ftn.restaurant.service;

import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.*;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.model.Waiter;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ftn.restaurant.repository.WaiterRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TableServiceUnitTest {

    @Autowired
    private TableService tableService;

    @MockBean
    private TableRepository tableRepository;

    @MockBean
    private WaiterRepository waiterRepository;
    
    @MockBean
    private AreaRepository areaRepository;

    @MockBean
    private OrderService orderService;

    @Test(expected = ForbiddenException.class )
    public void occupyTable_ThrowForbiddenExceptionWhenTableIsOccupied(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setWaiter(new Waiter());
        table.setOccupied(true);

        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(table);
        tableService.occupyTable("waiter", 1L);
    }

    @Test(expected = NotFoundException.class )
    public void occupyTable_ThrowNotFoundExceptionWhenTableIsNonExisting(){
        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(null);
        tableService.occupyTable("waiter", 1L);
    }

    @Test
    public void occupyTable_ReturnStringWhenAllOk(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setWaiter(new Waiter());

        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(table);
        Assert.assertEquals( "Successfully occupied table with table id: 1", tableService.occupyTable("waiter", 1L));
        Assert.assertTrue(table.isOccupied());
    }

    @Test(expected = ForbiddenException.class )
    public void clearTable_ThrowForbiddenExceptionWhenTableIsNotOccupied(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setWaiter(new Waiter());

        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(table);
        tableService.clearTable("waiter", 1L);
    }

    @Test(expected = NotFoundException.class )
    public void clearTable_ThrowNotFoundExceptionWhenTableIsNonExisting(){
        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(null);
        tableService.clearTable("waiter", 1L);
    }

    @Test(expected = ActiveOrdersPresentException.class )
    public void clearTable_ThrowActiveOrdersPresentExceptionWhenTableHasActiveOrders(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setWaiter(new Waiter());
        table.setOccupied(true);
        List<Integer> activeOrders = new ArrayList<>();
        activeOrders.add(1);

        Mockito.when(orderService.getActiveOrdersForTable(1L, "waiter")).thenReturn(activeOrders);
        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(table);
        tableService.clearTable("waiter", 1L);
    }

    @Test
    public void clearTable_ReturnStringWhenAllOk(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setWaiter(new Waiter());
        table.setOccupied(true);

        Mockito.when(tableRepository.findOneWithWaiter(1L, "waiter")).thenReturn(table);
        Assert.assertEquals( "Successfully cleared table with table id: 1", tableService.clearTable("waiter", 1L));
        Assert.assertFalse(table.isOccupied());
    }

    @Test(expected = ForbiddenException.class )
    public void claimTable_ThrowForbiddenExceptionWhenTableIsClaimed(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        Waiter waiter = new Waiter("waiter", "waiter", false);
        table.setWaiter(new Waiter());

        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        Mockito.when(waiterRepository.findByUsername(waiter.getUsername())).thenReturn(waiter);
        tableService.claimTable(waiter.getUsername(),1L);
    }

    @Test(expected = NotFoundException.class )
    public void claimTable_ThrowNotFoundExceptionWhenTableIsNonExisting(){
        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.empty());
        tableService.claimTable("waiter", 1L);
    }

    @Test(expected = NotFoundException.class )
    public void claimTable_ThrowNotFoundExceptionWhenWaiterIsNonExisting(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        Mockito.when(waiterRepository.findByUsername("waiter")).thenReturn(null);
        tableService.claimTable("waiter", 1L);
    }

    @Test
    public void claimTable_ReturnStringWhenAllOk(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        Waiter waiter = new Waiter("waiter", "waiter", false);

        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        Mockito.when(waiterRepository.findByUsername(waiter.getUsername())).thenReturn(waiter);
        Assert.assertEquals( "Successfully claimed table with table id: 1", tableService.claimTable(waiter.getUsername(),1L));
        Assert.assertNotNull(table.getWaiter());
    }

    @Test(expected = ForbiddenException.class )
    public void leaveTable_ThrowForbiddenExceptionWhenTableIsOccupied(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        table.setOccupied(true);

        Mockito.when(tableRepository.findOneWithWaiter(1L,"waiter")).thenReturn(table);
        tableService.leaveTable("waiter",1L);
    }

    @Test(expected = NotFoundException.class )
    public void leaveTable_ThrowNotFoundExceptionWhenTableIsNonExisting(){
        Mockito.when(tableRepository.findOneWithWaiter(1L,"waiter")).thenReturn(null);
        tableService.claimTable("waiter", 1L);
    }

    @Test(expected = NotFoundException.class )
    public void getTableByTableId_ThrowNotFoundExceptionWhenTableIsNonExisting(){
        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.empty());
        tableService.getTableByTableId( 1);
    }

    @Test
    public void getTableByTableId_ReturnRestaurantTableDTOWhenAllOk(){
        Area area = new Area("First floor");
        area.setId(1L);
        RestaurantTable table = new RestaurantTable(12, 12, area);
        table.setOccupied(true);
        Waiter waiter = new Waiter();
        waiter.setId(1L);
        table.setWaiter(waiter);

        Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
        Mockito.when(waiterRepository.findById(1L)).thenReturn(Optional.of(waiter));
        Assert.assertEquals(RestaurantTableDTO.class, tableService.getTableByTableId( 1L).getClass());
    }

    @Test
    public void leaveTable_ReturnStringWhenAllOk(){
        RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
        Waiter waiter = new Waiter("waiter", "waiter", false);
        table.setWaiter(waiter);

        Mockito.when(tableRepository.findOneWithWaiter(1L,"waiter")).thenReturn(table);
        Assert.assertEquals( "Successfully left table with table id: 1", tableService.leaveTable(waiter.getUsername(),1L));
        Assert.assertNull(table.getWaiter());
    }
    
    @Test
    public void addTableTest_Success() {
    	Area area = new Area("First floor");
    	area.setId(1L);
    	Mockito.when(tableRepository.findByPositionXAndPositionY(33, 33)).thenReturn(Optional.empty());
    	Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.of(area));
    	Assert.assertEquals("First floor", tableService.addTable(new RestaurantTableDTO(33, 33, 1L)).getArea().getName());
    }
    
    @Test(expected = AreaNotFoundException.class )
    public void addTableTest_Area_Not_Found_Exception() {
    	Mockito.when(areaRepository.findById(2L)).thenReturn(Optional.empty());
    	tableService.addTable(new RestaurantTableDTO(33, 33, 1L));
    }
    
    @Test
    public void deleteTable_Success() throws Exception {
    	RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
    	tableService.deleteTable(1L);
    }
    
    @Test(expected = TableNotFoundException.class )
    public void deleteTable_TableNotFoundException() throws Exception {
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.empty());
    	tableService.deleteTable(1L);
    }
    
    @Test(expected = TableOccupiedException.class )
    public void deleteTable_TableOccupiedException() throws Exception {
    	RestaurantTable table = new RestaurantTable(12, 12, new Area("abc"));
    	table.setOccupied(true);
    	Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table));
    	tableService.deleteTable(1L);
    }
    
    
}
