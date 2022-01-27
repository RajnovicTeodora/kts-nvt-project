package com.ftn.restaurant.service;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.ftn.restaurant.dto.AreaDTO;
import com.ftn.restaurant.dto.RestaurantTableDTO;
import com.ftn.restaurant.exception.AreaAlreadyExistsException;
import com.ftn.restaurant.exception.AreaNotFoundException;
import com.ftn.restaurant.model.Area;
import com.ftn.restaurant.model.RestaurantTable;
import com.ftn.restaurant.repository.AreaRepository;
import com.ftn.restaurant.repository.TableRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class AreaServiceUnitTest {
	
	@MockBean
	private AreaRepository areaRepository;
	
	@MockBean
	private TableRepository tableRepository;
	
	@MockBean
	private TableService tableService;
	
	@Autowired
	private AreaService areaService;
	
	final Area area1 = new Area("First floor");
	final Area area2 = new Area("Garden");
	
	final RestaurantTable table1 = new RestaurantTable(1, 1, area1);
	final RestaurantTable table2 = new RestaurantTable(2, 2, area1);
	
	@BeforeAll
	public void setup() {

	}
	
	@Test
	public void addAreaTest_Success() throws Exception {
		Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.of(area1));
		
		Area area = areaService.addArea("Roof");
		assertEquals("Roof", area.getName());	
	}
	
	@Test(expected = AreaAlreadyExistsException.class)
	public void addAreaTest_Area_Already_Exists_Exception() throws Exception {
		Mockito.when(areaRepository.findByName("First floor")).thenReturn(Optional.of(area1));
		areaService.addArea("First floor");
	}
	
	@Test
	public void deleteAreaTest_Success() throws Exception {
		
		Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.of(area1));
		
		assertEquals("First floor", areaService.deleteArea(1L).getName());
	}
	
	@Test(expected = AreaNotFoundException.class)
	public void deleteAreaTest_Area_Not_Found_Exception() throws Exception {
		Mockito.when(areaRepository.findById(12L)).thenReturn(Optional.empty());
		areaService.deleteArea(12L);
	}
	
	@Test
	public void editAreaTest_Success() {
		
		Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.of(area1));
		Mockito.when(tableRepository.findById(1L)).thenReturn(Optional.of(table1));
		Mockito.when(tableRepository.findById(2L)).thenReturn(Optional.of(table2));
		Mockito.when(tableRepository.findByTableId(1L)).thenReturn(table1);
		Mockito.when(tableRepository.findByTableId(2L)).thenReturn(table2);
		
		table1.setId(1L);
		table2.setId(2L);
		area1.setId(1L);
		
		RestaurantTableDTO table1DTO = new RestaurantTableDTO(table1);
		RestaurantTableDTO table2DTO = new RestaurantTableDTO(table2);
		
		ArrayList<RestaurantTableDTO> tables = new ArrayList<RestaurantTableDTO>();
		tables.add(table1DTO);
		tables.add(table2DTO);
		
		AreaDTO area1DTO = new AreaDTO(1L, "First floor", tables);
		
		Area editedArea = areaService.editTables(area1DTO);
		
		assertEquals("First floor", editedArea.getName());
		assertEquals(2, editedArea.getTables().size());
	}
	
	@Test(expected = AreaNotFoundException.class)
	public void editAreaTest_Area_Not_Found_Exception() {
		Mockito.when(areaRepository.findById(1L)).thenReturn(Optional.empty());
		area1.setId(1L);
		
		areaService.editTables(new AreaDTO(area1));
	}

}
