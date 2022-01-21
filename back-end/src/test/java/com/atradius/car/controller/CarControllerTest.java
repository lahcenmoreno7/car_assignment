
package com.atradius.car.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.atradius.car.constant.CarConstant;
import com.atradius.car.convert.CarDTO;
import com.atradius.car.model.Car;
import com.atradius.car.service.CarService;
import com.atradius.car.utils.CarResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Home @RunWith(MockitoJUnitRunner.class)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CarService carService;

	@BeforeEach
	void setMockOutput() {
		
		
	}
	
	

	@DisplayName("Test all cars - Controller")
	@Test
	public void get_all_cars() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/cars/all").accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());

	}

	@DisplayName("Test search cars by keyword - Controller")
	@Test
	public void get_cars_by_keyword() throws Exception {

		String keyword = "Audi";
		Sort sort = CarConstant.DEFAULT_SORT_DIRECTION.equalsIgnoreCase(Sort.Direction.ASC.name())
				? Sort.by(CarConstant.DEFAULT_SORT_BY).ascending()
				: Sort.by(CarConstant.DEFAULT_SORT_BY).descending();

		Pageable pageable = PageRequest.of(CarConstant.DEFAULT_PAGE_NUMBER, CarConstant.DEFAULT_PAGE_SIZE, sort);

		CarService mock = org.mockito.Mockito.mock(CarService.class);
		

		when(mock.getCarsBySerachKeyword(keyword, pageable)).thenReturn(carsListStub());

		mockMvc.perform(MockMvcRequestBuilders.get("/cars/search/" + keyword).contentType(MediaType.APPLICATION_JSON))

  				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$",hasSize(3)));

		verifyNoMoreInteractions(mock);

	}

	@DisplayName("Test search cars by keyword - Controller")
	@Test
	public void get_lowest_annual_cost() throws Exception {

		int distanceMonth = 250;
		double price = 0.65;
		
		CarService mock = org.mockito.Mockito.mock(CarService.class);
		
		when(mock.getLowestAnnualCost(String.valueOf(distanceMonth), String.valueOf(price))).thenReturn(carsListStub());

		mockMvc.perform(MockMvcRequestBuilders.get("/lowest").content(asJsonString(cardto()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		verifyNoMoreInteractions(mock);
	}

	@DisplayName("Test Spring add new car - Controller")
	@Test
	public void saveCar_whenPostMethod() throws Exception {

		Car car = Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		mockMvc.perform(MockMvcRequestBuilders.post("/cars/add").content(asJsonString(car))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());

		verify(carService, times(1)).createCar(car);
		verifyNoMoreInteractions(carService);
	}

	public CarDTO cardto() {

		Car car = Car.builder().make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23).annualCost(530.0)
				.yearRelease(2021).build();

		CarDTO carDto = new CarDTO(car);
		return carDto;
	}

	public Car car() {

		return Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

	}

	private CarResponse carsListStub() {

		CarResponse respnseStub = new CarResponse();

		Car car1 = Car.builder().id(1).make("Honda").model("Fit Hibrid").name("Fit").price(17000).fuel(0.23)
				.annualCost(530.0).yearRelease(2021).build();

		Car car2 = Car.builder().id(1).make("Mazda").model("CX - master").name("CX").price(27000).fuel(0.50)
				.annualCost(530.0).yearRelease(2021).build();

		Car car3 = Car.builder().id(1).make("Opel").model("O-Energy").name("Corsa").price(31000).fuel(0.52)
				.annualCost(530.0).yearRelease(2021).build();

		List<CarDTO> carList = Arrays.asList(new CarDTO(car1), new CarDTO(car2), new CarDTO(car3));
		respnseStub.setCars(carList);

		return respnseStub;
	}


	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
